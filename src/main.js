import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/store'
import ElementUI from 'element-ui'
import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts
import 'element-ui/lib/theme-chalk/index.css'
import axios from "axios";

Vue.config.productionTip = false
Vue.use(ElementUI)
axios.defaults.baseURL = '/proxy_url'
let self = this

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireAuth)) {// 需要登录
    if(window.localStorage.token && window.localStorage.isLogin === '1'){
      next()
    }
    else if (to.path !== '/login') {
      let token = window.localStorage.token;
      if (token === 'null' || token === '' || token === undefined){
        self.$notify.warning({
          title: '警告',
          message: '检测到您还未登录,请登录后操作！'
        });
          next({path: '/login'})
      }
      else{
          console.log(to)
          next()
      }
    }
    else {
      next()
    }
  }
  else {   // 不需要登录
    next()
  }
})

//添加请求拦截器
axios.interceptors.request.use(
    config =>{
      console.log(store.state.token)
      if(store.state.token){
          //存储在请求头 header 中的token键下
          config.headers['token'] =store.state.token
      }
      return config;
    },
    error =>{
      //对请求错误做什么
      return Promise.reject(error);
    })

//http reponse响应拦截器
axios.interceptors.response.use(
    response =>{
      return response;
    },
    error=>{
      if(error.response){
        switch(error.response.status){
          case 401:
            localStorage.removeItem('token');
            router.replace({
              path: '/login',
              query: {redirect: router.currentRoute.fullPath}//登录成功后跳入浏览的当前页面
            })
        }
      }
    })

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
