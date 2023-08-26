<template>
    <div class="input">
        <el-form class="input-group mb-3">
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">账号</span>
                <el-input type="text" class="form-control" placeholder="账号" v-model="data.userId" aria-label="UserId" aria-describedby="basic-addon1"/>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">密码</span>
                <el-input type="password" class="form-control" placeholder="密码" v-model="data.password" aria-label="PassWord" aria-describedby="basic-addon1" show-password/>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">密码</span>
                <el-input type="text" class="form-control" placeholder="验证码" v-model="data.verifyCode" aria-label="verifyCode" aria-describedby="basic-addon1"/>
                <el-image :src="data.imageCode" fit="fill" @click="flushed"></el-image>
            </div>
        </el-form>
        <div class="button-login">
            <button class="btn btn-primary" @click="login">登陆</button>
        </div>
    </div>
</template>

<script setup lang="ts">
import store from "../store/index.ts";
import {getCurrentInstance, reactive} from "vue";
import router from "../router";
import {ElMessage} from "element-plus";
import { JSEncrypt } from 'jsencrypt'

let { proxy } = getCurrentInstance();

// const publicKey=process.env.VUE_APP_PUBLICKEY;
// console.log("publicKey:  "+publicKey)

const data = reactive({
    userId: '',
    password: '',
    verifyCode:'',
    imageCode:''
})
const flushed=()=>{
    proxy.$axios.get('/Guest/verifyCode').then(response=>{
        sessionStorage.setItem('kaptchaOwner', JSON.stringify({ data: response.headers.kaptchaowner, time: new Date().getTime() }))
        data.imageCode=response.data.imageCode
    })
}
const init=()=>{
    proxy.$axios.get('/Guest/').then(response=> {
        sessionStorage.setItem('publicKey',response.data)
    })
}
if(!sessionStorage.getItem('publicKey')){
    init()
}
if(data.imageCode===''){
    flushed()
}
const encrypt=(publicKey, password)=> {
    let encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    return encrypt.encrypt(password);
}

const login=()=>{
    let kaptchaOwnerTime=JSON.parse(sessionStorage.getItem('kaptchaOwner')).time
    //验证码生效10分钟
    if((new Date().getTime()-kaptchaOwnerTime)>10*60*1000){   flushed()
        ElMessage({
        customClass:'focus-message',
        message:'验证码过期请重新输入',
        type:'error',
        duration:1000})
        return;}
    let kaptchaOwnerData=JSON.parse(sessionStorage.getItem('kaptchaOwner')).data
    let publicKey=sessionStorage.getItem('publicKey')
    let encrypt_Pwd = encrypt(publicKey, data.password)
    store.commit('exitUser')
    if(data.userId===''){
        alert("请输入用户名")
        return
    }
    if(data.password===''){
        alert("请输入密码")
        return
    }
    proxy.$axios.post('/Guest/userLogin',proxy.$qs.stringify({
        userId:data.userId,
        password:encrypt_Pwd,
        verifyCode:data.verifyCode,
        kaptchaOwner:kaptchaOwnerData
    })).then(response=>{
        let token=response.data;
        console.log(token)
        if(token===''){
            ElMessage({
                customClass:'focus-message',
                message:'密码错误请重新输入',
                type:'error',
                duration:1000})
        }
        else if(token==='not found'){
            ElMessage({
                customClass:'focus-message',
                message:'用户名错误请重新输入',
                type:'error',
                duration:1000})
        }
        else if(response.data==='verifyCode error'){
            ElMessage({
                customClass:'focus-message',
                message:'验证码错误请重新输入',
                type:'error',
                duration:1000})
        }
        else{
            store.commit('addUser', token)
            ElMessage({
                customClass:'focus-message',
                message:'登录成功',
                type:'success',
                duration:1000});
            router.push({path: '/index'});
        }
    }).catch(error=>{
        console.error(error);
    })
}
</script>

<style scoped>
.input{
    position: relative;
    left: 40%;
    top: 200px;
    width: 300px;
    height: 300px;
}
</style>