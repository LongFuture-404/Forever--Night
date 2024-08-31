import { createApp } from 'vue'
import store from './store'
import App from './App.vue'
import router from './router';
import $ from 'jquery'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.min.js'
import './assets/root.css'
// @ts-ignore
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//配置请求数据
import axios,{AxiosInstance} from  'axios'
import service from './utils/Service'
import uiService from './utils/uiService'
// @ts-ignore
import qs from 'qs'
import 'font-awesome/css/font-awesome.min.css'

// //ts全局配置Axios
// declare module '@vue/runtime-core' {
//     interface ComponentCustomProperties {
//         $axios: AxiosInstance;
//     }
// }

const app = createApp(App)
app.config.globalProperties.$ = $
axios.defaults.baseURL = '/uiApi';
app.config.globalProperties.$axios = axios
app.config.globalProperties.$qs = qs
app.config.globalProperties.service=service
app.config.globalProperties.uiService=uiService

//【给页面设置标题】vue页面中使用 v-title data-title="添加新闻"
// app.directive('title', {
//     mounted(el){
//         document.title = el.dataset.title
//     }
// })
app.use(ElementPlus)
app.use(store)
app.use(router).mount('#app')
