import Vue from 'vue'
import VueRouter from 'vue-router'
const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/list/emps',
    name: 'emp',
    meta: {
      requireAuth: true
    },
    component: () => import('../views/demo1/EmpView.vue'),
  },
  {
    path: '/list/viss',
    name: 'vis',
    component: () => import('../views/demo1/VisView.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/access/record',
    name: 'rec',
    component: () => import('../views/demo1/RecordView.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/access/statistic',
    name: 'sta',
    component: () => import('../views/demo1/StatisticView.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/login',
    name: 'log',
    component: () => import('../views/demo1/LoginView.vue')
  },
  {
    path: '/main',
    name: 'main',
    component: () => import('../views/demo1/MainView.vue')
  },
  {
    path: '/list/vissed',
    name: 'vised',
    component: () => import('../views/demo1/VisedView.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/',
    redirect: '/login',
  },
]


const router = new VueRouter({
  routes
})

export default router
// export default new VueRouter({
//   base: "/",
//   routes
// })
