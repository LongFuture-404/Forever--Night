<template>
  <iframe class="textInput" :src=iframeUrl />
<!--  <el-form label-position="top" label-width="380px">-->
<!--    <el-form-item class="avatar-upload" label="头像">-->
<!--      <el-dialog v-model="upload.dialogVisible">-->
<!--        <img class="avatar-upload-img" :src="upload.dialogImageUrl" alt="Preview Image" />-->
<!--      </el-dialog>-->
<!--    </el-form-item>-->
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
import store from "../store/index";
import {getCurrentInstance, reactive, ref} from "vue";
import router from "../router";

let { proxy } = getCurrentInstance();

// const publicKey = process.env.VUE_APP_PUBLICKEY
// console.log('publicKey:  '+publicKey)

const data=reactive({
    userList: [],
    total: 0,
    pageNumber:1,
    pageSize:10,
    image:['http://localhost:8002/view/1528961685699.png','http://localhost:8002/view/1522571423608.png'],
})
const iframeUrl=ref('/richTextFormat')
store.commit('setTime',new Date().getTime())

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

</script>
<style scoped lang="scss">
.textInput{
  width: 80%;
  height: 70%;
  position: absolute;
  left: 20%;
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