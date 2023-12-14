<template>
  <div v-loading="loading" element-loading-text="登录中..."
       element-loading-spinner="el-icon-loading"
       element-loading-background="rgba(0, 0, 0, 0.6)"
       id="login-container">
      
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <!-- 头像区域 -->
      <div v-if="TxStatus" class="avatar-box">
        <el-image style="width: 115%; height:115%"
                  :src="require('../avatar.png')">
        </el-image>
      </div>
      
      <div class="title-container">
        <h3 class="title">访客管理系统</h3>
      </div>
      
      <el-form-item prop="pnumber">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input ref="pnumber" v-model="loginForm.pnumber" placeholder="手机号" name="pnumber" type="text" tabindex="1" auto-complete="on" />
      </el-form-item>
      
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType" placeholder="密码" name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <div class="login_btn">
        <el-button class="login_btn" type="primary" style="width:100%;margin-bottom:20px;" @click.native.prevent="handleLogin">
            登录
        </el-button>
      </div>
      <div class="tips">
        <span style="margin-right:20px;">如果您还没有账号请先 <span style="color:#409EFF;cursor:pointer" @click="register">注册</span></span>
      </div>
    
    </el-form>
  </div>
</template>

<script>
// 引入去除空格工具
import { validPnumber } from '@/util/validate'
import axios from "axios";

export default {
  name: 'LoginView',
  data() {
    const validatePnumber = (rule, value, callback) => {
      if (!validPnumber(value)) {
        callback(new Error('请输入合法手机号！'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码最少为6位字符！'))
      } else {
        callback()
      }
    }
    return {
      // 头像状态
      TxStatus: true,
      loginForm: {
        pnumber: '',
        password: ''
      },
      // 登录规则
      loginRules: {
        pnumber: [{ required: true, trigger: 'blur', validator: validatePnumber }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    // 登录业务
    async handleLogin() {
      await this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          this.loading = true;
          try {

            
            // 使用 async/await 发送登录请求
            const response = await axios.post('/login', this.loginForm);
            console.log(response)
            if (!response.data.code) throw new Error('Login error');
            // 登录成功的处理
            this.loading = false;
            this.$store.commit('$_setToken', response.data.data)
            await this.$router.push({path: this.redirect || '/list/emps'});
            this.$notify.success({
              title: '成功',
              message: '登录成功！'
            });
          } catch (error) {
            // 登录失败的处理
            console.error('Login error:', error);
            this.$notify.error({
              title: '错误',
              message: '手机号或密码输入错误！'
            });
            this.loading = false;
          }
        } else {
          console.log('Error: Form validation failed!');
          return false;
        }
      });
    },
    // 注册业务
    register() {
      console.log('123')
      this.$router.push({ name: 'register' })
    }
  }
}
</script>

<style lang="scss">
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;
@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  #login-container .el-input input {
    color: $cursor;
  }
}
#login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
    
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;
      
      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }
  
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style>
.login_btn{
  text-decoration: none;
  color: #303133;
}

body{
  margin: 0;
}

</style>
<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

#login-container {
  min-height: 767px;
  width: 100%;
  overflow: hidden;
  background-image: url("F:/JavaWeb/Vue1/vue1_project/src/assets/backg.png") ;
  background-size: cover;
  //padding: 0px 0px 0px 0px;
  // 头像
  .avatar-box {
    margin: 0 auto;
    width: 120px;
    height: 120px;
    border-radius: 50%;
    border: 1px solid #409eff;
    box-shadow: 0 0 10px #409eff;
    position: relative;
    bottom: 20px;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
    }
  }
  
  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
    
  }
  
  .tips {
    font-size: 18px;
    text-align: center;
    color: #000;
    margin-bottom: 10px;
  }
  
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
  
  .title-container {
    position: relative;
    
    .title {
      font-size: 30px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: 500;
    }
  }
  
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>