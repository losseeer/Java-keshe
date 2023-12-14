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
          <el-menu :default-openeds="['2']">
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
              <el-menu-item index="2-3">
                <router-link to="/asd">选项3</router-link>
              </el-menu-item>
              <el-menu-item index="2-4">选项4</el-menu-item>
            </el-submenu>
            <!--<el-submenu index="3">-->
            <!--  <template slot="title"><i class="el-icon-setting"></i>大棚监测</template>-->
            <!--  <el-menu-item index="3-1">环境数据监测</el-menu-item>-->
            <!--  <el-menu-item index="3-2">杂草检测</el-menu-item>-->
            <!--  <el-menu-item index="3-3">选项3</el-menu-item>-->
            <!--  <el-menu-item index="3-4">选项4</el-menu-item>-->
            <!--</el-submenu>-->
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>出入统计</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/access/statistic' }">出入数据统计</el-breadcrumb-item>
          </el-breadcrumb>
          <br> <br>
          <div id="chartPie" class="pie-wrap"
               :style="{ width: '600px', height: '300px' }"></div>
          <div class="chartLine_wrap" >
            <div id="myChart" :style="{ width: '100%', height: '438px' }"></div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  
  </div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";
// import echarts from 'echarts';
require("echarts/theme/macarons"); //引入主题
export default {
  name: "HelloWorld",
  data() {
    return {
      // 下面是折线图相关数据
      date: [],
      a: [],
      a1: [],
      a2: [],
      a3: [],
      b: [],
      c: [],
      // 下面是饼状图相关数据
      dateRange: {
        start: new Date(),
        end: new Date(),
      },
      recentSevenDays: [],  // 用于存储最近七天的日期
      //饼状图数据模型 pieChartData
      pieChartData: [
        { name: "主管理员", value: 0 },
        { name: "普通管理员", value: 0 },
        { name: "研究人员", value: 0 },
        { name: "访客", value: 0 },
      ],
    };
  },
  methods: {
    //------------------------饼状图相关处理--------------------
    // 获取近七天日期
    getRecentSevenDays() {
      const today = new Date();
      return [...Array(7)].map((_, index) => {
        const day = new Date(today);
        day.setDate(today.getDate() - index);
        return day;
      });
    },
    // 获取饼状图数据
    async fetchPieChartData(identity, category){
      try {
        const response = await axios.get("/access/record", {
          params: {
            identity: identity
          }
        });
        const nameToFind = category;
        this.pieChartData.find((data) => data.name === nameToFind).value = response.data.data.total
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
    //绘制饼状图
    async drawPieChart() {
      //调用相应函数获取数据
      await this.fetchPieChartData('1', '主管理员');
      await this.fetchPieChartData('2', '普通管理员');
      await this.fetchPieChartData('3', '研究人员');
      await this.fetchPieChartData('4', '访客');
      
      this.chartPie = echarts.init(
          document.getElementById("chartPie"),
          "macarons"
      );
      this.chartPie.setOption({
        //显示在上面的文字
        tooltip: {
          trigger: "item",
          // formatter: "{a}<br/>{b}: <br/>{c}({d}%)",  其中 {a}指向name名称（访问来源）
          formatter: "{b}: <br/>{c}({d}%)",
        },
        legend: {
          data: ["主管理员", "普通管理员", "研究人员", "访客"],
          right: 500,
          orient: "vertical",
          // 下面注释的代码是控制分类放在哪个地方,需要体验的话，直接把上面的代码注释，把下面的代码解开注释即可
          //   data: ["主管理员", "普通管理员", "研究人员", "访客"],
          //   left: "center",
          //   top: "bottom",
          //   orient: "horizontal"
        },
        // 更新饼图数据
        series: [
          {
            name: "访问身份",
            type: "pie",
            radius: ["50%", "80%"],
            center: ["50%", "50%"],
            data: this.pieChartData,
            animationDuration: 2000, // 设置动画持续时间
            label: {
              show: false, // 控制是否显示指向文字
              position: "center",
              //以下代码可以代表指向小文字的
              //   show: true,
              //   formatter: "{b} : {c} ({d}%)",
              //   textStyle: {
              //     color: "#333",
              //     fontSize: 14,
              //   },
            },
          },
        ],
      });
    },
    //------------------------折线图相关处理----------------------
    
    // 规格化日期为 yyyy-mm-dd
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
    
    // 规格化日期时间为 yyyy-mm-dd HH:mm:ss
    convertToCustomFormat(isoDateString, isEnd) {
      const isoDate = new Date(isoDateString);
      // 获取年月日
      const year = isoDate.getFullYear();
      const month = String(isoDate.getMonth() + 1).padStart(2, "0");
      const day = String(isoDate.getDate()).padStart(2, "0");
      
      // 如果是 end，则时分秒设置为 "23:59:59"，否则设置为 "00:00:00"
      const hours = isEnd ? "23" : "00";
      const minutes = isEnd ? "59" : "00";
      const seconds = isEnd ? "59" : "00";
      // 构建新的日期字符串
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    // 计算内部人员总数和总人数
    calculateTotal(...arrays) {
      return arrays[0].map((_, index) => arrays.reduce((total, array) => total + array[index], 0));
    },
    // 获取对应身份和日期的访问人数
    async fetchData(identity, dateRange) {
      const promises = dateRange.map(async (day) => {
        try {
          const formattedBeginDate = this.convertToCustomFormat(day.toISOString(), false);
          const formattedEndDate = this.convertToCustomFormat(day.toISOString(), true);
          const response = await axios.get("/access/record", {
            params: {
              identity,
              begin: formattedBeginDate,
              end: formattedEndDate,
            },
          });
          return response.data.data.total;
        } catch (error) {
          console.error(`Error fetching ${identity} data for ${day}:`, error);
          return null;
        }
      });
      
      return Promise.all(promises);
    },
    // 调用接口函数获得完整列表数据
    async fetchLineChartData() {
      try {
        const today = new Date();
        const sevenDaysAgo = new Date(today);
        sevenDaysAgo.setDate(today.getDate() - 6);
        
        const dateRange = [...Array(7)].map((_, index) => {
          const day = new Date(sevenDaysAgo);
          day.setDate(sevenDaysAgo.getDate() + index);
          return day;
        });
        const [a1Response, a2Response, a3Response, bResponse] = await Promise.all([
          this.fetchData("1", dateRange),
          this.fetchData("2", dateRange),
          this.fetchData("3", dateRange),
          this.fetchData("4", dateRange),
        ]);
        this.date = dateRange.map((day) => this.formatDate(day));
        this.a1 = a1Response;
        this.a2 = a2Response;
        this.a3 = a3Response;
        // 计算 a和c 的数据
        this.a = this.calculateTotal(this.a1, this.a2, this.a3);
        this.b = bResponse;
        this.c = this.calculateTotal(this.a, this.b);
        this.drawLine()
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
    //绘制折线图
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById("myChart"));
      // 绘制图表
      myChart.setOption({
        title: {
          text: "每日访问人数（人次/天）",
          top: "30",
          left: "32",
          textStyle: {
            fontSize: 16, //字体大小
            color: "#333", //字体颜色
            fontWeight: "500",
          },
        },
        tooltip: {
          //触发类型：坐标轴触发
          trigger: "axis", //触发类型  'item'图形触发：散点图，饼图等无类目轴的图表中使用； 'axis'坐标轴触发；'none'：什么都不触发。
          axisPointer: {
            type: "line", //默认为line，line直线，cross十字准星，shadow阴影
            lineStyle: {
              type: "dashed",
              color: "#808BA9",
            },
          },
          backgroundColor: "#fff", //也可以通过设置rgba调节背景颜色与透明度
          color: "#333",
          borderWidth: "1",
          borderColor: "#D9E1F8",
          textStyle: {
            color: "#333",
            fontSize: "12",
            lineHeight: "17",
          },
          formatter: function(arg) {
            // 自定义提示
            return (
                arg[0].name +
                "<br>" +
                '<span style="margin:0 5px 2px 0;display:inline-block;width:6px;height:6px;border-radius:5px;background-color:' +
                arg[0].color +
                ';"></span>' +
                arg[0].seriesName +
                '<span style="margin-left:10px;">' +
                arg[0].data +
                "</span>" +
                "<br>" +
                '<span style="margin:0 5px 2px 0;display:inline-block;width:6px;height:6px;border-radius:5px;background-color:' +
                arg[1].color +
                ';"></span>' +
                arg[1].seriesName +
                '<span style="margin-left:10px;">' +
                arg[1].data +
                "</span>" +
                "<br>" +
                '<span style="margin:0 5px 2px 0;display:inline-block;width:6px;height:6px;border-radius:5px;background-color:' +
                arg[2].color +
                ';"></span>' +
                arg[2].seriesName +
                '<span style="margin-left:10px;">' +
                arg[2].data +
                "</span>"
            );
          },
        },
        legend: {
          icon: "circle",
          itemHeight: 10, //修改icon图形大小
          textStyle: {
            fontSize: 14,
            color: "#333",
          },
          x: "left", //可设定图例在左、右、居中
          y: "top", //可设定图例在上、下、居中
          padding: [81, 0, 0, 32], //可设定图例[距上方距离，距右方距离，距下方距离，距左方距离]
        },
        grid: {
          top: "145",
          left: "17",
          right: "17",
          bottom: "41",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          data: this.date,
          //使坐标轴刻度与标签对齐
          axisTick: {
            alignWithLabel: true,
          },
          axisLine: {
            // 改变x轴颜色
            lineStyle: {
              // color: "#D9E1F8",
              color: "#666",
              width: "1",
            },
          },
          axisLabel: {
            // 改变x轴字体颜色和大小
            formatter(val) {
              return "{a|" + `${val}` + "}";
            },
            rich: {
              a: {
                height: 40, // 设置字体行高
                color: "#666",
                fontSize: 14,
              },
            },
          },
        },
        yAxis: {
          min: 0,
          max: 200,
          type: "value",
          axisLine: {
            show: false, //y轴线消失
          },
          axisTick: {
            show: false, //y轴坐标点消失
          },
          boundaryGap: [0.1, 0], //有负数设置这个
          splitLine: {
            show: true,
            lineStyle: {
              type: "dashed",
              color: "#D9E1F8",
            },
          },
          axisLabel: {
            textStyle: {
              fontSize: 14,
              color: "#92A5E3",
            },
          },
        },
        series: [
          {
            name: "研究人员",
            type: "line", //bar:柱状 line:折线图
            data: this.a,
            color: "#00C5AB",
            // symbol: "circle", //折线点设置为实心点
            symbolSize: 8, //折线点的大小
          },
          {
            name: "访客",
            type: "line",
            data: this.b,
            color: "#3F53FF",
            symbolSize: 8, //折线点的大小
          },
          {
            name: "总人数",
            type: "line",
            data: this.c,
            color: "#8DF3E8",
            symbolSize: 8, //折线点的大小
          },
        ],
      });
    },
  },
  // 钩子方法
  mounted() {
    this.$nextTick(() => {
      this.drawPieChart();  //绘制饼状图
      this.recentSevenDays = this.getRecentSevenDays();  //获取近七天日期
      this.dateRange.start = this.recentSevenDays[0];    // 获取第一天
      this.dateRange.end = this.recentSevenDays[this.recentSevenDays.length - 1];    //获取最后一天
    });
    this.fetchLineChartData()
  }
};
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
.chartLine_wrap {
  width: 1000px;
  height: 438px;
  background: #fff;
}
.pie-wrap {
  width: 100%;
  height: 126px;
}
#h1{
  color: black;
}
</style>