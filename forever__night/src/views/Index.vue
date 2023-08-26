<template>
  <iframe class="textInput" :src=iframeUrl />
<!--  <el-form label-position="top" label-width="380px">-->
<!--    <el-form-item class="avatar-upload" label="头像">-->
<!--      <el-upload class="upload-demo" :class="{reached_the_limit: upload.isReachedTheLimit}" action="http://localhost:8002/img" :show-file-list="true" :headers="data.headers" :on-remove="handleRemove"-->
<!--                 :file-list="data.fileList" list-type="picture-card" :limit="1" :on-exceed="handleExceed" :on-success="handleSuccess"-->
<!--                 :on-error="uploadFileError" :on-preview="handlePictureCardPreview" :before-upload="beforeUpload">-->
<!--&lt;!&ndash;        <img :src="data.fileList" class="avatar"  alt=""/>&ndash;&gt;-->
<!--        <el-icon class="avatar-uploader-icon"><Plus /></el-icon>-->
<!--      </el-upload>-->
<!--      <el-dialog v-model="upload.dialogVisible">-->
<!--        <img class="avatar-upload-img" :src="upload.dialogImageUrl" alt="Preview Image" />-->
<!--      </el-dialog>-->
<!--    </el-form-item>-->
<!--&lt;!&ndash;    <div class="thread_mess" id="thread_imgid" data-tid="67902">&ndash;&gt;-->
<!--&lt;!&ndash;      <div class="message">点击跳转到百度首页：https://www.baidu.com[百度一下]  https://www.bilibili.com[哔站] https://t.bilibili.com/?spm_id_from=333.1007.0.0[哔站动态]</div>&ndash;&gt;-->
<!--&lt;!&ndash;      <div>&ndash;&gt;-->
<!--&lt;!&ndash;        <img src="../assets/logo.png">&ndash;&gt;-->
<!--&lt;!&ndash;        <img style="width: 300px;height: 200px;" src="http://localhost:8002/view/1528961685699.png">&ndash;&gt;-->
<!--&lt;!&ndash;      </div>&ndash;&gt;-->
<!--&lt;!&ndash;    </div>&ndash;&gt;-->
<!--  </el-form>-->
<!--  <button @click="jump">跳转</button>-->
<!--    <el-button @click="get" style="position: absolute;left: 50%">图片</el-button>-->
<!--    <el-image-->
<!--            style="width: 100px; height: 100px"-->
<!--            :src="data.image[0]"-->
<!--            :preview-src-list="data.image"-->
<!--            >-->
<!--    </el-image>-->
<!--    <el-image-viewer-->
<!--            v-if="data.visible"-->
<!--            :url-list="data.image"-->
<!--            @close="close"-->
<!--    />-->
<!--    <el-form label-position="left" label-width="380px">-->
<!--        <el-form-item label="头像">-->
<!--            <el-upload class="upload-demo" action="http://localhost:8002/img" :headers="data.headers" :on-remove="handleRemove"-->
<!--                       :file-list="data.fileList" list-type="picture" :limit="1" :on-exceed="handleExceed" :on-success="handleSuccess"-->
<!--                       :before-upload="beforeUpload">-->
<!--                <el-button size="small" type="primary">点击上传</el-button>-->
<!--                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
<!--            </el-upload>-->
<!--        </el-form-item>-->
<!--    </el-form>-->
<!--    <el-table class="table" :data="data.userList" border style="width: 100%">-->
<!--        <el-table-column fixed prop="userId" label="序号">-->
<!--        </el-table-column>-->
<!--        <el-table-column prop="userName" label="姓名">-->
<!--        </el-table-column>-->
<!--        <el-table-column fixed="right" label="操作" width="100">-->
<!--            <template #default="scope">-->
<!--                <el-button type="primary" link size="small" @click="">编辑</el-button>-->
<!--                <el-button type="primary" link size="small" @click="">删除</el-button>-->
<!--            </template>-->
<!--        </el-table-column>-->
<!--    </el-table>-->
<!--    <el-pagination class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="data.pageNumber"-->
<!--                   :page-sizes="[10, 20, 50]" :page-size="data.pageSize" layout="total, sizes, prev, pager, next, jumper"-->
<!--                   :total="data.total">-->
<!--    </el-pagination>-->
</template>

<script setup lang="ts">
import { Plus } from '@element-plus/icons-vue'
import store from "../store/index.ts";
import {getCurrentInstance, reactive, ref} from "vue";
import router from "../router";
import {ElMessage, UploadProps} from "element-plus";

let { proxy } = getCurrentInstance();

// const publicKey = process.env.VUE_APP_PUBLICKEY
// console.log('publicKey:  '+publicKey)

const upload = reactive({
  dialogImageUrl:'',
  dialogVisible:false,
  isReachedTheLimit:false
})

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  console.log(uploadFile)
  upload.dialogImageUrl = uploadFile.url!
  upload.dialogVisible = true
}
const data=reactive({
    userList: [],
    total: 0,
    pageNumber:1,
    pageSize:10,
    headers: {Authorization: store.state.user.token},
    imageUrl:'',
    fileList: [],
    image:['http://localhost:8002/view/1528961685699.png','http://localhost:8002/view/1522571423608.png'],
    visible: false
})
const iframeUrl=ref('/richTextFormat')
store.commit('setTime',new Date().getTime())

const content=reactive({
  data:'点击跳转到百度首页：https://www.baidu.com[百度一下]  https://www.bilibili.com[哔站] https://t.bilibili.com/?spm_id_from=333.1007.0.0[哔站动态]\n' +
          '        <img src="static/img/logo.png">\n' +
      '        <img style="width: 300px;height: 200px;" src="http://localhost:8002/view/1528961685699.png">'
})
const get=()=> {
    data.visible = true
}
const close=()=> {
    data.visible = false
}
const handleSuccess=(res, file, fileList)=> {
    let imageUrl = "http://localhost:8002/view/" + file.name;
    data.imageUrl = imageUrl;
    let obj = {url:'',name:''};
    obj.url = imageUrl;
    obj.name = file.name;
    data.fileList.push(obj);
  ElMessage({message: "头像图片上传成功",type:"success"});
}
const handleRemove=(file, fileList) =>{
    data.fileList = [];
    data.imageUrl = '';
  upload.isReachedTheLimit=false
    console.log(fileList);
    console.log(data.imageUrl);
}
const handleExceed=(files, fileList) =>{
    ElMessage({message: "上传文件个数不能超过1个"});
}
const uploadFileError=(files, fileList)=>{
  if(data.fileList.length==0){
    upload.isReachedTheLimit=false
  }
}
const beforeUpload=(file)=> {
  if(data.fileList.length==0){
    upload.isReachedTheLimit=true
  }
    if (file.type != 'image/png') {
        ElMessage({message: "上传文件类型仅支持png格式"});
    }
}
const jump=()=>{
    proxy.uiService.post('/userManage',proxy.$qs.stringify({
        id:'1'
    })).then(response=>{
        router.push({path: '/register'});
    }).catch(error=>{

    })
}
const getUserList=()=> {
    proxy.uiService.post("/getUserPage", proxy.$qs.stringify({
        pageNumber : data.pageNumber,
        pageSize : data.pageSize,
    })).then(response => {
        let page = response.data;
        data.userList = page.records;
        data.total = page.total;
        data.pageNumber = page.current;
        data.pageSize = page.size;
    }).catch(() => {
        console.log('获取用户失败')
    })
}
const handleSizeChange=(val)=> {
    console.log(data.pageNumber)
    // 把变化的每页显示条数赋给pageSize
    // 调用getUserList查询结果
    data.pageSize = val;
    getUserList();
}
const handleCurrentChange=(val)=> {
    console.log(data.pageSize)
    // 把当前页数赋给pageNumber
    // 调用getUserList
    data.pageNumber = val;
    getUserList();
}
getUserList()

// $(document).ready(function(){
//   $(function() {
//     let imgSRC=[]
//     let textR=$('.thread_mess').html();
//     let reg = /(http:\/\/|https:\/\/)((\w|=|\?|\.|\/|&|-)+)(\[)+((\w|\.|\/|\:|[\u4e00-\u9fa5])+)(\])+/g;
//     // var reg = /(http:\/\/|https:\/\/)((\w|=|\?|\.|\/|&|-)+)(\[)*((\w|\.|\/|\:|[\u4e00-\u9fa5])*)(\])*/g;
//     $.each($('.thread_mess img'),function(index,domEle){
//       imgSRC.push(domEle.src)
//     })
//       imgSRC.forEach((item,index,arr)=>{
//         if(reg.exec(item)){
//           console.log(item+'    为imgSRC')
//           // return true
//         }else{
//           textR = textR.replace(reg, "<a href='$1$2'>$5</a>");
//           document.getElementById('thread_imgid').innerHTML = textR;
//         }
//         reg.lastIndex = 0;
//         },
//       )
//     console.log(textR)
//   });
// });
</script>
<style scoped lang="scss">
.textInput{
  width: 80%;
  height: 70%;
  position: absolute;
  left: 20%;
}
.reached_the_limit {
  :deep(.el-upload){
    display: none;
  }
}
.avatar-upload-img{
  width: 100%;
  height: 100%;
}
.avatar-upload{
  width: 30%;
  height: 25%;
  position: relative;
  left: 30%;
  top: 100px;
}
.upload-demo{
    z-index: 5;
}
.table{
    width: 80% !important;
    position: absolute;
    left: 200px;
    top: 250px;
}
.pagination{
    position: absolute;
    left: 45%;
    top: 20%;
}
</style>