import userLogin from '../views/userLogin.vue'
import index from  '../views/Index.vue'
import register from '../views/register.vue'
import whiteHtml from '../views/supplierAllBack.vue'
import richTextFormat from '../views/richTextFormat.vue'
import rolePage from '../views/rolePage.vue'

const routes = [
    { path: '/', meta:{title: '登录页面'}, component: userLogin },
    { path: '/index', meta:{requiresAuth: true,title: '主页面'}, component: index,children:{
            path: '/richTextFormat', component: richTextFormat
        } },
    { path: '/register', meta:{requiresAuth: true,title: '用户管理页面'}, component: register },
    { path: '/white', meta:{title: '空白页面'}, component: whiteHtml },
    { path: '/rolePage', component: rolePage },
    { path: '/richTextFormat', component: richTextFormat }
]

export default routes;