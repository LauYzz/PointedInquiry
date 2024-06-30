<template>
  <div class="login">
    <div class="login_form">
      <i class="el-icon-arrow-left" @click="backToWelcome()">返回</i>
      <p style="font-size: 36px;">登录对询</p>
      <el-tabs v-model="activeName">
        <el-tab-pane label="" name="first">
          <el-form :model="loginForm" :rules="rules" ref="loginForm" class="selectForm" size="medium">
            <br>
            <br>
            <el-form-item label="" prop="account" class="elItem" label-width="80px">
              <el-input type="text" autocomplete="off" v-model="loginForm.account" prefix-icon="el-icon-user-solid"
                placeholder="请输入用户名" class="infoIn"></el-input>
            </el-form-item>
            <div class="Deutsch">
              <el-form-item label="" prop="password" class="elItem" label-width="80px">
                <el-input type="password" autocomplete="off" v-model="loginForm.password" prefix-icon="el-icon-lock"
                  placeholder="请输入密码" class="infoIn"></el-input>
              </el-form-item>
            </div>
            <el-form-item class="btns">
              <el-button type="primary" @click="doLogin">登录</el-button>
              <el-button @click="resetLoginForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import register from '@/components/register';
import axios from 'axios';
import VueRouter from 'vue-router';
export default {
  data() {
    var validateAccount = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("账号不能为空"));
      } else if (value.length !== 11) {
        callback(new Error('用户名应为11位'));
      } else {
        callback();
      }
    };
    var validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      }
    };
    return {
      loginForm: {
        account: "",
        password: "",
      },
      accept: '',
      activeName: 'first',//默认显示登录页面
      rules: {
        account: [
          {
            validator: validateAccount,
            trigger: "blur",
          },
        ],
        password: [
          {
            validator: validatePassword,
            trigger: "blur",
          },
        ],
      },
    };
  },

  methods: {

    backToWelcome() {
      console.log("返回Welcome页面");
      this.$router.push('/Welcome');
    },
    linkToHome() {
      console.log("前往Home页面");
      this.$router.push('/Home');
    },
    doLogin: function () {

      let username = this.loginForm.account;
      let password = this.loginForm.password;
      let url = "/user/login";
      let data2 = {
        userId: username,
        passwd: password,
      }
      let token = '';

      var data = new FormData();
      data.append('userId', data2.userId);
      data.append('passwd', data2.passwd);

      var config = {
        method: 'post',
        url: '/user/login',
        data: data
      };

      var that = this;
      axios(config)
        .then(function (response) {
          that.accept = response;
          console.log(JSON.stringify(response.data));
          if (response.data.status == "1002") {
            alert("用户名或密码错误");
          }
          if (response.data.status == "100") {
            var storage = window.localStorage;
            storage.userId = data2.userId;
            console.log(storage.userId);
            that.linkToHome();
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log("接受的值为" + that.accept);
    },

    resetLoginForm() {
      this.$refs["loginForm"].resetFields();
    },
  }
  ,
  components: {
    register,
    VueRouter
  }
}
</script>


<style scoped lang='less'>
.login {
  background-image: url("../img/new_login-background.png");
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  margin: auto;

  .login_form {
    margin-left: 100px;
    margin-top: 240px;
    opacity: 100%;
    height: 55%;
    width: 95%;
    position: absolute;
    margin-left: 0px;
    padding: 10px;
    background: #fff;
    border-radius: 0px;
    box-shadow: 0 0 10px #ddd;

    .btns {
      display: flex;
      justify-content: center;
    }

    /deep/.el-form-item__error {
      color: #F56C6C;
      font-size: 12px;
      line-height: 1;
      padding-top: 4px;
      position: absolute;
      top: 100%;
      left: 0;
    }

    .infoIn {
      width: 100%;
    }

    .elItem {
      width: 80%;
    }
  }

  p {
    font-size: 20px;
    text-align: center;
    font-weight: 600;
  }

  .Deutsch {
    height: 60px;
  }

  /deep/ .el-input--medium .el-input__inner {
    height: 48px;
    line-height: 48px;
    font-size: 22px;
  }

  /deep/ .el-button--medium {
    padding: 10px 20px;
    font-size: 24px;
    border-radius: 8px;
    margin: 20px;
  }

  .el-input--medium {
    font-size: 20px;
  }

}
</style>
<style scoped lang='less'>
.login {
  background-image: url("../img/new_login-background.png");
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  margin: auto;

  .login_form {
    margin-left: 100px;
    margin-top: 240px;
    opacity: 100%;
    height: 55%;
    width: 95%;
    position: absolute;
    margin-left: 0px;
    padding: 10px;
    background: #fff;
    border-radius: 0px;
    box-shadow: 0 0 10px #ddd;

    .btns {
      display: flex;
      justify-content: center;
    }

    /deep/.el-form-item__error {
      color: #F56C6C;
      font-size: 12px;
      line-height: 1;
      padding-top: 4px;
      position: absolute;
      top: 100%;
      left: 0;
    }

    .infoIn {
      width: 100%;
    }

    .elItem {
      width: 80%;
    }
  }

  p {
    font-size: 20px;
    text-align: center;
    font-weight: 600;
  }

  .Deutsch {
    height: 60px;
  }

  /deep/ .el-input--medium .el-input__inner {
    height: 48px;
    line-height: 48px;
    font-size: 22px;
  }

  /deep/ .el-button--medium {
    padding: 10px 20px;
    font-size: 24px;
    border-radius: 8px;
    margin: 20px;
  }

  .el-input--medium {
    font-size: 20px;
  }

}
</style>
<style scoped lang='less'>
.login {
  background-image: url("../img/new_login-background.png");
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  margin: auto;

  .login_form {
    margin-left: 100px;
    margin-top: 240px;
    opacity: 100%;
    height: 55%;
    width: 95%;
    position: absolute;
    margin-left: 0px;
    padding: 10px;
    background: #fff;
    border-radius: 0px;
    box-shadow: 0 0 10px #ddd;

    .btns {
      display: flex;
      justify-content: center;
    }

    /deep/.el-form-item__error {
      color: #F56C6C;
      font-size: 12px;
      line-height: 1;
      padding-top: 4px;
      position: absolute;
      top: 100%;
      left: 0;
    }

    .infoIn {
      width: 100%;
    }

    .elItem {
      width: 80%;
    }
  }

  p {
    font-size: 20px;
    text-align: center;
    font-weight: 600;
  }

  .Deutsch {
    height: 60px;
  }

  /deep/ .el-input--medium .el-input__inner {
    height: 48px;
    line-height: 48px;
    font-size: 22px;
  }

  /deep/ .el-button--medium {
    padding: 10px 20px;
    font-size: 24px;
    border-radius: 8px;
    margin: 20px;
  }

  .el-input--medium {
    font-size: 20px;
  }

}
</style>
