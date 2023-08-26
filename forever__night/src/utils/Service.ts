import axios from 'axios'
import store from "../store";
import router from "../router";
import jwt_decode from "jwt-decode";
import { ElMessage } from 'element-plus'

const service = axios.create({
    baseURL: '/api', // url = base url + request url
    timeout: 6 * 1000, // 请求超时时间6秒
    withCredentials: true, // 当跨域请求时发送cookie
})

service.interceptors.request.use((config) => {

    // 如果大于说明是token过期了
    if(!store.state.user.token||Date.now()/1000>jwt_decode(store.state.user.token)['exp']) {
        store.commit('exitUser')
        router.push('/')
    }
    else{
        ElMessage({
                customClass:'focus-message',
                message:'操作成功',
                type:'success',
                duration:1000});
        console.log('刷新 token')
        //告诉后端刷新token
        config.headers['Authorization'] = store.state.user.token;
        config.headers['Refresh']='true'
    }
    return config ;
}, (error) => {
    ElMessage({
        customClass:'focus-message',
        message:'请重新登录',
        type:'error'});
})

service.interceptors.response.use((response) => {
        //如果接口返回token，替换本地旧token
        if (response.headers.allow==='false') {
            ElMessage({
                customClass:'focus-message',
                message:'请重新登录',
                type:'error',
                duration:1000});
            store.commit('exitUser')
            console.log('返回登录页面')
            router.push('/')
            return Promise.reject()
        }
        if(response.headers.token){
            console.log('token刷新成功')
            store.commit('addUser', response.headers.token)
            console.log(store.state.user.token)
        }
        return response
    }, (error) => {
        ElMessage({
            customClass:'focus-message',
            message:'token已过期,请重新登录',
            type:'error'});
    }
);

export default service