<template>
  <div class="popper">
  <div class="indexHead">
    <!-- router:是否使用 vue-router 的模式，启用该模式会在激活导航时以 index 作为 path 进行路由跳转 -->
    <!-- default-active:默认激活的路由 -->
    <el-menu :default-active="data.activePath" class="el-menu-demo" mode="horizontal" background-color="#545c64" text-color="#fff"
             :unique-opened="true" active-text-color="#ffd04b" router>
      <el-sub-menu class="reference" :index="navigationTree.id" :key="navigationTree.id" v-for="navigationTree in data.navigationTrees">
        <template #title>
          <span>{{navigationTree.navigationName}}</span>
        </template>
        <el-menu-item class="popper" :index="second_navigation.navigationUrl" :key="second_navigation.id" v-for="second_navigation in navigationTree.second_navigation" @click="activeItem(second_navigation.navigationUrl)" >
          {{second_navigation.navigationName}}
        </el-menu-item>
      </el-sub-menu>
      <el-menu-item index="4">测试</el-menu-item>
      <el-menu-item index="5">用户</el-menu-item>
      <el-menu-item index="6">退出</el-menu-item>
    </el-menu>
  </div>
  </div>
</template>

<script setup lang="ts">
import store from "../store";
import {getCurrentInstance, nextTick, reactive, watch} from "vue";
// import router from "@/router/index.ts";
// import { createPopper } from '@popperjs/core';

// const exit=()=>{
//   store.commit('exitUser')
//   router.push('/white')
// }
let { proxy } = getCurrentInstance();

const data=reactive({
  activePath:'',
  navigationTrees:[{
    id:'',
    navigationName:'',
    navigationKey:'',
    navigationAble:'',
    second_navigation:[{
      id:'',
      navigationName:'',
      navigationUrl:'',
      navigationAble:'',
      parentId:''
    }]
  }],
})

const init=()=>{
  data.activePath=window.sessionStorage.getItem('activePath')
  proxy.uiService.get('/Guest/navigation').then(response => {
    console.log(response.data)
    data.navigationTrees=[]
    response.data.forEach(item=>{
      data.navigationTrees.push(item)
    })
    console.log(data.navigationTrees)
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
// window.onload = function () {
//   const reference = document.querySelector('div.el-sub-menu__title.el-tooltip__trigger.el-tooltip__trigger');
//   const popper = document.querySelector('ul.el-menu.el-menu--popup.el-menu--popup-bottom-start') as HTMLElement;
//   console.log(reference)
//   console.log(popper)
//   createPopper(reference, popper, {
//     modifiers: [{
//       name: 'computeStyles',
//       options: {
//         adaptive: false,
//       },
//     }],
//   });
// }
</script>
<style>
.el-menu--collapse .el-menu .el-submenu, .el-menu--popup{
  min-width: 100px!important;
}
</style>
<style scoped>
.popper{
  border: 1px solid skyblue;
  border-radius: 4px;
}
.indexHead{
  padding: 1.5rem;
  margin: .5px;
  background-color: skyblue;
}
:deep(.el-menu-item-group__title){
  padding: 0!important;
}
</style>
