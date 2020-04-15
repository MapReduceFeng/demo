import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import store from '../store'

Vue.use(VueRouter)
const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/About.vue'),
    children: [
      {
        name: 'aa',
        path: 'aa',
        components: {menus: () => import("@/components/about/a.vue")},
        meta: {
          auth: false,
          roles: ['admin']
        },
        children: []
      },
      {
        name: 'bb',
        path: 'bb',
        components: {menus: () => import("@/components/about/b.vue")},
        meta: {
          auth: false,
          roles: ['admin']
        },
        children: []
      },
      {
        name: 'schedule',
        path: 'schedule',
        components: {menus: () => import("@/components/about/schedule.vue")},
        meta: {
          auth: false,
          roles: ['admin']
        },
        children: []
      },
      {
        name: 'koa2',
        path: 'koa2',
        components: {menus: () => import("@/components/about/koa2.vue")},
        meta: {
          auth: false,
          roles: ['admin']
        },
        children: []
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  console.log('url: ' + to.path + '是否检证: ' + to.meta.auth)
  if (to.matched.some(record => record.meta.auth)) { // console.log('要检证')
    if (sessionStorage.getItem('access_token') !== 'init') {
      console.log('有权限')
      next()
    } else {
      console.log('无权限')
      next({path: '/', query: {redirect: to.fullPath}})
    }
  } else {
    console.log('不要检证')
    next()
  }
  if (to.path.toString() != '/') {
    store.commit('setLocation', to.path)
  }
})
export default router
