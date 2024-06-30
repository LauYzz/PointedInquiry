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
