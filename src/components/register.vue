<template>
  <div class="register">
    <div class="register_form">
      <i class="el-icon-arrow-left" @click="backToWelcome()">返回</i>
      <p style="font-szie:36px">注册账号</p>

      <el-tabs v-model="activeName">
        <el-tab-pane label="" name="first">

          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm" size="medium">
            <br>
            <br>
            <el-form-item label="" prop="name" class="elItem" label-width="80px">
              <el-input type="text" autocomplete="off" v-model="ruleForm.name" prefix-icon="el-icon-user-solid"
                placeholder="请输入手机号" class="infoIn"></el-input></el-form-item>
            <el-form-item label="" prop="pass" class="elItem" label-width="80px"><el-input type="password"
                autocomplete="off" v-model="ruleForm.pass" prefix-icon="el-icon-lock" placeholder="请输入密码"
                class="infoIn"></el-input></el-form-item>
            <div class="Deutsch">
              <el-form-item label="" prop="checkPass" class="elItem" label-width="80px"><el-input type="password"
                  autocomplete="off" v-model="ruleForm.checkPass" prefix-icon="el-icon-lock" placeholder="再次输入密码"
                  class="infoIn"></el-input></el-form-item>
            </div>
            <el-form-item class="btns">
              <el-button type="primary" @click="doRegister()">注册</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>

          <el-dialog :visible.sync="dialogVisible" width="95%" :before-close="handleClose">
            <div style="text-align:center">
              <span style="font-size:18px;">注册成功！</span>
              <br><br><br><br>
              <el-button primary @click="ToLogin()">去登录</el-button>
            </div>
          </el-dialog>

        </el-tab-pane>
      </el-tabs>
      
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };

    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入的密码不一致!"));
      } else {
        callback();
      }
    };



    return {
      activeName: "first",
      ruleForm: {
        name: "",
        pass: "",
        checkPass: "",
      },

      dialogVisible: false,
      rules: {
        name: [
          { required: true, message: "请输入您的名称", trigger: "blur" },
          { min: 11, max: 11, message: "长度应为11个字符", trigger: "blur" },
        ],
        pass: [{ required: true, validator: validatePass, trigger: "blur" }],
        checkPass: [
          { required: true, validator: validatePass2, trigger: "blur" },
        ],
      },
    };
  },

  methods: {
    ToLogin() {
      this.$router.push('/login');
    },
    backToWelcome() {
      console.log("返回Welcome页面");
      this.$router.push('/Welcome');
    },
    doRegister() {
      console.log(this.ruleForm.name);
      console.log(this.ruleForm.pass);
      if (this.ruleForm.pass != this.ruleForm.checkPass) {
        return;
      }
      var data = new FormData();
      data.append("userId", this.ruleForm.name);
      data.append("passwd", this.ruleForm.pass);
      var config = {
        method: 'post',
        url: '/user/register',
        data: data
      }
      var that = this;
      axios(config)
        .then(function (response) {
          console.log(JSON.stringify(response.data));
          that.dialogVisible = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    handleClose(done) {
      done();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$message({
            type: "success",
            message: "注册成功",
          });
          // this.activeName: 'first',
        } else {
          console.log("提交了错误的表单");
          return false;
        }
      });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>
<style scoped lang="less">
.register {
  background-image: url("../img/new_login-background.png");
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  margin: auto;

  .register_form {
    margin-left: 100px;
    margin-top: 240px;
    opacity: 100%;
    height: 57%;
    width: 95%;
    position: absolute;
    margin-left: 0px;
    padding: 10px;
    background: #fff;
    border-radius: 0px;
    box-shadow: 0 0 10px #ddd;
  }

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

  p {
    font-size: 30px;
    text-align: center;
    font-weight: 600;
  }

  .infoIn {
    width: 100%;
  }

  .elItem {
    width: 80%;
  }

  /deep/ .el-input--medium .el-input__inner {
    height: 48px;
    line-height: 48px;
    font-size: 22px;
  }

  .Deutsch {
    height: 60px;
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