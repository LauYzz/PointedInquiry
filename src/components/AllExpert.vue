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
  color:royalblue;
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
        params:{
          type:1
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
          type:index
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
