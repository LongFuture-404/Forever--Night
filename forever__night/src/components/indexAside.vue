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
                <el-menu-item-group :index="child.permissionUrl" :key="child.id" v-for="child in permMenuTree.children">
<!--                  无custom,在ul内层li外层套一层a标签，外层v-for中无法使用custom，RouterLink中的v-for可以使用custom-->
<!--                  <RouterLink :to="child.permissionUrl" :active-class="child.permissionUrl" :v-slot="{activeItem}"  replace>-->
                    <el-menu-item :index="child.permissionUrl" @click="activeItem">{{child.permissionName}}</el-menu-item>
<!--                  </RouterLink>-->
                </el-menu-item-group>
              <!--   不刷新改变页面,单层跳转逻辑下可以直接v-for  [custom 属性的意思，就是允许自定义标签，如果不写就会定义成 a 标签] -->
              <!--   v-slot="{ href,route,isActive,navigate,isExactActive}" href 就是解析后的 url route 就是解析后的规范的route对象 -->
              <!--   navigate 导航的触发函数   isActive 是否匹配的状态   isExactActive 是否精准匹配的状态-->
              <!--   active-class这个属性是设置激活链接时class属性，也就是当前页面所有与当前地址所匹配的的链接都会被添加class属性-->
              <!--   replace在router-link标签中添加后，页面切换时不会留下历史记录-->
              <!--   exact开启router-link的严格模式   一般只给/路由加上exact属性-->
<!--              <RouterLink custom :to="child.permissionUrl" active-class="" v-slot="{}"  replace  exact>{{permMenuTree.permissionName}}</RouterLink>-->
            </el-sub-menu>
        </el-menu>
    </div>
</template>

<script setup lang="ts">
import store from "../store";
import {getCurrentInstance, reactive, watch} from "vue";

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
const activeItem=(event)=>{
  window.sessionStorage.setItem('activePath', event.index)
  data.activePath = event.index
}
</script>
<style scoped>
.indexAside{
    width: 15%;
    position: absolute;
    top: 30%;
}
</style>