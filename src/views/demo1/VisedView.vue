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
            <el-breadcrumb-item>人员管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/list/vissed' }">访客申请记录</el-breadcrumb-item>
          </el-breadcrumb>
          <br> <br>
          <!--查询条件表单-->
          <el-form :inline="true" :model="searchForm" class="demo-form-inline">
            <el-form-item label="姓名">
              <el-input v-model="searchForm.name" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="编号" >
              <el-input v-model="searchForm.id" placeholder="编号" ></el-input>
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="searchForm.status" placeholder="审批状态">
                <el-option label="通过" value="2"></el-option>
                <el-option label="不通过" value="3"></el-option>
                <el-option label="已来访" value="4"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="审批人">
              <el-input v-model="searchForm.approver" placeholder="审批人"></el-input>
            </el-form-item>
            <!--日期查询-->
            <el-form-item label="访问日期">
              <el-date-picker
                  v-model="searchForm.time"
                  type="daterange"
                  format="yyyy-MM-dd"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  @change="dateFormat">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onInquery">查询</el-button>
              <el-button @click="onClear">清空</el-button>
            </el-form-item>
          </el-form>
          <!--主体内容表格-->
          <el-table :data="tableData" border>
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-form-item label="备注:">
                    <span>{{ props.row.memo }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="100">
            </el-table-column>
            <el-table-column prop="gender" label="性别" width="100">
              <template slot-scope="scope">
                {{scope.row.gender == 1 ? '男' : '女'}}
              </template>
            </el-table-column>
            <el-table-column prop="age" label="年龄" width="100">
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
                {{ scope.row.status === 2 ? '通过' : scope.row.status === 3 ? '不通过' : '已来访' }}
              </template>
            </el-table-column>
            <el-table-column prop="id" label="人员编号" width="100">
            </el-table-column>
            <el-table-column prop="pnumber" label="手机号码" width="100">
            </el-table-column>
            <el-table-column prop="vdate" label="访问日期" width="100">
            </el-table-column>
            <el-table-column prop="approver" label="审批人" width="100">
            </el-table-column>
            <el-table-column label="编辑状态">
              <el-button type="primary" size="mini" @click="dialogFormVisible=true">编辑</el-button>
              <el-dialog title="管理身份" :visible.sync="dialogFormVisible" :before-close="handleClose" append-to-body>
                <el-dialog
                    title="提示"
                    :visible.sync="dialogVisible"
                    width="30%"
                    append-to-body>
                  <span>确认保存修改？</span>
                  <span slot="footer" class="dialog-footer">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="dialogVisible = false, dialogFormVisible = false;onModify()">确 定</el-button>
                      </span>
                </el-dialog>
                <el-form :model="form">
                  <el-form-item label="修改审批状态" :label-width="formLabelWidth">
                    <el-select v-model="form.status" placeholder="修改状态">
                      <el-option label="不通过" value="3"></el-option>
                      <el-option label="通过" value="2"></el-option>
                    </el-select>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogFormVisible = false">取 消</el-button>
                  <el-button type="primary" @click="dialogVisible = true">保 存</el-button>
                </div>
              </el-dialog>
            </el-table-column>
          </el-table>
          <br> <br>
          <!--分页条-->
          <el-pagination
              background
              layout="total, sizes, prev, pager, next, jumper"
              :current-page="currentPage"
              :page-sizes="pageSizes"
              :page-size="pageSize"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :total="total">
          </el-pagination>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data(){
    return  {
      tableData: [],
      msgnum: 0,
      currentPage:1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      total:30,
      // 个数选择器（可修改）
      pageSizes:[5,10,15,20],
      // 默认每页显示的条数（可修改）
      pageSize:10,
      form: {
        status: ""
      },
      searchForm:{
        name:"",
        id:"",
        status:"",
        approver:"",
        time:[]
      },
      dialogFormVisible: false,
      dialogVisible: false,
      formLabelWidth: '120px'
    }
  },
  methods: {
    turnHelper(d){
      //将中国标准时间转换为"yyyy-mm-dd"形式的辅助函数
      var year = d.getFullYear();
      var month = (d.getMonth() + 1).toString().padStart(2, '0'); // 添加零
      var day = d.getDate().toString().padStart(2, '0'); // 添加零
      return year + '-' + month + '-' + day
    },
    dateFormat(picker){
      this.searchForm.time[0] = this.turnHelper(picker[0])
      this.searchForm.time[1] = this.turnHelper(picker[1])
      //转换后的begin和end才可以作为参数成功传入后端
    },
    
    onModify() {
      this.$message({
        message: '修改成功！',
        type: 'success'
      })
    },
    async onInquery() {
      try {
        const response = await axios.get('/list/vissed', {
          params: {
            page: this.currentPage,
            pageSize: this.pageSize,
            name: this.searchForm.name,
            id: this.searchForm.id,
            status: this.searchForm.status,
            approver: this.searchForm.approver,
            begin: this.searchForm.time[0],
            end: this.searchForm.time[1]
          },
        });
        this.tableData = response.data.data.rows;
        this.total = response.data.data.total;
        const res = await axios.get('list/viss')
        this.msgnum = res.data.data.total
      } catch (error) {
        console.error(error);
      }
    },
    onClear(){
      this.searchForm.time = []
      this.searchForm.name = ''
      this.searchForm = []
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.onInquery();
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.onInquery();
    },
    handleClose(done) {
      this.$confirm('确认取消修改？')
          // eslint-disable-next-line no-unused-vars
          .then(_ => {
            done();
          })
          // eslint-disable-next-line no-unused-vars
          .catch(_ => {});
    }
  },
  mounted() {
    //发送异步请求，获取数据
    this.onInquery()
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
}
</style>