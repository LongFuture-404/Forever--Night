<template>
    <header>
        <IndexHeader v-if="store.state.isIndex!=undefined&&store.state.isIndex!='false'"/>
    </header>
    <main>
        <index-aside v-if="store.state.isIndex!=undefined&&store.state.isIndex!='false'"/>
        <message/>
        <router-view></router-view>
    </main>
</template>

<script setup lang="ts">
// this.$options.name    获取组件名称
import IndexHeader from './components/indexHeader.vue'
import Message from "./components/message.vue";
import IndexAside from "./components/indexAside.vue";
import store from "./store";

</script>

<style>

</style>
