import {createApp} from 'vue'
import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router'
import routes from './routes'
import store from "../store";
import axios from 'axios'
import jwt_decode from "jwt-decode";

const app = createApp({})

app.config.globalProperties.axios = axios
// 解决重复路由报异常问题
// @ts-ignore
const originalPush = axios.push;
// @ts-ignore
axios.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to, from, next) => {
    if (to.meta.requiresAuth) {
        store.state.isIndex='true'
        if (!store.state.user.token||Date.now()/1000>jwt_decode(store.state.user.token)['exp']) {
            document.title = '登录页面'
            // 跳转到登录页面
            next({
                name: '',
                query: { // 通过 url 传递查询字符串参数
                    redirect: to.fullPath // 把登录成功需要返回的页面告诉登录页面
                }
            })
        } else {
            // @ts-ignore
            document.title = to.meta.title ? to.meta.title : '';
            next() // 允许通过
        }
    } else {
        store.state.isIndex='false'
        // @ts-ignore
        document.title = to.meta.title ? to.meta.title : '';
        next() // 允许通过
    }
})
export default router