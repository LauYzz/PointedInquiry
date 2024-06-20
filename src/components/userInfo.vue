<template>
  <div>
    <Header />
    <el-container style="height:710px;">
      <el-main>
        <el-card>
          <el-descriptions class="margin-top" title="个人信息" :column="1" border>
            <template slot="extra">
              <el-button @click="edit()" size="small">编辑</el-button>
            </template>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                手机号
              </template>
              {{ phone }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-custom"></i>
                姓名
              </template>
              {{ name }}
            </el-descriptions-item>
            <!-- <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            地区
          </template>
          {{ province }}{{ city }}
        </el-descriptions-item> -->

            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-basketball"></i>
                学校
              </template>
              {{ school }}
            </el-descriptions-item>
            <!-- <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-date"></i>
            是否为行家
          </template>
          {{isExpert}}
        </el-descriptions-item> -->
          </el-descriptions>
        </el-card>
      </el-main>
    </el-container>
    <userInfoEdit ref="userInfoEdit"></userInfoEdit>
    <el-container>
      <Footer :num="num" />
    </el-container>
  </div>
</template>

<script>
// 个人信息的编辑，涉及到省份和城市接口的调用，先选省份，选好后得到pid再传给city接口才能得到city信息。
import axios from 'axios';
import Footer from './Screen/Footer.vue';
import Header from './Screen/Header.vue';
import userInfoEdit from './userInfoEdit.vue';
export default {
  name: "Info",
  components: {
    userInfoEdit,
    Header,
    Footer
  },
  data() {
    return {
      phone: String,
      name: String,
      province: Number,
      city: Number,
      school: String,
      isExpert: Boolean,
      userId: "",
      userInfo: "",
      num: "third",
    };
  },
  mounted() {
    this.userId = localStorage.getItem('userId');
    console.log("个人信息页面的userId为: " + this.userId);
    var data = new FormData();
    data.append("userId", this.userId);
    var config = {
      method: 'post',
      url: '/user/myInfo',
      data: data
    }
    var that = this;
    axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        that.userInfo = response.data.data;
        console.log(that.userInfo);

        that.phone = that.userId;
        that.name = response.data.data.name;
        that.province = response.data.data.province;
        that.city = response.data.data.city;
        that.school = response.data.data.school;
        if (response.data.data.isExpert)
          that.isExpert = "是";
        else that.isExpert = "否";
      })
      .catch(function (error) {
        console.log(error);
      });

  },
  methods: {
    edit() {
      console.log("edit函数被调用");
      this.$refs.userInfoEdit.init();
    }
  },
};
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
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.img {
  width: 80px;
  height: 80px;
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
</style>
