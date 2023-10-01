<template>
    <div class="input">
        <div class="input-group mb-3">
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">账号</span>
                <input type="text" class="form-control" placeholder="账号" v-model="data.userId" aria-label="UserId" aria-describedby="basic-addon1">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">密码</span>
                <input type="password" class="form-control" placeholder="密码" v-model="data.password" aria-label="PassWord" aria-describedby="basic-addon1">
            </div>
        </div>
        <div class="button-login">
            <button class="btn btn-primary" @click="register">注册</button>
        </div>
    </div>
</template>

<script setup lang="ts">
import router from "../router";
import {getCurrentInstance, reactive} from "vue";
import {JSEncrypt} from "jsencrypt";

let { proxy } = getCurrentInstance();
const data = reactive({
    userId: '',
    password: ''
})
// let publicKey=sessionStorage.getItem('publicKey')
const init=()=>{
    proxy.$axios.get('/').then(response=> {
        sessionStorage.setItem('publicKey',response.data)
    })
}
if(!sessionStorage.getItem('publicKey')){
    init()
}
const encrypt=(publicKey, password)=> {
    let encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    return encrypt.encrypt(password);
}
const register=()=>{
    let publicKey=sessionStorage.getItem('publicKey')
    let encrypt_Pwd = encrypt(publicKey, data.password)
    proxy.$axios.post('/register',proxy.$qs.stringify({
        userId:data.userId,
        password:encrypt_Pwd
    })).then(response=>{
        router.push({path: '/'});
    }).catch(error=>{

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