import axios from 'axios'
import store from "../store";
import router from "../router";
import jwt_decode from "jwt-decode";
import { ElMessage } from 'element-plus'

const uiService = axios.create({
    baseURL: '/uiApi', // url = base url + request url
    timeout: 6 * 1000, // 请求超时时间6秒
    withCredentials: true, // 当跨域请求时发送cookie
})
// 正在进行中的请求列表
let reqList = [] // 如果某个api存在这个数组里，说明该api暂时无法再次请求[刷新后重新重置为空]
let normalList=['/menu','/getUserPage']
/**
 * 阻止重复请求
 * @param {array} reqList - 请求缓存列表
 * @param {string} url - 当前请求地址
 * @param {function} cancel - 请求中断函数
 * @param {string} errorMessage - 请求中断时需要显示的错误信息
 */
const stopRepeatRequest = function (reqList, url, cancel, errorMessage) {
    const errorMsg = errorMessage || ''
    for (let i = 0; i < reqList.length; i++) {
        if (reqList[i] === url) {
            cancel(errorMsg)
            return
        }
    }
    reqList.push(url)
}
const stopRequest=function (cancel, errorMessage) {
    cancel(errorMessage)
}
/**
 * 允许某个请求可以继续进行
 * @param {array} reqList 全部请求列表
 * @param {string} url 请求地址
 */
const allowRequest = function (reqList, url) {
    for (let i = 0; i < reqList.length; i++) {
        if (reqList[i] === url) {
            reqList.splice(i, 1)
            break
        }
    }
}
uiService.interceptors.request.use((config) => {
    let cancel
    // 设置cancelToken对象
    config.cancelToken = new axios.CancelToken(function(c) {
        cancel = c
    })
    // 阻止重复请求。当上个请求未完成时，相同请求不会进行
    stopRepeatRequest(reqList, config.url, cancel, `${config.url} 请求被中断`)
    // 如果大于说明是token过期了
    if(!store.state.user.token||Date.now()/1000>jwt_decode(store.state.user.token)['exp']) {
        console.log('请求以拦截')
        store.commit('exitUser')
        router.push('/').then(
            r=>stopRequest(cancel, `token失效 ${config.url} 请求被中断`)
        )
    }
    else{
        if(config.url in normalList) {
            ElMessage({
                customClass: 'focus-message',
                message: '操作成功',
                type: 'success',
                duration: 1000
            });
        }
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
    return Promise.reject(error)
})

uiService.interceptors.response.use((response) => {
        setTimeout(() => {
            allowRequest(reqList, response.config.url)
        }, 1000) // 上一次请求返回后过1s才允许再次请求
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
            message:'操作失败,请重试',
            type:'error'});
        if (axios.isCancel(error)) {
            console.log(error.message);
        }
        else{
            setTimeout(() => {
                allowRequest(reqList, error.config.url)
            }, 1000) // 上一次请求返回后过1s才允许再次请求
            return Promise.reject(error);
        }
    }
);

export default uiService