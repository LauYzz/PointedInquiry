<template>
    <div class="bg">
      <link rel="stylesheet icon" href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css">
      <div class="icons">
        <span class="com" @click="changeView(1)"><i class="iconfont icon-shangyehezuo-copy"></i>
          <div style="text-align:center;" >心理</div>
        </span>
        <span class="com" style="margin-left:50px;margin-right:50px;"  @click="changeView(2)"><i class="iconfont icon-bianji_edit"></i>
          <div style="text-align:center;">学业</div>
        </span>
        <span class="com" @click="changeView(3)"><i class="iconfont icon-gongzuotai_workbench"></i>
          <div style="text-align:center;">就业</div>
        </span>
      </div>
      <br>
      <br>
      <hr class="hr_solid">
  
      <br>
      <el-card :body-style="{ padding: '0px' }" v-for="(item, index) in Experts" :key="index">
        <div style="padding: 14px;" :key="index" @click="linkToExpertDetail(item, index)">
          <div>
            <span class="name">{{ Experts[index].realName }}&nbsp;&nbsp;</span>
            <br>
            <span class="title">{{ Experts[index].job }}</span>
          </div>
          <Test5 :fatherScore="Experts[index].rating" class="rating" />
  
          <div class="description">
            <div class="subdes">
              <span class="topic" v-for="(item, subIndex) in Experts[index].topic" :key="subIndex"><i
                  style="color:gray; font-size: 20px;">#&nbsp;</i>{{ Experts[index].topic[subIndex].title }}<br></span>
            </div>
            <div>
              <span class="price">最低价格：{{ Experts[index].price }}元/小时</span>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </template>

<style scoped lang="less">
.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}


.icons {
  border: 1px;
  padding: 10px;
  border-radius: 2px;
  background-color: white;
  display: flex;
  justify-content: center;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 18px;
  color: royalblue;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #FFF;
  overflow: hidden;
  transition: .3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>

<script>
import axios from "axios";
import Vue from 'vue';
import Vuex from 'vuex';
import Expert from './expert.vue';
import Test4 from './Test4.vue';
import Test5 from './Test5.vue';
Vue.use(Vuex);


export default {
  props:
    ['value']
  ,
  data() {
    return {
      Experts: [],
      Topics: [],
      Price: [],
      currentDate: new Date(),
      length: Number,
    };
  },
  components: {
    Expert,
    Test4,
    Test5
  },
  methods: {
    async queryData() {
      var config = {
        method: 'get',
        url: '/expert/getAll',
        params: {
          type: 1
        }
      }
      var res = await axios(config)
      this.Experts = res.data.data
      return res;
    },
    getExpert(item, index) {
      console.log(this.Experts[index].phone);
      var id = this.Experts[index].phone;
      return id;
      /*this.$router.push({
        path: `/expert/${id}`
      })*/
    },
    linkToExpertDetail(item, index) {
      var id = this.getExpert(item, index);
      console.log("跳转到" + id + "对应的行家信息详情");
      this.$router.push(`/ExpertDetailInfo/${id}`);
    },

    async changeView(index) {
      console.log("changeview")
      const vm = this; // 存储外部的 this
      const config = {
        method: 'get',
        url: '/expert/getAll',
        params: {
          type: index
        }
      }
      var res = await axios(config)
      this.Experts = res.data.data
    },

    async getTopics(length) {
      for (var i = 0; i < length; i++) {
        var data = new FormData();
        data.append('expertId', this.Experts[i].phone);
        var config = {
          method: 'post',
          url: '/topic/getById',
          data: data
        }
        var that = this;
        await axios(config)
          .then(function (response) {
            that.$set(that.Experts[i], "topic", response.data.data.topics);
            that.$set(that.Experts[i], "price", response.data.data.minPrice[0]);
            that.$set(that.Experts[i], "type", response.data.data.topics[0].type);

          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },

    async getCity(length) {
      for (var i = 0; i < length; i++) {
        var data = new FormData();
        data.append('userId', this.Experts[i].phone);
        var config = {
          method: 'post',
          url: '/user/myInfo',
          data: data
        }
        var that = this;
        await axios(config)
          .then(function (response) {
            console.log(response.data.data.city);
            that.$set(that.Experts[i], "province", response.data.data.province);
            that.$set(that.Experts[i], "city", response.data.data.city);
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },



    //从接口获取Topic信息，初始化给Topics
    //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）

  },
  mounted() {
    this.queryData().then(res => {
      this.Experts = res.data.data;
      this.getTopics(this.Experts.length);
      this.getCity(this.Experts.length);
    })

    /*
            for (var i = 0; i < that.length; i++)
    {
      var data=new FormData();;
      data.append('expertId', that.Experts[i].phone);
      console.log(data);
    }
    
        var config1={
            method: 'post',
            url: '/topic/getById',
            data: data
        }
          axios(config1)
            .then(function (response) {
              console.log(JSON.stringify(response.data));
            })
            .catch(function (error) {
              console.log(error);
            });*/
    /*
        axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
              this.Experts = res.data;
          var length = this.Experts.length;
            })*/
  }
}
</script>

<script setup>


</script>
<style scoped lang="less">
.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}


.icons {
  border: 1px;
  padding: 10px;
  border-radius: 2px;
  background-color: white;
  display: flex;
  justify-content: center;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 18px;
  color: royalblue;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #FFF;
  overflow: hidden;
  transition: .3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
<style scoped lang="less">
.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}


.icons {
  border: 1px;
  padding: 10px;
  border-radius: 2px;
  background-color: white;
  display: flex;
  justify-content: center;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 18px;
  color: royalblue;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #FFF;
  overflow: hidden;
  transition: .3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
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
<template>
    <div>
      <Header></Header>
      <div style="height:710px;overflow: scroll;" >
          <el-card>
            <el-descriptions class="margin-top" title="我的行家信息" :column="1" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-phone"></i>
                  ID
                </template>
{{ user.expertId }}
</el-descriptions-item>
<el-descriptions-item>
  <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  真实姓名
                </template>
  {{ user.realName }}
</el-descriptions-item>
<el-descriptions-item>
  <template slot="label">
                  <i class="el-icon-user"></i>
                  描述
                </template>
  {{ user.description }}
</el-descriptions-item>

<el-descriptions-item>
  <template slot="label">
                  <i class="el-icon-data-board"></i>
                  行家分类
                </template>
  {{ user.type===1?"心理":user.type ===2?"学业":"就业" }}
</el-descriptions-item>
<el-descriptions-item>
  <template slot="label">
                  <i class="el-icon-data-board"></i>
                  职位
                </template>
  {{ user.job }}
</el-descriptions-item>

<el-descriptions-item>
  <template slot="label">
                  <i class="el-icon-thumb"></i>
                  用户评分
                </template>
  {{ user.rating }}
</el-descriptions-item>


</el-descriptions>
</el-card>

<el-button @click="addNewTopic" type="primary" class="el-icon-circle-plus-outline" style="margin: 10px;">
  新建话题</el-button>

<el-card v-for="(item,index) in user.topics" :key="index" style="margin-bottom: 30px;" v-if="item.state ==='已审核'">
  <el-descriptions class="margin-top" :title="'话题' + (index + 1)" :column="1" border>
    <el-descriptions-item>
      <template slot="label">
                  <i ></i>
                  话题名
                </template>
      {{ item.title }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
                  <i ></i>
                  话题描述
                </template>
      {{ item.text }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
                  <i ></i>
                  收费标准
                </template>
      {{ item.price }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
                  <i ></i>
                  咨询方式
                </template>
      {{ item.way }}
    </el-descriptions-item>
  </el-descriptions>
</el-card>

<el-dialog :visible.sync="CreateTopicVisible">
  <el-form>
    <el-form-item label="话题名称">
      <el-input v-model="topicTitle"></el-input>
    </el-form-item>
    <el-form-item label="描述">
      <el-input type="textarea" :row="3" v-model="topicText"></el-input>
    </el-form-item>
    <el-form-item label="价格">
      <el-input v-model="topicPrice"></el-input>
    </el-form-item>
    <el-form-item label="咨询方式">
      <el-select v-model="topicWay">
        <el-option label="线上" value="线上"></el-option>
        <el-option label="线下" value="线下"></el-option>
      </el-select>
    </el-form-item>
    <div style="display:flex;justify-content: center;margin-top: 20px;">
      <el-button type="primary" @click="submitTopic">提交</el-button>
      <el-button @click="CreateTopicVisible = false">取消</el-button>
    </div>
  </el-form>
</el-dialog>
</div>
<Footer :num="num" />
</div>
</template>

<script>
  //个人信息的编辑，涉及到省份和城市接口的调用，先选省份，选好后得到pid再传给city接口才能得到city信息。
  import axios from 'axios';
  import Footer from './Screen/Footer.vue';
  import Header from './Screen/Header.vue';
  import CreateTopics from './Topics/CreateTopics.vue';
  import userInfoEdit from './userInfoEdit.vue';
  export default {
    name: "Info",
    components: {
      userInfoEdit,
      Header,
      Footer,
      CreateTopics,
    },
    data() {
      return {
        ID: "",
        title: "",
        userId: "",
        userInfo: "",
        num: "third",
        CreateTopicVisible: false,
        user:{},
  
        topicTitle:"",
        topicText:"",
        topicPrice: "",
        topicWay:"",
  
      };
    },
    mounted() {
      let expert_id = localStorage.getItem('userId');
      const config = {
        url:'expert/getOne',
        method:'get',
        params:{
          expertId: expert_id
        }
      }
      axios(config).then((res)=>{
        this.user = res.data.data;
      })
    },
    methods: {
      submitTopic(){
        let id = localStorage.getItem('userId');
        const config ={
          url:'/topic/addTopic',
          method:'post',
          params:{
            expertId: id,
            title: this.topicTitle,
            text: this.topicText,
            price: this.topicPrice,
            way: this.topicWay
          }
        }
        axios(config).then((res)=>{
          if (res.data.status === 100) {
            this.$message({
              message: '提交成功，等待管理员审核！',
              type: 'success'
            });
          }
          else {
            this.$message({
              message: '提交失败',
              type: 'error'
            });
          }
        })
        this.CreateTopicVisible = false;
      },
      addNewTopic(){
        this.CreateTopicVisible = true;
      },
      edit() {
  
        this.$refs.userInfoEdit.init();
      },
      handleClose(done) {
        done();
      },
      linkToMyExpertTopic() {
        this.$router.push('/MyExpertTopic');
      },
      linkToMyExpertOrder() {
        this.$router.push('/MyExpertOrder');
      },
    },
  };
  </script>

<style scoped lang="less">
.img {
  width: 80px;
  height: 80px;
}

.btns {
  text-align: center;
}

/deep/.el-card {
  border-radius: 4px;
  border: 1px solid #EBEEF5;
  background-color: #FFF;
  overflow: hidden;
  color: #303133;
  transition: .3s;
  width: 100%;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>
<template>
    <div>
      <Header />
      <el-container>
        <el-main style="height: 710px">
          <div>
            <div class="bg">
              <link
                rel="stylesheet icon"
                href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css"
              />
  
              <br />
              <br />
              <hr class="hr_solid" />
  
              <br />
              <el-card
                :body-style="{ padding: '0px' }"
                v-for="(item, index) in Topics"
                :key="index"
              >
                <div
                  style="padding: 14px"
                  :key="index"
                  @click="linkToExpertDetail(item, index)"
                >
                  <div>
                    <span class="name"
                      ><i style="color: steelblue">#</i>
                      {{ Topics[index].title }}&nbsp;&nbsp;</span
                    >
  
                    <!-- <span class="city"><i class="el-icon-location"></i>{{Topics[index].city}}</span> -->
                    <br />
                    <span class="title"
                      >可预约时间：{{ Topics[index].appointTime }}</span
                    >
                    <span class="title"
                      >创建时间：{{ Topics[index].topicTime }}</span
                    >
                  </div>
  
                  <div class="description">
                    <br />
                    <div>
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].type }}&nbsp;&nbsp;</span
                      >
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].state }}&nbsp;&nbsp;</span
                      >
                      <span class="price">{{ Topics[index].price }}元/小时</span>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </el-main>
      </el-container>
      <el-container>
        <Footer :num="num" />
      </el-container>
    </div>
  </template>

<style scoped lang="less">
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.btns {
  margin-top: 20px;
  text-align: center;
}

.el-button {
  border-radius: 10px;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.topic {
  padding: 0px;
  font-size: 18px;
  margin-top: 60px;
  line-height: 28px;
}

.state {
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  float: right;
  color: forestgreen;
  background-color: lightblue;
  height: 30px;
}

.name {
  color: black;
  font-size: 20px;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>

<script>
  import axios from "axios";
  import Vue from "vue";
  import Vuex from "vuex";
  import Expert from "./expert.vue";
  import Footer from "./Screen/Footer.vue";
  import Header from "./Screen/Header.vue";
  import Test4 from "./Test4.vue";
  import Test5 from "./Test5.vue";
  Vue.use(Vuex);
  
  export default {
    props: ["value"],
    data() {
      return {
        url: "https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry",
        Topics: [],
        Price: [],
        currentDate: new Date(),
        length: Number,
        num: "third",
      };
    },
    components: {
      Expert,
      Test4,
      Test5,
      Header,
      Footer,
    },
    methods: {
      async queryData() {
        var data = new FormData();
        data.append("expertId", this.userId);
        var config = {
          method: "post",
          url: "/topic/getById",
          data: data,
        };
        var res = await axios(config);
        console.log(res.data);
        this.Topics = res.data.data.topics;
        return res;
      },
      getExpert(item, index) {
        console.log(this.Topics[index].phone);
        var id = this.Topics[index].phone;
        return id;
        /*this.$router.push({
            path: `/expert/${id}`
          })*/
      },
      linkToExpertDetail(item, index) {
        var id = this.getExpert(item, index);
        console.log("跳转到" + id + "对应的行家信息详情");
        this.$router.push(`/ExpertDetailInfo/${id}`);
      },
  
      async getTopics(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("expertId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/topic/getById",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              that.$set(that.Topics[i], "topic", response.data.data.topics);
              that.$set(that.Topics[i], "price", response.data.data.minPrice[0]);
              that.$set(
                that.Topics[i],
                "type",
                response.data.data.topics[0].type
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      async getCity(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("userId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/user/myInfo",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              console.log(response.data.data.city);
              that.$set(that.Topics[i], "province", response.data.data.province);
              that.$set(that.Topics[i], "city", response.data.data.city);
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      //从接口获取Topic信息，初始化给Topics
      //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
    },
    mounted() {
      // var config = {
      //   method: 'post',
      //   url: '/expert/getAll'
      // }
      // var that = this;
      // axios(config)
      // .then(function (response) {
      //   console.log(JSON.stringify(response.data));
      //   that.Topics = response.data.data;
      //   that.length = that.Topics.length;
      //   console.log("length为" + that.length);
      //   console.log(data);
      //     })
      //     .catch(function (error) {
      //       console.log(error);
      //     });
      this.userId = localStorage.getItem("userId");
      this.queryData().then((res) => {
        this.Topics = res.data.data.topics;
        console.log("Topics长度是" + this.Topics.length);
      });
  
      /*
          for (var i = 0; i < that.length; i++)
  {
    var data=new FormData();;
    data.append('expertId', that.Topics[i].phone);
    console.log(data);
  }
  
      var config1={
          method: 'post',
          url: '/topic/getById',
          data: data
      }
        axios(config1)
          .then(function (response) {
            console.log(JSON.stringify(response.data));
          })
          .catch(function (error) {
            console.log(error);
          });*/
      /*
      axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
            this.Topics = res.data;
        var length = this.Topics.length;
          })*/
    },
  };
  </script>
<template>
    <div>
      <Header />
      <el-container>
        <el-main style="height: 710px">
          <div>
            <div class="bg">
              <link
                rel="stylesheet icon"
                href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css"
              />
  
              <br />
              <br />
              <hr class="hr_solid" />
  
              <br />
              <el-card
                :body-style="{ padding: '0px' }"
                v-for="(item, index) in Topics"
                :key="index"
              >
                <div
                  style="padding: 14px"
                  :key="index"
                  @click="linkToExpertDetail(item, index)"
                >
                  <div>
                    <span class="name"
                      ><i style="color: steelblue">#</i>
                      {{ Topics[index].title }}&nbsp;&nbsp;</span
                    >
  
                    <!-- <span class="city"><i class="el-icon-location"></i>{{Topics[index].city}}</span> -->
                    <br />
                    <span class="title"
                      >可预约时间：{{ Topics[index].appointTime }}</span
                    >
                    <span class="title"
                      >创建时间：{{ Topics[index].topicTime }}</span
                    >
                  </div>
  
                  <div class="description">
                    <br />
                    <div>
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].type }}&nbsp;&nbsp;</span
                      >
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].state }}&nbsp;&nbsp;</span
                      >
                      <span class="price">{{ Topics[index].price }}元/小时</span>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </el-main>
      </el-container>
      <el-container>
        <Footer :num="num" />
      </el-container>
    </div>
  </template>

<style scoped lang="less">
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.btns {
  margin-top: 20px;
  text-align: center;
}

.el-button {
  border-radius: 10px;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.topic {
  padding: 0px;
  font-size: 18px;
  margin-top: 60px;
  line-height: 28px;
}

.state {
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  float: right;
  color: forestgreen;
  background-color: lightblue;
  height: 30px;
}

.name {
  color: black;
  font-size: 20px;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>

<script>
  import axios from "axios";
  import Vue from "vue";
  import Vuex from "vuex";
  import Expert from "./expert.vue";
  import Footer from "./Screen/Footer.vue";
  import Header from "./Screen/Header.vue";
  import Test4 from "./Test4.vue";
  import Test5 from "./Test5.vue";
  Vue.use(Vuex);
  
  export default {
    props: ["value"],
    data() {
      return {
        url: "https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry",
        Topics: [],
        Price: [],
        currentDate: new Date(),
        length: Number,
        num: "third",
      };
    },
    components: {
      Expert,
      Test4,
      Test5,
      Header,
      Footer,
    },
    methods: {
      async queryData() {
        var data = new FormData();
        data.append("expertId", this.userId);
        var config = {
          method: "post",
          url: "/topic/getById",
          data: data,
        };
        var res = await axios(config);
        console.log(res.data);
        this.Topics = res.data.data.topics;
        return res;
      },
      getExpert(item, index) {
        console.log(this.Topics[index].phone);
        var id = this.Topics[index].phone;
        return id;
        /*this.$router.push({
            path: `/expert/${id}`
          })*/
      },
      linkToExpertDetail(item, index) {
        var id = this.getExpert(item, index);
        console.log("跳转到" + id + "对应的行家信息详情");
        this.$router.push(`/ExpertDetailInfo/${id}`);
      },
  
      async getTopics(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("expertId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/topic/getById",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              that.$set(that.Topics[i], "topic", response.data.data.topics);
              that.$set(that.Topics[i], "price", response.data.data.minPrice[0]);
              that.$set(
                that.Topics[i],
                "type",
                response.data.data.topics[0].type
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      async getCity(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("userId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/user/myInfo",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              console.log(response.data.data.city);
              that.$set(that.Topics[i], "province", response.data.data.province);
              that.$set(that.Topics[i], "city", response.data.data.city);
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      //从接口获取Topic信息，初始化给Topics
      //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
    },
    mounted() {
      // var config = {
      //   method: 'post',
      //   url: '/expert/getAll'
      // }
      // var that = this;
      // axios(config)
      // .then(function (response) {
      //   console.log(JSON.stringify(response.data));
      //   that.Topics = response.data.data;
      //   that.length = that.Topics.length;
      //   console.log("length为" + that.length);
      //   console.log(data);
      //     })
      //     .catch(function (error) {
      //       console.log(error);
      //     });
      this.userId = localStorage.getItem("userId");
      this.queryData().then((res) => {
        this.Topics = res.data.data.topics;
        console.log("Topics长度是" + this.Topics.length);
      });
  
      /*
          for (var i = 0; i < that.length; i++)
  {
    var data=new FormData();;
    data.append('expertId', that.Topics[i].phone);
    console.log(data);
  }
  
      var config1={
          method: 'post',
          url: '/topic/getById',
          data: data
      }
        axios(config1)
          .then(function (response) {
            console.log(JSON.stringify(response.data));
          })
          .catch(function (error) {
            console.log(error);
          });*/
      /*
      axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
            this.Topics = res.data;
        var length = this.Topics.length;
          })*/
    },
  };
  </script>
<template>
    <div>
      <Header />
      <el-container>
        <el-main style="height: 710px">
          <div>
            <div class="bg">
              <link
                rel="stylesheet icon"
                href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css"
              />
  
              <br />
              <br />
              <hr class="hr_solid" />
  
              <br />
              <el-card
                :body-style="{ padding: '0px' }"
                v-for="(item, index) in Topics"
                :key="index"
              >
                <div
                  style="padding: 14px"
                  :key="index"
                  @click="linkToExpertDetail(item, index)"
                >
                  <div>
                    <span class="name"
                      ><i style="color: steelblue">#</i>
                      {{ Topics[index].title }}&nbsp;&nbsp;</span
                    >
  
                    <!-- <span class="city"><i class="el-icon-location"></i>{{Topics[index].city}}</span> -->
                    <br />
                    <span class="title"
                      >可预约时间：{{ Topics[index].appointTime }}</span
                    >
                    <span class="title"
                      >创建时间：{{ Topics[index].topicTime }}</span
                    >
                  </div>
  
                  <div class="description">
                    <br />
                    <div>
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].type }}&nbsp;&nbsp;</span
                      >
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].state }}&nbsp;&nbsp;</span
                      >
                      <span class="price">{{ Topics[index].price }}元/小时</span>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </el-main>
      </el-container>
      <el-container>
        <Footer :num="num" />
      </el-container>
    </div>
  </template>

<style scoped lang="less">
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.btns {
  margin-top: 20px;
  text-align: center;
}

.el-button {
  border-radius: 10px;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.topic {
  padding: 0px;
  font-size: 18px;
  margin-top: 60px;
  line-height: 28px;
}

.state {
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  float: right;
  color: forestgreen;
  background-color: lightblue;
  height: 30px;
}

.name {
  color: black;
  font-size: 20px;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>

<script>
  import axios from "axios";
  import Vue from "vue";
  import Vuex from "vuex";
  import Expert from "./expert.vue";
  import Footer from "./Screen/Footer.vue";
  import Header from "./Screen/Header.vue";
  import Test4 from "./Test4.vue";
  import Test5 from "./Test5.vue";
  Vue.use(Vuex);
  
  export default {
    props: ["value"],
    data() {
      return {
        url: "https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry",
        Topics: [],
        Price: [],
        currentDate: new Date(),
        length: Number,
        num: "third",
      };
    },
    components: {
      Expert,
      Test4,
      Test5,
      Header,
      Footer,
    },
    methods: {
      async queryData() {
        var data = new FormData();
        data.append("expertId", this.userId);
        var config = {
          method: "post",
          url: "/topic/getById",
          data: data,
        };
        var res = await axios(config);
        console.log(res.data);
        this.Topics = res.data.data.topics;
        return res;
      },
      getExpert(item, index) {
        console.log(this.Topics[index].phone);
        var id = this.Topics[index].phone;
        return id;
        /*this.$router.push({
            path: `/expert/${id}`
          })*/
      },
      linkToExpertDetail(item, index) {
        var id = this.getExpert(item, index);
        console.log("跳转到" + id + "对应的行家信息详情");
        this.$router.push(`/ExpertDetailInfo/${id}`);
      },
  
      async getTopics(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("expertId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/topic/getById",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              that.$set(that.Topics[i], "topic", response.data.data.topics);
              that.$set(that.Topics[i], "price", response.data.data.minPrice[0]);
              that.$set(
                that.Topics[i],
                "type",
                response.data.data.topics[0].type
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      async getCity(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("userId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/user/myInfo",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              console.log(response.data.data.city);
              that.$set(that.Topics[i], "province", response.data.data.province);
              that.$set(that.Topics[i], "city", response.data.data.city);
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      //从接口获取Topic信息，初始化给Topics
      //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
    },
    mounted() {
      // var config = {
      //   method: 'post',
      //   url: '/expert/getAll'
      // }
      // var that = this;
      // axios(config)
      // .then(function (response) {
      //   console.log(JSON.stringify(response.data));
      //   that.Topics = response.data.data;
      //   that.length = that.Topics.length;
      //   console.log("length为" + that.length);
      //   console.log(data);
      //     })
      //     .catch(function (error) {
      //       console.log(error);
      //     });
      this.userId = localStorage.getItem("userId");
      this.queryData().then((res) => {
        this.Topics = res.data.data.topics;
        console.log("Topics长度是" + this.Topics.length);
      });
  
      /*
          for (var i = 0; i < that.length; i++)
  {
    var data=new FormData();;
    data.append('expertId', that.Topics[i].phone);
    console.log(data);
  }
  
      var config1={
          method: 'post',
          url: '/topic/getById',
          data: data
      }
        axios(config1)
          .then(function (response) {
            console.log(JSON.stringify(response.data));
          })
          .catch(function (error) {
            console.log(error);
          });*/
      /*
      axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
            this.Topics = res.data;
        var length = this.Topics.length;
          })*/
    },
  };
  </script>
<template>
    <div>
      <Header />
      <el-container>
        <el-main style="height: 710px">
          <div>
            <div class="bg">
              <link
                rel="stylesheet icon"
                href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css"
              />
  
              <br />
              <br />
              <hr class="hr_solid" />
  
              <br />
              <el-card
                :body-style="{ padding: '0px' }"
                v-for="(item, index) in Topics"
                :key="index"
              >
                <div
                  style="padding: 14px"
                  :key="index"
                  @click="linkToExpertDetail(item, index)"
                >
                  <div>
                    <span class="name"
                      ><i style="color: steelblue">#</i>
                      {{ Topics[index].title }}&nbsp;&nbsp;</span
                    >
  
                    <!-- <span class="city"><i class="el-icon-location"></i>{{Topics[index].city}}</span> -->
                    <br />
                    <span class="title"
                      >可预约时间：{{ Topics[index].appointTime }}</span
                    >
                    <span class="title"
                      >创建时间：{{ Topics[index].topicTime }}</span
                    >
                  </div>
  
                  <div class="description">
                    <br />
                    <div>
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].type }}&nbsp;&nbsp;</span
                      >
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].state }}&nbsp;&nbsp;</span
                      >
                      <span class="price">{{ Topics[index].price }}元/小时</span>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </el-main>
      </el-container>
      <el-container>
        <Footer :num="num" />
      </el-container>
    </div>
  </template>

<style scoped lang="less">
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.btns {
  margin-top: 20px;
  text-align: center;
}

.el-button {
  border-radius: 10px;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.topic {
  padding: 0px;
  font-size: 18px;
  margin-top: 60px;
  line-height: 28px;
}

.state {
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  float: right;
  color: forestgreen;
  background-color: lightblue;
  height: 30px;
}

.name {
  color: black;
  font-size: 20px;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>

<script>
  import axios from "axios";
  import Vue from "vue";
  import Vuex from "vuex";
  import Expert from "./expert.vue";
  import Footer from "./Screen/Footer.vue";
  import Header from "./Screen/Header.vue";
  import Test4 from "./Test4.vue";
  import Test5 from "./Test5.vue";
  Vue.use(Vuex);
  
  export default {
    props: ["value"],
    data() {
      return {
        url: "https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry",
        Topics: [],
        Price: [],
        currentDate: new Date(),
        length: Number,
        num: "third",
      };
    },
    components: {
      Expert,
      Test4,
      Test5,
      Header,
      Footer,
    },
    methods: {
      async queryData() {
        var data = new FormData();
        data.append("expertId", this.userId);
        var config = {
          method: "post",
          url: "/topic/getById",
          data: data,
        };
        var res = await axios(config);
        console.log(res.data);
        this.Topics = res.data.data.topics;
        return res;
      },
      getExpert(item, index) {
        console.log(this.Topics[index].phone);
        var id = this.Topics[index].phone;
        return id;
        /*this.$router.push({
            path: `/expert/${id}`
          })*/
      },
      linkToExpertDetail(item, index) {
        var id = this.getExpert(item, index);
        console.log("跳转到" + id + "对应的行家信息详情");
        this.$router.push(`/ExpertDetailInfo/${id}`);
      },
  
      async getTopics(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("expertId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/topic/getById",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              that.$set(that.Topics[i], "topic", response.data.data.topics);
              that.$set(that.Topics[i], "price", response.data.data.minPrice[0]);
              that.$set(
                that.Topics[i],
                "type",
                response.data.data.topics[0].type
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      async getCity(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("userId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/user/myInfo",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              console.log(response.data.data.city);
              that.$set(that.Topics[i], "province", response.data.data.province);
              that.$set(that.Topics[i], "city", response.data.data.city);
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      //从接口获取Topic信息，初始化给Topics
      //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
    },
    mounted() {
      // var config = {
      //   method: 'post',
      //   url: '/expert/getAll'
      // }
      // var that = this;
      // axios(config)
      // .then(function (response) {
      //   console.log(JSON.stringify(response.data));
      //   that.Topics = response.data.data;
      //   that.length = that.Topics.length;
      //   console.log("length为" + that.length);
      //   console.log(data);
      //     })
      //     .catch(function (error) {
      //       console.log(error);
      //     });
      this.userId = localStorage.getItem("userId");
      this.queryData().then((res) => {
        this.Topics = res.data.data.topics;
        console.log("Topics长度是" + this.Topics.length);
      });
  
      /*
          for (var i = 0; i < that.length; i++)
  {
    var data=new FormData();;
    data.append('expertId', that.Topics[i].phone);
    console.log(data);
  }
  
      var config1={
          method: 'post',
          url: '/topic/getById',
          data: data
      }
        axios(config1)
          .then(function (response) {
            console.log(JSON.stringify(response.data));
          })
          .catch(function (error) {
            console.log(error);
          });*/
      /*
      axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
            this.Topics = res.data;
        var length = this.Topics.length;
          })*/
    },
  };
  </script>
<template>
    <div>
      <Header />
      <el-container>
        <el-main style="height: 710px">
          <div>
            <div class="bg">
              <link
                rel="stylesheet icon"
                href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css"
              />
  
              <br />
              <br />
              <hr class="hr_solid" />
  
              <br />
              <el-card
                :body-style="{ padding: '0px' }"
                v-for="(item, index) in Topics"
                :key="index"
              >
                <div
                  style="padding: 14px"
                  :key="index"
                  @click="linkToExpertDetail(item, index)"
                >
                  <div>
                    <span class="name"
                      ><i style="color: steelblue">#</i>
                      {{ Topics[index].title }}&nbsp;&nbsp;</span
                    >
  
                    <!-- <span class="city"><i class="el-icon-location"></i>{{Topics[index].city}}</span> -->
                    <br />
                    <span class="title"
                      >可预约时间：{{ Topics[index].appointTime }}</span
                    >
                    <span class="title"
                      >创建时间：{{ Topics[index].topicTime }}</span
                    >
                  </div>
  
                  <div class="description">
                    <br />
                    <div>
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].type }}&nbsp;&nbsp;</span
                      >
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].state }}&nbsp;&nbsp;</span
                      >
                      <span class="price">{{ Topics[index].price }}元/小时</span>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </el-main>
      </el-container>
      <el-container>
        <Footer :num="num" />
      </el-container>
    </div>
  </template>

<style scoped lang="less">
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.btns {
  margin-top: 20px;
  text-align: center;
}

.el-button {
  border-radius: 10px;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.topic {
  padding: 0px;
  font-size: 18px;
  margin-top: 60px;
  line-height: 28px;
}

.state {
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  float: right;
  color: forestgreen;
  background-color: lightblue;
  height: 30px;
}

.name {
  color: black;
  font-size: 20px;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>

<script>
  import axios from "axios";
  import Vue from "vue";
  import Vuex from "vuex";
  import Expert from "./expert.vue";
  import Footer from "./Screen/Footer.vue";
  import Header from "./Screen/Header.vue";
  import Test4 from "./Test4.vue";
  import Test5 from "./Test5.vue";
  Vue.use(Vuex);
  
  export default {
    props: ["value"],
    data() {
      return {
        url: "https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry",
        Topics: [],
        Price: [],
        currentDate: new Date(),
        length: Number,
        num: "third",
      };
    },
    components: {
      Expert,
      Test4,
      Test5,
      Header,
      Footer,
    },
    methods: {
      async queryData() {
        var data = new FormData();
        data.append("expertId", this.userId);
        var config = {
          method: "post",
          url: "/topic/getById",
          data: data,
        };
        var res = await axios(config);
        console.log(res.data);
        this.Topics = res.data.data.topics;
        return res;
      },
      getExpert(item, index) {
        console.log(this.Topics[index].phone);
        var id = this.Topics[index].phone;
        return id;
        /*this.$router.push({
            path: `/expert/${id}`
          })*/
      },
      linkToExpertDetail(item, index) {
        var id = this.getExpert(item, index);
        console.log("跳转到" + id + "对应的行家信息详情");
        this.$router.push(`/ExpertDetailInfo/${id}`);
      },
  
      async getTopics(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("expertId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/topic/getById",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              that.$set(that.Topics[i], "topic", response.data.data.topics);
              that.$set(that.Topics[i], "price", response.data.data.minPrice[0]);
              that.$set(
                that.Topics[i],
                "type",
                response.data.data.topics[0].type
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      async getCity(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("userId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/user/myInfo",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              console.log(response.data.data.city);
              that.$set(that.Topics[i], "province", response.data.data.province);
              that.$set(that.Topics[i], "city", response.data.data.city);
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      //从接口获取Topic信息，初始化给Topics
      //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
    },
    mounted() {
      // var config = {
      //   method: 'post',
      //   url: '/expert/getAll'
      // }
      // var that = this;
      // axios(config)
      // .then(function (response) {
      //   console.log(JSON.stringify(response.data));
      //   that.Topics = response.data.data;
      //   that.length = that.Topics.length;
      //   console.log("length为" + that.length);
      //   console.log(data);
      //     })
      //     .catch(function (error) {
      //       console.log(error);
      //     });
      this.userId = localStorage.getItem("userId");
      this.queryData().then((res) => {
        this.Topics = res.data.data.topics;
        console.log("Topics长度是" + this.Topics.length);
      });
  
      /*
          for (var i = 0; i < that.length; i++)
  {
    var data=new FormData();;
    data.append('expertId', that.Topics[i].phone);
    console.log(data);
  }
  
      var config1={
          method: 'post',
          url: '/topic/getById',
          data: data
      }
        axios(config1)
          .then(function (response) {
            console.log(JSON.stringify(response.data));
          })
          .catch(function (error) {
            console.log(error);
          });*/
      /*
      axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
            this.Topics = res.data;
        var length = this.Topics.length;
          })*/
    },
  };
  </script>
<template>
    <div>
      <Header />
      <el-container>
        <el-main style="height: 710px">
          <div>
            <div class="bg">
              <link
                rel="stylesheet icon"
                href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css"
              />
  
              <br />
              <br />
              <hr class="hr_solid" />
  
              <br />
              <el-card
                :body-style="{ padding: '0px' }"
                v-for="(item, index) in Topics"
                :key="index"
              >
                <div
                  style="padding: 14px"
                  :key="index"
                  @click="linkToExpertDetail(item, index)"
                >
                  <div>
                    <span class="name"
                      ><i style="color: steelblue">#</i>
                      {{ Topics[index].title }}&nbsp;&nbsp;</span
                    >
  
                    <!-- <span class="city"><i class="el-icon-location"></i>{{Topics[index].city}}</span> -->
                    <br />
                    <span class="title"
                      >可预约时间：{{ Topics[index].appointTime }}</span
                    >
                    <span class="title"
                      >创建时间：{{ Topics[index].topicTime }}</span
                    >
                  </div>
  
                  <div class="description">
                    <br />
                    <div>
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].type }}&nbsp;&nbsp;</span
                      >
                      <span class="type"
                        >&nbsp;&nbsp;{{ Topics[index].state }}&nbsp;&nbsp;</span
                      >
                      <span class="price">{{ Topics[index].price }}元/小时</span>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </el-main>
      </el-container>
      <el-container>
        <Footer :num="num" />
      </el-container>
    </div>
  </template>

<style scoped lang="less">
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.btns {
  margin-top: 20px;
  text-align: center;
}

.el-button {
  border-radius: 10px;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.topic {
  padding: 0px;
  font-size: 18px;
  margin-top: 60px;
  line-height: 28px;
}

.state {
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  float: right;
  color: forestgreen;
  background-color: lightblue;
  height: 30px;
}

.name {
  color: black;
  font-size: 20px;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>

<script>
  import axios from "axios";
  import Vue from "vue";
  import Vuex from "vuex";
  import Expert from "./expert.vue";
  import Footer from "./Screen/Footer.vue";
  import Header from "./Screen/Header.vue";
  import Test4 from "./Test4.vue";
  import Test5 from "./Test5.vue";
  Vue.use(Vuex);
  
  export default {
    props: ["value"],
    data() {
      return {
        url: "https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry",
        Topics: [],
        Price: [],
        currentDate: new Date(),
        length: Number,
        num: "third",
      };
    },
    components: {
      Expert,
      Test4,
      Test5,
      Header,
      Footer,
    },
    methods: {
      async queryData() {
        var data = new FormData();
        data.append("expertId", this.userId);
        var config = {
          method: "post",
          url: "/topic/getById",
          data: data,
        };
        var res = await axios(config);
        console.log(res.data);
        this.Topics = res.data.data.topics;
        return res;
      },
      getExpert(item, index) {
        console.log(this.Topics[index].phone);
        var id = this.Topics[index].phone;
        return id;
        /*this.$router.push({
            path: `/expert/${id}`
          })*/
      },
      linkToExpertDetail(item, index) {
        var id = this.getExpert(item, index);
        console.log("跳转到" + id + "对应的行家信息详情");
        this.$router.push(`/ExpertDetailInfo/${id}`);
      },
  
      async getTopics(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("expertId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/topic/getById",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              that.$set(that.Topics[i], "topic", response.data.data.topics);
              that.$set(that.Topics[i], "price", response.data.data.minPrice[0]);
              that.$set(
                that.Topics[i],
                "type",
                response.data.data.topics[0].type
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      async getCity(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("userId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/user/myInfo",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              console.log(response.data.data.city);
              that.$set(that.Topics[i], "province", response.data.data.province);
              that.$set(that.Topics[i], "city", response.data.data.city);
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      //从接口获取Topic信息，初始化给Topics
      //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
    },
    mounted() {
      // var config = {
      //   method: 'post',
      //   url: '/expert/getAll'
      // }
      // var that = this;
      // axios(config)
      // .then(function (response) {
      //   console.log(JSON.stringify(response.data));
      //   that.Topics = response.data.data;
      //   that.length = that.Topics.length;
      //   console.log("length为" + that.length);
      //   console.log(data);
      //     })
      //     .catch(function (error) {
      //       console.log(error);
      //     });
      this.userId = localStorage.getItem("userId");
      this.queryData().then((res) => {
        this.Topics = res.data.data.topics;
        console.log("Topics长度是" + this.Topics.length);
      });
  
      /*
          for (var i = 0; i < that.length; i++)
  {
    var data=new FormData();;
    data.append('expertId', that.Topics[i].phone);
    console.log(data);
  }
  
      var config1={
          method: 'post',
          url: '/topic/getById',
          data: data
      }
        axios(config1)
          .then(function (response) {
            console.log(JSON.stringify(response.data));
          })
          .catch(function (error) {
            console.log(error);
          });*/
      /*
      axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
            this.Topics = res.data;
        var length = this.Topics.length;
          })*/
    },
  };
  </script>
<template>
  <div>
    <Header />
    <el-container>
      <el-main style="height: 710px">
        <div>
          <div class="bg">
            <link rel="stylesheet icon" href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css" />

            <br />
            <br />
            <hr class="hr_solid" />

            <br />
            <el-card :body-style="{ padding: '0px' }" v-for="(item, index) in Topics" :key="index">
              <div style="padding: 14px" :key="index" @click="linkToExpertDetail(item, index)">
                <div>
                  <span class="name"><i style="color: steelblue">#</i>
                    {{ Topics[index].title }}&nbsp;&nbsp;</span>

                  <!-- <span class="city"><i class="el-icon-location"></i>{{Topics[index].city}}</span> -->
                  <br />
                  <span class="title">可预约时间：{{ Topics[index].appointTime }}</span>
                  <span class="title">创建时间：{{ Topics[index].topicTime }}</span>
                </div>

                <div class="description">
                  <br />
                  <div>
                    <span class="type">&nbsp;&nbsp;{{ Topics[index].type }}&nbsp;&nbsp;</span>
                    <span class="type">&nbsp;&nbsp;{{ Topics[index].state }}&nbsp;&nbsp;</span>
                    <span class="price">{{ Topics[index].price }}元/小时</span>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
        </div>
      </el-main>
    </el-container>
    <el-container>
      <Footer :num="num" />
    </el-container>
  </div>
</template>

<style scoped lang="less">
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.hr_solid {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}

.com {
  display: inline-block;
}

.iconfont {
  display: inline-block;
  font-size: 60px;
}

.psychology {
  display: none;
}

.city {
  color: gray;
}

.name {
  color: black;
  font-size: 20px;
}

.title {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}

.topic {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}

.type {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01 {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname {
  text-align: center;
  font-size: 28px;
}

/deep/.description {
  font-size: 2px;
}

.el-card {
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-bottom: 30px;
  height: auto;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.btns {
  margin-top: 20px;
  text-align: center;
}

.el-button {
  border-radius: 10px;
}

.price {
  font-size: 20px;
  color: red;
  float: right;
}

.topic {
  padding: 0px;
  font-size: 18px;
  margin-top: 60px;
  line-height: 28px;
}

.state {
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  float: right;
  color: forestgreen;
  background-color: lightblue;
  height: 30px;
}

.name {
  color: black;
  font-size: 20px;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>

<script>
  import axios from "axios";
  import Vue from "vue";
  import Vuex from "vuex";
  import Expert from "./expert.vue";
  import Footer from "./Screen/Footer.vue";
  import Header from "./Screen/Header.vue";
  import Test4 from "./Test4.vue";
  import Test5 from "./Test5.vue";
  Vue.use(Vuex);
  
  export default {
    props: ["value"],
    data() {
      return {
        url: "https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry",
        Topics: [],
        Price: [],
        currentDate: new Date(),
        length: Number,
        num: "third",
      };
    },
    components: {
      Expert,
      Test4,
      Test5,
      Header,
      Footer,
    },
    methods: {
      async queryData() {
        var data = new FormData();
        data.append("expertId", this.userId);
        var config = {
          method: "post",
          url: "/topic/getById",
          data: data,
        };
        var res = await axios(config);
        console.log(res.data);
        this.Topics = res.data.data.topics;
        return res;
      },
      getExpert(item, index) {
        console.log(this.Topics[index].phone);
        var id = this.Topics[index].phone;
        return id;
        /*this.$router.push({
            path: `/expert/${id}`
          })*/
      },
      linkToExpertDetail(item, index) {
        var id = this.getExpert(item, index);
        console.log("跳转到" + id + "对应的行家信息详情");
        this.$router.push(`/ExpertDetailInfo/${id}`);
      },
  
      async getTopics(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("expertId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/topic/getById",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              that.$set(that.Topics[i], "topic", response.data.data.topics);
              that.$set(that.Topics[i], "price", response.data.data.minPrice[0]);
              that.$set(
                that.Topics[i],
                "type",
                response.data.data.topics[0].type
              );
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      async getCity(length) {
        for (var i = 0; i < length; i++) {
          var data = new FormData();
          data.append("userId", this.Topics[i].phone);
          var config = {
            method: "post",
            url: "/user/myInfo",
            data: data,
          };
          var that = this;
          await axios(config)
            .then(function (response) {
              console.log(response.data.data.city);
              that.$set(that.Topics[i], "province", response.data.data.province);
              that.$set(that.Topics[i], "city", response.data.data.city);
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
  
      //从接口获取Topic信息，初始化给Topics
      //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
    },
    mounted() {
      // var config = {
      //   method: 'post',
      //   url: '/expert/getAll'
      // }
      // var that = this;
      // axios(config)
      // .then(function (response) {
      //   console.log(JSON.stringify(response.data));
      //   that.Topics = response.data.data;
      //   that.length = that.Topics.length;
      //   console.log("length为" + that.length);
      //   console.log(data);
      //     })
      //     .catch(function (error) {
      //       console.log(error);
      //     });
      this.userId = localStorage.getItem("userId");
      this.queryData().then((res) => {
        this.Topics = res.data.data.topics;
        console.log("Topics长度是" + this.Topics.length);
      });
  
      /*
          for (var i = 0; i < that.length; i++)
  {
    var data=new FormData();;
    data.append('expertId', that.Topics[i].phone);
    console.log(data);
  }
  
      var config1={
          method: 'post',
          url: '/topic/getById',
          data: data
      }
        axios(config1)
          .then(function (response) {
            console.log(JSON.stringify(response.data));
          })
          .catch(function (error) {
            console.log(error);
          });*/
      /*
      axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
            this.Topics = res.data;
        var length = this.Topics.length;
          })*/
    },
  };
  </script>