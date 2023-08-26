<template>
    <div class="indexAside">
        <!-- router:是否使用 vue-router 的模式，启用该模式会在激活导航时以 index 作为 path 进行路由跳转 -->
        <!-- default-active:默认激活的路由 -->
        <el-menu :default-active="data.activePath" class="el-menu-demo" background-color="#545c64" text-color="#fff"
                 :unique-opened="true" active-text-color="#ffd04b" router>
            <el-sub-menu :index="permMenuTree.id" :key="permMenuTree.id" v-for="permMenuTree in data.permMenuTrees">
                <template #title>
                    <span>{{permMenuTree.permissionName}}</span>
                </template>
                <el-menu-item-group :index="child.permissionUrl" :key="child.id" v-for="child in permMenuTree.children" @click="activeItem(child.permissionUrl)">
                    <el-menu-item :index="child.permissionUrl">{{child.permissionName}}</el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>
        </el-menu>
    </div>
</template>

<script setup lang="ts">
import store from "../store";
import {getCurrentInstance, reactive, ref, watch} from "vue";

let { proxy } = getCurrentInstance();

const data=reactive({
  activePath:'',
    permMenuTrees: [{
        id:'',
        permissionName:'',
        permissionUrl:'',
        children:[{
            id:'',
            permissionName:'',
            permissionUrl:''
        }]
    }]
})

const init=()=>{
    data.activePath=window.sessionStorage.getItem('activePath')
    proxy.uiService.post('/menu', proxy.$qs.stringify({
        userId: store.state.user.userId
    })).then(response => {
      console.log(response.data)
      data.permMenuTrees=[]
      response.data.forEach(item=>{
        data.permMenuTrees.push(item)
      })
      console.log(data.permMenuTrees)
    }).catch(error => {
        console.log(error)
    })
}

if(store.state.user.userId!=''&&store.state.user.userId!=undefined) {
  if((new Date().getTime()-store.state.time)>1500) {
    init()
  }
}
watch(()=>store.state.user.userId,()=>{
    //防止登录时init()方法被重复执行
    if(store.state.user.userId!=''&&store.state.user.userId!=undefined) {
      if((new Date().getTime()-store.state.time)>1500) {
        init()
      }
    }
})
const activeItem=(permissionUrl)=>{
  window.sessionStorage.setItem('activePath', permissionUrl)
  data.activePath = permissionUrl
}
</script>
<style scoped>
.indexAside{
    width: 15%;
    position: absolute;
    top: 30%;
}
</style>