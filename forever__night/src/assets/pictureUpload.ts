import {ElMessage, UploadProps} from "element-plus";
import {reactive} from "vue";
import store from "../store";

export const upload = reactive({
    dialogImageUrl:'',
    dialogVisible:false,
    isReachedTheLimit:false
})
export const pictureData=reactive({
    headers: {Authorization: store.state.user.token},
    imageUrl:'',
    fileList: [],
    image:['http://localhost:8002/view/1528961685699.png','http://localhost:8002/view/1522571423608.png'],
    visible: false
})
export function handleSuccess(res, file, fileList:[]) {
    let imageUrl = "http://localhost:8002/view/" + file.name;
    pictureData.imageUrl = imageUrl;
    let obj = {url:'',name:''};
    obj.url = imageUrl;
    obj.name = file.name;
    pictureData.fileList.push(obj);
    ElMessage({message: "头像图片上传成功",type:"success"});
}
export function handleRemove(file, fileList:[]) {
    pictureData.fileList = [];
    pictureData.imageUrl = '';
    upload.isReachedTheLimit=false
    console.log(fileList);
    console.log(pictureData.imageUrl);
}
export function handleExceed(files, fileList:[]){
    ElMessage({message: "上传文件个数不能超过1个"});
}
export function uploadFileError(files, fileList:[]){
    if(pictureData.fileList.length==0){
        upload.isReachedTheLimit=false
    }
}
export function beforeUpload(file) {
    if(pictureData.fileList.length==0){
        upload.isReachedTheLimit=true
    }
    if (file.type != 'image/png') {
        ElMessage({message: "上传文件类型仅支持png格式"});
    }
}
// const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {}
export function handlePictureCardPreview(uploadFile) {
    console.log(uploadFile)
    upload.dialogImageUrl = uploadFile.url!
    upload.dialogVisible = true
}
export function get() {
    pictureData.visible = true
}
export function close(){
    pictureData.visible = false
}