<script setup lang="ts">
import {getCurrentInstance, nextTick, reactive, watch, onBeforeUnmount, ref, shallowRef, onMounted} from "vue";
// import {useDebounceRef} from "../debounceRef";
import store from "../store";
import "../assets/root.css"
import {changeFontTag} from "../assets/changeFontTag";
import {
  upload,
  pictureData,
  beforeUpload,
  handleExceed,
  handlePictureCardPreview, handleRemove,
  handleSuccess,
  uploadFileError
} from "../assets/pictureUpload";
import { Plus } from '@element-plus/icons-vue'
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
const { ctx } = getCurrentInstance()

const data=reactive({
  debounceInput: () => {},
  // content:useDebounceRef('', 1000),
  content:'',
  isInsert:0,
  userList: [],
  total: 0,
  pageNumber:1,
  pageSize:10,
})
const linkUrl=reactive({
  url:'',
  name:''
})

function debounce (func, delay, immediate) {
  //闭包
  let timer = null
  // console.log(timer)
  //不能用箭头函数
  return function () {
    // console.log(data.content)
    if (timer) {
      clearTimeout(timer)
    }
    if (immediate && !timer) {
      func.apply(this,arguments)
    }
    timer = setTimeout(() => {
      func.apply(this,arguments)
    }, delay)
  }
}

/**
 * 输入完毕记录光标焦点位置
 */
let lastEditRange=reactive([])
let selection=reactive(window.getSelection())
const outContent=(value)=>{
//   console.log(data.content)
  data.content=ctx.$refs['textarea'].innerHTML
  for(let i=0;i<selection.rangeCount;i++){
    lastEditRange[i]=selection.getRangeAt(i)
  }
  if(lastEditRange[0].commonAncestorContainer.type!='p') {
    store.commit('setLastEditRange', lastEditRange[0])
  }
  console.log(store.state.lastEditRange)
  // console.log('value:   '+value)
  console.log('data.content:   '+data.content)
}
data.debounceInput=debounce(outContent,1000,false)
const getHref=(content)=> {
  let fContent = content.replace(/<p>/g,"").replace(/<\/p>/g,"\n");//去除p标签
  let pContent=fContent.split('\n')
  let allContent='';
  // console.log('pContent:  '+pContent)
  if (!content) {
    return '';
  }
  let regP=/([\w\W])+/g;
  pContent.forEach(function(item) {
    // console.log(item)
    item=item.replace(regP,
        (match) => {
          // console.log('match   '+match)
          return `<p>${match}</p>`;
        }
    );
    let reg = /(http:\/\/|https:\/\/)((\w|=|\?|\.|\/|&|-)+)(\[)+((\w|\.|\/|\:|\ |[\u4e00-\u9fa5])+)(\])+/g;
    item = item.replace(reg,
        // "<a href='$1$2'>$5</a>"
        (match) => {
          const urlHttp = match.indexOf('http://') && match.indexOf('https://');
          const url = urlHttp === -1 ? match.split('/')[0] : match.substring(match.lastIndexOf("[")+1,match.lastIndexOf("]"));
          const href = urlHttp === -1 ? `https://${match}` : match.split("[")[0];
          return `<a href="${href}">${url}</a>`;
        }
    );
    allContent=allContent+item
    // console.log('item   '+item)
  })
  // console.log(allContent)
  return allContent;
}
/**
 * 查看内容同时不失去聚焦
 */
const setContent=()=>{
  nextTick(() => {
    // console.log(ctx.$refs['textarea'].innerHTML)
    ctx.$refs['textarea'].focus()
    console.log(data.content)
    console.log(store.state.lastEditRange)
    selection.collapse(store.state.lastEditRange.endContainer, store.state.lastEditRange.endOffset)
  })
}
/**
 * 设置光标位置
 */
let node=reactive([])
const rangeSet=() => {
  if(store.state.lastEditRange) {
    let flag=0
    nextTick(() => {
      let Node=ctx.$refs['textarea']
      console.log(store.state.lastEditRange.endContainer)
      for(let i=0;i<Node.childNodes.length;i++){
        // console.log(store.state.lastEditRange.endContainer.data)
        // console.log(Node.childNodes[i].innerHTML.substring(0,store.state.lastEditRange.endOffset))
        // if(Node.childNodes[i].innerHTML==store.state.lastEditRange.endContainer.data){
        //   node[0]=Node.childNodes[i]
        //   selection.collapse(node[0].firstChild, store.state.lastEditRange.endOffset)
        //   break
        // }
        for(let j=0;j<Node.childNodes[i].childNodes.length;j++){
          if(Node.childNodes[i].childNodes[j].data==store.state.lastEditRange.endContainer.data){
            if(data.isInsert==1){
              flag=1
              continue
            }
            else{
              node[0] = Node.childNodes[i].childNodes[j]
              selection.collapse(node[0], store.state.lastEditRange.endOffset)
              break
            }
          }
          if(flag==1) {
            node[0] = Node.childNodes[i].childNodes[j]
            flag=0
            selection.collapse(node[0].firstChild, linkUrl.name.length)
            data.isInsert=0
            break;
          }
        }
      }
    })
  }
}

const uploadUrl=()=>{
  nextTick(() => {
    data.isInsert=1
    let fragment = lastEditRange[0].createContextualFragment(linkUrl.url+'['+linkUrl.name+']');
    lastEditRange[0].insertNode(fragment)
    data.content=ctx.$refs['textarea'].innerHTML
    ctx.$refs['textarea'].focus()
    // data.content=ctx.$refs['textarea'].innerHTML+linkUrl.url+'['+linkUrl.name+']'
  })
  // data.content=data.content+linkUrl.url+'['+linkUrl.name+']'
}

document.addEventListener("selectionchange", () => {

});
document.onselectionchange = () => {
  if(selection.isCollapsed==false){
    store.commit("setSelection",selection)
  }
};
watch(()=>data.content,()=>{
  //防止登录时init()方法被重复执行
    rangeSet()
})

/**
 * 设置光标在编辑器末尾
 * @param obj
 */
// function keepLastIndex(obj) {
//   console.log('obj:   '+obj)
//   console.log('window.getSelection:   '+window.getSelection)
//   console.log('document.selection:   '+document.selection)
//   if (window.getSelection) { //ie11 10 9 ff safari
//     obj.focus(); //解决ff不获取焦点无法定位问题
//     // 设置最后光标对象
//     selection = window.getSelection(); //创建selection
//     selection.selectAllChildren(obj); //selection 选择obj下所有子内容
//     selection.collapseToEnd(); //光标移至最后
//
//     // selection.removeAllRanges()
//     // selection.collapsed(false)
//     // selection.addRange(lastEditRange[0]) //范围选中
//   } else if (document.selection) { //ie10 9 8 7 6 5
//     selection = document.selection.createRange(); //创建选择对象
//     //var range = document.body.createTextRange();
//     selection.moveToElementText(obj); //range定位到obj
//     selection.collapse(false); //光标移至最后
//     selection.select();
//   }
// }
// const myEditorEnter=(e)=> {
//   e.preventDefault();
//   data.content = data.content+'<br/>'
// }

/**
 * 记录失去焦点时光标位置
 */
const inputEnd=()=>{
  for(let i=0;i<selection.rangeCount;i++){
    lastEditRange[i]=selection.getRangeAt(i)
  }
  if(lastEditRange[0].commonAncestorContainer.type!='p') {
    store.commit('setLastEditRange', lastEditRange[0])
  }
  console.log(store.state.lastEditRange)
  // console.log(lastEditRange[0])
}
/**
 * keyup在input前执行
 */
// const inputNow=()=>{
//   for(let i=0;i<selection.rangeCount;i++){
//       lastEditRange[i]=selection.getRangeAt(i)
//   }
//   // console.log(lastEditRange[0])
// }
// const blurEvent=(e)=>{
//   // let end=e.target.selectionEnd;
//   // e.target.setSelectionRange(end-6,end)
//   console.log(e)
//   console.log(e.srcElement)
//   console.log(e.srcElement.selectionStart) //光标所在的位置
// }

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

// 内容 HTML
const valueHtml = ref('<p>hello</p>')

const mode = 'default'

// 模拟 ajax 异步获取内容
onMounted(() => {
  setTimeout(() => {
    valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
  }, 1500)
})

const toolbarConfig = {}
const editorConfig = { placeholder: '请输入内容...' }

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
</script>

<template>
  <div style="border: 1px solid #ccc">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        :mode="mode"
    />
    <Editor
        style="height: 500px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
    />
  </div>
<!--  @keypress.enter="myEditorEnter($event)" 对换行操作进行自定义处理  @keyup="inputNow"input输入前执行方法-->
  <div class="textarea" ref="textarea" @blur="inputEnd" @input="data.debounceInput" v-html="getHref(data.content)" contentEditable="true" placeholder="请输入正文"/>
  <div class="textareaInput">
<!--    <el-input v-model="data.content" @input="data.debounceInput"/>-->
<!--    <input v-model="data.content" />-->
    <div style="width: 200px;">
<!--     @blur="blurEvent" 设置焦点丢失事件,只有input元素上才能获取到selectionEnd和selectionStart-->
      <el-input v-model="linkUrl.url" placeholder="地址"></el-input>
      <el-input v-model="linkUrl.name" placeholder="链接名"></el-input>
      <el-button @click="uploadUrl">链接提交</el-button>
      <el-button @click="changeFontTag('strong','STRONG', 'EM')"></el-button>
      <el-button @click="changeFontTag('em','EM', 'STRONG')"></el-button>
      <el-button @click="setContent">查看内容</el-button>
      <el-upload class="upload-demo" :class="{reached_the_limit: upload.isReachedTheLimit}" action="http://localhost:8002/img" :show-file-list="true" :headers="pictureData.headers" :on-remove="handleRemove"
                         :file-list="pictureData.fileList" list-type="picture-card" :limit="1" :on-exceed="handleExceed" :on-success="handleSuccess"
                         :on-error="uploadFileError" :on-preview="handlePictureCardPreview" :before-upload="beforeUpload">
        <img :src="pictureData.fileList" class="avatar"  alt=""/>
        <el-icon class="avatar-uploader-icon"><Plus/></el-icon>
      </el-upload>
    </div>
  </div>
</template>

<style scoped lang="scss">
*{
  -webkit-user-modify: read-write-plaintext-only;
}
.reached_the_limit {
  :deep(.el-upload){
    display: none;
  }
}
.textareaInput{
  position: absolute;
  left: 20%;
}
.textarea{
  width: var(--rich-text-width);
  height: var(--rich-text-height);
}
/*为空时显示 element attribute content*/
.textarea:empty:before{
  content: attr(placeholder);
}
/*焦点时内容为空*/
.textarea:focus:before{
  content:none;
}
</style>