<template>
  <div>
    <!--<h1>{{message}}</h1>-->
    <!--<Element_View></Element_View>-->
    <!--<emp-view></emp-view>-->
    <router-view></router-view>
  </div>
</template>

<script>
// import Element_View from "./views/element/Element_View.vue"
// import EmpView from "./views/demo1/EmpView.vue";
// 解决ERROR ResizeObserver loop completed with undelivered notifications.

//问题的

const debounce = (fn, delay) => {
  let timer = null;
  
  return function () {
    let context = this;
    
    let args = arguments;
    
    clearTimeout(timer);
    
    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  };
};

// 解决ERROR ResizeObserver loop completed with undelivered notifications.

const _ResizeObserver = window.ResizeObserver;

window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 16);
    
    super(callback);
  }
};
export  default {
  // components: {EmpView},
  // components: {Element_View},
  comments:{},
  data(){
    return {
      message: 'hello vue'
    }
  },
  methods:{
  
  }
}
</script>

<style>

</style>
