<template>
  <div>
    <el-container >
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
            <!--    <el-menu-item index="3-1">环境数据监测</el-menu-item>-->
            <!--    <el-menu-item index="3-2">杂草检测</el-menu-item>-->
            <!--    <el-menu-item index="3-3">选项3</el-menu-item>-->
            <!--     <el-menu-item index="3-4">选项4</el-menu-item>-->
            <!--</el-submenu>-->
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>人员管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/list/emps' }">研究人员管理</el-breadcrumb-item>
          </el-breadcrumb>
          <br>
          <!--查询条件表单-->
          <el-form :inline="true"
                   :model="searchForm"
                   class="demo-form-inline">
            <el-form-item label="姓名">
              <el-input v-model="searchForm.name" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="编号">
              <el-input v-model="searchForm.id" placeholder="编号"></el-input>
            </el-form-item>
            <el-form-item label="身份">
              <el-select v-model="searchForm.identity" placeholder="身份">
                <el-option label="主管理员" value="1"></el-option>
                <el-option label="普通管理员" value="2"></el-option>
                <el-option label="研究人员" value="3"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onInquery">查询</el-button>
              <el-button @click="onClear">清空</el-button>
            </el-form-item>
          </el-form>
          <!--新增人员-->
          <el-button type="primary" round
                     @click="addFormVisible = true">
            <i class="el-icon-circle-plus-outline"> 新增研究人员 </i>
          </el-button>
          <el-button type="danger" round
                     @click="onDelete"
                     :disabled="multipleSelection.length === 0" >
            <i class="el-icon-delete"> 一键删除 </i>
          </el-button>
          <br> <br>
          <el-dialog title="人员信息" :visible.sync="addFormVisible">
            <el-form :model="addForm"
                     :rules="rules"
                     label-width="95px"
                     ref="addForm">
              <el-form-item label="姓名" :label-width="shortWidth" prop="name">
                <el-input class="inputCon"
                    v-model="addForm.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="性别" :label-width="shortWidth" prop="gender">
                <el-select v-model="addForm.gender" placeholder="选择性别">
                  <el-option label="男" value="1"></el-option>
                  <el-option label="女" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="年龄" :label-width="shortWidth" prop="age">
                <el-input class="inputCon"
                    v-model.number="addForm.age" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="手机号码" :label-width="shortWidth" prop="pnumber">
                <el-input class="inputCon"
                          v-model.number="addForm.pnumber" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="身份" :label-width="shortWidth" prop="identity">
                <el-select v-model="addForm.identity" placeholder="选择身份">
                  <el-option label="主管理员" value="1"></el-option>
                  <el-option label="普通管理员" value="2"></el-option>
                  <el-option label="研究人员" value="3"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="人员编号" :label-width="shortWidth" prop="id">
                <el-input class="inputCon"
                    v-model.number="addForm.id" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="人员照片" >
                <el-upload
                    ref="upload"
                    action=""
                    list-type="picture-card"
                    :on-preview="handlePictureCardPreview"
                    :on-remove="handleRemove"
                    :on-change="uploadPhoto"
                    :limit="1"
                    :file-list="filelist"
                    :auto-upload="false">
                  <i class="el-icon-plus"></i>
                  <template #tip>
                    <div style="font-size: 12px;color: #919191;">
                      单次限制上传一张图片
                    </div>
                  </template>
                </el-upload>
                <el-dialog v-model="previewVisible" style="line-height: 0;">
                  <img style="width: 100%;height: 100%"  :src="photoUrl" alt="" />
                </el-dialog>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="addFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="addSubmit()">确 定</el-button>
            </div>
          </el-dialog>
          <!--主体内容表格-->
          <el-table ref="multipleTable"
                    :data="tableData"
                    border
                    @selection-change="handleSelectionChange">
            <el-table-column
                label="全选"
                type="selection"
                width="55"
                :reserve-selection="true">
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="120">
            </el-table-column>
            <el-table-column prop="gender" label="性别" width="80">
              <template slot-scope="scope">
                {{scope.row.gender == 1 ? '男' : '女'}}
              </template>
            </el-table-column>
            <el-table-column prop="age" label="年龄" width="80">
            </el-table-column>
            <el-table-column prop="identity" label="身份" width="100">
              <template slot-scope="scope">
                {{scope.row.identity == 1 ? '主管理员' : scope.row.identity == 2 ? '普通管理员' : '研究人员'}}
              </template>
            </el-table-column>
            <el-table-column prop="id" label="人员编号" width="80">
            </el-table-column>
            <el-table-column prop="pnumber" label="手机号码" width="120">
            </el-table-column>
            <el-table-column prop="photo" label="照片">
              <template slot-scope="scope">
                <img :src="scope.row.photo"
                        alt="图片加载失败"
                        width="120px" height="70px" >
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="danger" size="mini" @click="onDeleteSingle(scope.row.id)" style="margin-right: 10px">删 除</el-button>
                <el-button type="primary" size="mini" @click="dialogFormVisible = true">编 辑</el-button>
                <el-dialog title="管理身份" :visible.sync="dialogFormVisible" :before-close="handleClose" append-to-body>
                  <el-form :model="onModify_form">
                    <el-form-item label="修改身份" :label-width="formLabelWidth">
                      <el-select v-model="onModify_form.identity" placeholder="修改身份">
                        <el-option label="主管理员" value="1"></el-option>
                        <el-option label="普通管理员" value="2"></el-option>
                        <el-option label="研究人员" value="3"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="onModify(scope.row.id)">保 存</el-button>
                  </div>
                </el-dialog>
              </template>
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
  // import {contain} from "echarts/types/src/scale/helper";
  
  export default {
    // computed: {
    //   upload() {
    //     return upload
    //   }
    // },
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
        photoUrl: "",
        formLabelWidth: '120px',
        shortWidth: '100px',
        dialogFormVisible: false,
        dialogVisible: false,
        previewVisible: false,
        onDeleteVisible: false,
        photodialogVisible: false,
        addFormVisible: false,
        multipleSelection: [],
        filelist: [],
        onModify_form: {    //修改人员表单
          identity: ""
        },
        addForm: {          //新增人员表单
          name: "",
          gender: "",
          age: "",
          pnumber: "",
          identity: "",
          id: "",
          photo: ""
        },
        rules: {            //新增表单的校验规则
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur'},
            {min: 1, message: '长度不得小于 1 ', trigger: 'blur'}
          ],
          gender: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
          age: [
            { required: true, message: '请输入年龄', trigger: 'blur'},
            { type: 'number', message: '年龄必须为数字'}, // 且不包括小数
            { pattern: /^(1[8-9]|[2-5][0-9]|60)$/, message: '年龄范围在18-60',trigger: 'blur'}
          ],
          pnumber: [
            { required: true, message: '请输入手机号码', trigger: 'blur'},
            {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
          ],
          identity: [
            { required: true, message: '请选择身份', trigger: 'change' }
          ],
          id: [
            { required: true, message: '请输入人员编号', trigger: 'blur'},
            { type: 'number', message: '编号必须为数字'}, // 且不包括小数
            { pattern: /^(1|2[0-9]|30)$/, message: '编号范围在1-30',trigger: 'blur'}
          ]
        },
        searchForm:{            //查询表单
          name:"",
          id:"",
          identity:""
        }
      }
    },
    methods: {
      async uploadPhoto(file,filelist){
        try{
          const formData = new FormData();
          formData.append('name',this.addForm.name)
          formData.append('photo', file.raw);
          const response = await axios.post('/list/emps/upload',formData,{
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          console.log("上传图片到:"+response.data.data)
          this.addForm.photo = response.data.data
        }catch (error){
          console.error(error)
        }
      },
      handleRemove(file, filelist) {
        console.log(file, filelist);
      },
      handlePictureCardPreview(file) {
        console.log(file.url);
        alert(file.url)
        this.previewVisible = true
        this.photoUrl = file.url
      },
      async addSubmit(){
        this.$refs["addForm"].validate(async (valid) => {
          if (valid) {
            try {
              // 执行添加记录的操作（这里使用Axios发送POST请求）
              await axios.post('/list/emps', {
                // 传递表单数据
                name: this.addForm.name,
                gender: this.addForm.gender,
                age: this.addForm.age,
                pnumber: this.addForm.pnumber,
                identity: this.addForm.identity,
                id: this.addForm.id,
                photo: this.addForm.photo
              });
              
              // 如果添加成功，显示成功通知
              this.$notify({
                title: '新增成功',
                message: '新增人员成功!',
                type: 'success'
              });
              // 隐藏表单
              this.addFormVisible = false;
              // 重新加载数据或其他操作
              this.onInquery(); //刷新数据
              this.addForm = []; //清空新增表单
            } catch (error) {
              console.error('添加记录时出错', error);
              // 如果添加失败，显示错误通知
              this.$notify.error({
                title: '新增失败',
                message: '新增人员失败!'
              });
            }
          } else {
            this.$notify.error({
              title: '新增失败',
              message: '人员信息有误!'
            });
          }
        });
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      async onModify(id) {
        try {
          // 使用后端提供的接口获取员工信息
          // const response = await axios.get(`/list/emps/${id}`);
          // const employeeId = response.data.data.id;
          // 更新员工的身份
          const updateResponse = await axios.put(`/list/emps`, {
            id: id,
            identity: this.onModify_form.identity // 更新员工的身份
          });
          
          if (updateResponse.status === 200) {
            this.$notify({
              title: '成功',
              message: '员工身份已更新',
              type: 'success',
              duration: 2000
            });
            // this.dialogVisible = false;
            this.dialogFormVisible = false;
            this.onInquery(); //刷新数据
            this.onModify_form = []; //清空修改表单
          } else {
            console.error('Failed to update employee identity');
          }
        } catch (error) {
          console.error('Error fetching or updating employee data', error);
        }
      },
      async onDeleteSingle(id){
        this.$confirm('确定删除所选员工?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(async () => {
          try{
            await axios.delete(`/list/emps/${id}`)
            this.$notify({
              title: '删除成功',
              message: '',
              type: 'success',
              duration: 2000
            })
            this.onInquery()  //重新加载页面的方法
          }catch (error){
            this.$message({
              type: 'error',
              message: '删除失败'
            });
            console.error(error)
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      async onDelete() {
        var arr = []
        //遍历点击选择的对象集合，拿到每一个对象的id添加到新的集合中
        this.multipleSelection.forEach(row=>arr.push(row.id))
        this.$confirm('确定删除所选员工?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(async () => {
              //批量删除
              try{
                await axios.delete(`/list/emps/${arr.join(',')}`)
                this.$notify({
                  title: '删除成功',
                  message: '',
                  type: 'success',
                  duration: 2000
                })
                this.onInquery()  //重新加载页面的方法
              }catch (error){
                this.$message({
                  type: 'error',
                  message: '删除失败'
                });
                console.error(error)
              }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
       async onInquery() {
         try {
           const response = await axios.get('/list/emps', {
             params: {
               page: this.currentPage,
               pageSize: this.pageSize,
               name: this.searchForm.name,
               id: this.searchForm.id,
               identity: this.searchForm.identity
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
      handleSizeChange(val) {
        this.pageSize = val
        this.currentPage = 1
        this.onInquery();
      },
      handleCurrentChange(val) {
        this.currentPage = val
        this.onInquery();
      },
      onClear(){
        this.searchForm = []
      },
      handleClose(done) {
        this.$confirm('确认取消修改？')
            // eslint-disable-next-line no-unused-vars
            .then(_ => {
              done();
            })
            // eslint-disable-next-line no-unused-vars
            .catch(_ => {});
      },
      // eslint-disable-next-line
    },
    mounted() {
      //发送异步请求，获取数据
      // axios.get("/list/emps").then((result)=>{
      //   this.tableData = result.data.data.rows;
      // })
      this.onInquery();
    }
  }
</script>

<style>

.inputCon{
  width: 220px;
}
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