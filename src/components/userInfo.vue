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
//个人信息的编辑，涉及到省份和城市接口的调用，先选省份，选好后得到pid再传给city接口才能得到city信息。
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
    console.log("个人信息页面的userId是" + this.userId);
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
      console.log("这是edit函数");
      this.$refs.userInfoEdit.init();
    }
  },
};
</script>

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
