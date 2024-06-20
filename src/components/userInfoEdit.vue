<template>
  <div class="personalCenter">
    <el-dialog :visible.sync="examineBtn" width="70%">

      姓名
      <el-input v-model="input5" type="text" placeholder="请输入您的姓名" show-word-limit></el-input>

      学校
      <el-input v-model="input8" type="text" placeholder="请输入您的学校" maxlength="20" show-word-limit></el-input>


      <!-- <AreaChoose @custClick="getCity" /> -->

      <br>
      <br>
      <br>
      <div style="text-align:center;">
        <el-button type="primary" size="medium" round @click="confirmEdit">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
import AreaChoose from './UserInfo/AreaChoose.vue'
export default {
  components: {
    AreaChoose
  },
  name: 'PersonalCenter',
  data() {
    return {
      pid: "",
      cid: "",
      text: '',
      input5: '',
      input8: '',
      circleUrl:
        'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
      sizeList: ['large'],
      examineBtn: false,
      options: [{
        value: '选项1',
        label: '男'
      }, {
        value: '选项2',
        label: '女'
      }],
      userId: "",
    }
  },
  methods: {
    confirmEdit() {
      var config = {
        method: 'post',
        url: '/user/changeInfo',
        data: {
          phone: this.userId,
          name: this.input5,
          addrCity: this.cid,
          addrProvince: this.pid,
          school: this.input8,
        }
      }
      axios(config)
        .then(function (response) {
          console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
          console.log(error);
        });
      this.$router.push('/UserInfo');
      location.reload();
    },
    getCity(value1, value2) {
      console.log("pid的值为:" + value1);
      console.log("cid的值为:" + value2);
      this.pid = value1;
      this.cid = value2;
    },
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
    },
    init() {
      console.log("详情组件");
      this.examineBtn = true;
    },
  },
  mounted() {

    this.userId = localStorage.getItem('userId');
    console.log("个人信息编辑页面的userId为: " + this.userId);
  }
}
</script>

<style scoped lang="less">
.el-container-large-bottom {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
.hr_solid_bottom_top {
  border: 0;
  border-top: 1px solid #d0d0d5;
  float: right;
}

.com-top-bottom {
  display: inline-block;
}
.iconfont-inline {
  display: inline-block;
  font-size: 60px;
}
.psychology-top {
  display: none;
}
.city-display-ever {
  color: gray;
}
.name-style-never {
  color: black;
  font-size: 20px;
}

.title-font-display {
  display: inline-block;
  margin-top: 8px;
  color: gray;
}
.topic-top-slide {
  padding: 0px;
  margin-top: 60px;
  line-height: 28px;
}
.type-style-global-deep {
  display: inline-block;
  margin: 8px;
  border-radius: 8px;
  font-size: 16px;
  background-color: lightblue;
  color: cadetblue;
  text-align: center;
  line-height: 24px;
}

.line_01_upper {
  padding: 0 20px 0;
  margin: 20px 0;
  line-height: 5px;
  border-left: 250px solid #ddd;
  border-right: 250px solid #ddd;
  text-align: center;
}

.bg-display-upper {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.price-width-short {
  font-size: 20px;
  color: red;
  float: right;
}

.rating-height-deep {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes-sort {
  margin-top: 8px;
  font-size: 16px;
  margin-bottom: 0px;
}

/deep/.Tname-large-bottom-font {
  text-align: center;
  font-size: 28px;
}

/deep/.description-psychology {
  font-size: 2px;
}

.el-card-kind {
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-bottom: 30px;
  height: auto;
}

.time-limit {
  font-size: 13px;
  color: #999;
}

.bottom-up {
  margin-top: 13px;
  line-height: 12px;
}

.button-small {
  padding: 0;
  float: right;
}

.image-huge {
  width: 100%;
  display: block;
}

.clearfix-align:before,
.clearfix-align:after {
  display: table;
  content: "";
}

.clearfix-align:after {
  clear: both;
}

.btns-row {
  margin-top: 20px;
  text-align: center;
}

.el-button-glide {
  border-radius: 10px;
}
.price-fancy {
  font-size: 20px;
  color: red;
  float: right;
}
.topic-deep {
  padding: 0px;
  font-size: 18px;
  margin-top: 60px;
  line-height: 28px;
}
.state-container-style {
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  float: right;
  color: forestgreen;
  background-color: lightblue;
  height: 30px;
}
.name-inside {
  color: black;
  font-size: 20px;
}

.el-container-dark {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>

<style scoped lang="less">
.personalCenter {
  font-size: 18px;
}


/deep/.el-input__inner {
  -webkit-appearance: none;
  background-color: #FFF;
  background-image: none;
  border-radius: 24px;
  border: 1px solid #DCDFE6;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  height: 40px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px;
  transition: border-color .2s cubic-bezier(.645, .045, .355, 1);
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

/deep/.el-dialog__body {
  padding: 30px 20px;
  color: #606266;
  font-size: 16px;
  word-break: break-all;
}
</style>
