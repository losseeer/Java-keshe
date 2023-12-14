<template>
  <div>
    <el-container style="height: 700px; border: 1px solid #eee">
      <el-header style="font-size: 40px; background-color: rgb(238,241,246)">
        <router-link to="/list/emps">
          <span id="h1">
            访客管理系统
          </span>
        </router-link>
        <el-dropdown trigger="click" placement="bottom-end" style="float: right">
  <span class="el-icon-arrow-down">
    <el-image style="width: 30%; height:30%; margin:4px 5px 2px 5px;"
              :src="require('../avatar.png')">
    </el-image>
  </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item class="clearfix">
              <router-link to="/list/viss">
                <i class="el-icon-message"> 消息 </i>
              </router-link>
              <el-badge class="mark" :value=msgnum />
            </el-dropdown-item>
            <el-dropdown-item class="clearfix">
              <router-link to="/login"> <i class="el-icon-switch-button"> 退出 </i> </router-link>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu :default-openeds="['1']">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-news"></i>人员管理</template>
              <el-menu-item index="1-1">
                <router-link to="/list/emps">研究人员管理</router-link>
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/list/viss">访客申请处理</router-link>
              </el-menu-item>
              <el-menu-item index="1-3">
                <router-link to="/list/vissed">访客申请记录</router-link>
              </el-menu-item>
              <el-menu-item index="1-4">选项4</el-menu-item>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title"><i class="el-icon-view"></i>出入统计</template>
              <el-menu-item index="2-1">
                <router-link to="/access/record">人员出入记录</router-link>
              </el-menu-item>
              <el-menu-item index="2-2">
                <router-link to="/access/statistic">出入数据统计</router-link>
              </el-menu-item>
              <el-menu-item index="2-3">选项3</el-menu-item>
              <el-menu-item index="2-4">选项4</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title"><i class="el-icon-setting"></i>大棚监测</template>
              <el-menu-item index="3-1">环境数据监测</el-menu-item>
              <el-menu-item index="3-2">杂草检测</el-menu-item>
              <el-menu-item index="3-3">选项3</el-menu-item>
              <el-menu-item index="3-4">选项4</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <div class="block">
            <span class="demonstration"></span>
            <el-carousel height="500px">
              <el-carousel-item v-for="item in carouseImage" :key="item.src">
                <img :src="item"/>
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "MainIndex",
  data(){
    return {
      carouseImage: [
        require('F:/JavaWeb/Vue1/vue1_project/src/assets/carouseImage/image1.jpg'),
        require('F:/JavaWeb/Vue1/vue1_project/src/assets/carouseImage/image2.jpg'),
        require('F:/JavaWeb/Vue1/vue1_project/src/assets/carouseImage/image3.jpg'),
        require('F:/JavaWeb/Vue1/vue1_project/src/assets/carouseImage/image4.jpg'),
      ],
      msgnum: 0
    }
  },
  mounted () {
    // 首次加载时,初始化高度
    axios.get('list/viss').then(response=>{
      this.msgnum = response.data.data.total
    })
    this.screenWidth = window.innerWidth
    this.bannerHeight = 600 / 1550 * this.screenWidth
    // 窗口大小发生改变
    window.onresize = () => {
      this.screenWidth = window.innerWidth
      this.bannerHeight = 600 / 1550 * this.screenWidth
    }
  }
}
</script>

<style>
.router-link-active {
  text-decoration: none;
  color: #409EFF;
}
a {
  text-decoration: none;
  color: #303133;
}
#h1{
  color: black;
  margin-right: 780px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
img{
  width: 100%;
  height: inherit;
}
#h1{
  color: black;
}
</style>