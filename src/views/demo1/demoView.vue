



<template>
  <div>
    <div id="myChart" style="height: 400px;"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";

export default {
  data() {
    return {
      date: [],
      a: [],
      b: [],
      c: [],
      a1: [],
      a2: [],
      a3: [],
    };
  },
  mounted() {
    this.$nextTick(async () => {
      await this.fetchDataForLineChart();
      this.drawLine();
    });
  },
  methods: {
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
          return response;
        } catch (error) {
          console.error(`Error fetching ${identity} data for ${day}:`, error);
          return null;
        }
      });
      
      return Promise.all(promises);
    },
    // 调用接口函数获得完整列表数据
    async fetchDataForLineChart() {
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
        this.a1 = a1Response.data.data.map((item) => item.total);
        this.a2 = a2Response.data.data.map((item) => item.total);
        this.a3 = a3Response.data.data.map((item) => item.total);
        // 计算 a和c的数据
        this.a = this.calculateTotal(this.a1, this.a2, this.a3);
        this.b = bResponse.data.data.map((item) => item.total);
        this.c = this.calculateTotal(this.a, this.b);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
    
    drawLine() {
      const myChart = echarts.init(document.getElementById("myChart"));
      
      myChart.setOption({
        title: {
          text: "近七天人数统计",
          left: "center",
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
          },
        },
        legend: {
          data: ["a", "b", "c"],
          top: 30,
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            data: this.date,
          },
        ],
        yAxis: [
          {
            type: "value",
            min: 0,
            max: 150,
          },
        ],
        series: [
          {
            name: "内部人员",
            type: "line",
            data: this.a,
          },
          {
            name: "访客",
            type: "line",
            data: this.b,
          },
          {
            name: "总人数",
            type: "line",
            data: this.c,
          },
        ],
      });
    },
  },
};
</script>

<style>
/* Add your custom styles here */
</style>
