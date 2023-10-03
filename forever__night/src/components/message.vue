<template>
<!--  <div contenteditable="true">contenteditable的div中的文字</div>-->
<!--  <button @click="execCommandBold">内容加粗</button>-->
</template>

<script setup lang="ts">
// const execCommandBold=()=>{
//   document.execCommand('bold',false,null);
// }
</script>

<style lang="scss">
.focus-message{
    width: 200px;
    height: 50px;
    .el-icon-success:before{
        color:#1e8dfe !important;
    }
    .el-message__content {
        font-size: 12px;
        color: #333333;
    }
}
</style>