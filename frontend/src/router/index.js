import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect:'/home'
    },
    {
      path:'/',
      name:'home',
      component:resolve=>require(['../components/Home'],resolve),
      meta:{title:'首页'},
      children:[
        {
          path:'/home',
          component:resolve=>require(['../pages/home'],resolve),
          meta:{title:'首页'}
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: resolve=>require(['../pages/login'],resolve),
      meta:{title:'登录'}
    },
  ]
})
