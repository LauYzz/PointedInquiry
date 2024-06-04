<template>
  <div>
    <Header />
    
    <el-container >
    <el-main style="height:710px;">
      <div class="bg">
          <link rel="stylesheet icon" href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css">
      <hr class="hr_solid">

      <br>
          <el-card :body-style="{ padding: '0px' }" v-for="(item, index) in Experts" :key="index">
      <div style="padding: 14px;" :key="index" @click="linkToExpertDetail(item, index)">
        <div>
          <span class="name">{{ item.realName }}&nbsp;&nbsp;</span>
          <!-- <span class="type">{{ getTypeText(item.type) }}</span> -->
          <span class="price">最低价格：{{ item.price }}元/小时</span>
          <br>
          <span class="title">{{ item.job }}</span>
        </div>
        <br>
        <el-rate class="rate" style="display:inline-block; margin-bottom:8px;" v-model=item.rating disabled show-score></el-rate>

        <div class="description">
          <div class="subdes">
            
            <span class="topic" v-for="(subItem, subIndex) in Experts[index].topics" :key="subIndex"><i
                style="color:white; font-size: 20px;">#&nbsp;</i>{{ subItem.title }}<br v-if="subIndex < item.topics.length - 1" /></span>
                
                <span class="type">{{ getTypeText(item.type) }}</span>
                <i  :class="getTypeIcon(item.type)" class="tagIcon" ></i>


            
          </div>
          <div>
          </div>
        </div>
      </div>
    </el-card>
    </div>
</el-main>
</el-container>
<el-container>
  <Footer :num="num"></Footer>
</el-container>
</div>
  </template>

<style scoped lang="less">


.hr_solid {
		border: 0;
		border-top: 1px solid #d0d0d5;
        float:right;
	}

  .tagIcon{
  position: absolute;  
  float:right;  
  font-size:70px !important;
  opacity: 0.2;
  right:25px;
  top:50px;
}

.com{
    display:inline-block;
}
.iconfont{
    display:inline-block;
    font-size:60px;
}
.psychology{
    display:none;
}
.city{
    color:gray;
}
.name{
    color:black;
    font-size:20px;
}

.title{
    display:inline-block;
    margin-top:8px;
    color:gray;
}
.topic {
  padding: 6px;
  padding-left: 16px;
  padding-right: 18px;
  line-height: 40px;
  font-size:18px;
  width:fit-content;
  border-radius: 18px;
  background-color: rgba(0, 128, 92, 0.573);
  color: #fff;
}
.type {
  display: inline-block;
  float:right;
  margin: 8px;
  padding-left: 12px;
  padding-right:12px;
  border-radius: 8px;
  font-size: 20px;
  font-weight: bold;
  background-color: rgb(44, 136, 157);
  color: white;
  text-align: center;
  line-height: 36px;
}


/deep/.rate .el-rate__text{
  font-size: 18px;
}

/deep/.rate .el-rate__icon{
  font-size: 20px; /* 自定义星星图标大小 */
  line-height: 20px; /* 保持星星图标居中 */
}

.line_01{
    padding: 0 20px 0;
    margin: 20px 0;
    line-height: 5px;
    border-left: 250px solid #ddd;
    border-right: 250px solid #ddd;
    text-align: center;
}

.bg{
    width: 100%;
    height: 100vh;
    background-size: 100% 100%;
    background-position: center center;
    background-image: url("../../img/galaxy.jpg");
    overflow: auto;
    position: relative;
}
.price {
  padding: 4px 8px;
  background-color:rgb(44, 136, 157); 
  color: #fff; 
  //font-weight: bold;
  border-radius: 5px;
  font-size: 1.1em; 
  float: right;
}

.rating{
    margin-top:8px;
  font-size:24px;
  color:coral;
  float:left;
}
  .subdes{
      margin-top:8px;
    font-size:16px;
    margin-bottom:0px;
  }
/deep/.Tname{
  text-align:center;
  font-size:28px;
}

/deep/.description{
  font-size:2px;
}

.el-card {
    position: relative;
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
import Vue from 'vue';
import Vuex from 'vuex';
import Expert from '../expert.vue';
import Footer from '../Screen/Footer.vue';
import Header from '../Screen/Header.vue';
import Test4 from '../Test4.vue';
import Test5 from '../Test5.vue';
Vue.use(Vuex);


export default {
  props:
      ['value']
    ,
  data() {
      return {
        url:"https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry",
        Experts:[],
        currentDate: new Date(),
        num: "third",
        userId: "",
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
      getActiveType(type){
      switch(type){
        case 1:
          return "心理";
        case 2:
          return "学业"; 
        case 3:
          return "就业"; 
      }
    },
    getTypeText(type) {
      switch (type) {
        case 1:
          return "心理";
        case 2:
          return "学业"; 
        case 3:
          return "就业"; 
        default:
          return "未知"; 
      }},
      getTypeIcon(type){
      switch (type) {
        case 1:
          return "iconfont icon-shangyehezuo-copy";
        case 2:
          return "iconfont icon-bianji_edit"; 
        case 3:
          return "iconfont icon-gongzuotai_workbench"; 
        default:
          return "未知"; 
      }},

      getExpert(item, index) {
        console.log(this.Experts[index].expertId);
        var id = this.Experts[index].expertId;
        return id;
        /*this.$router.push({
          path: `/expert/${id}`
        })*/
      },
      async getFav() {
        var data=new FormData();
        data.append('phone', this.userId);
        var config = {
      method: 'post',
      url: '/favoritedirs-expert/GetDirsByUserid',
      data:data
        }
      var res=await axios(config)
      console.log(res.data)
      this.Experts = res.data.data
      return res;
      },
      linkToExpertDetail(item,index) {
        var id = this.getExpert(item, index);
        console.log("跳转到" + id + "对应的行家信息详情");
        this.$router.push( `/ExpertDetailInfo/${id}`);
    },


      async getTopics(length) {
      for (var i = 0; i < length; i++)
{
  var data=new FormData();
        data.append('expertId', this.Experts[i].expertId);
        var config = {
      method: 'post',
      url: '/topic/getById',
      data:data
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
      for (var i = 0; i < length; i++)
{
        var data=new FormData();
        data.append('userId', this.Experts[i].expertId);
        console.log("i的值是" + i);
        console.log("this.expertId"+this.Experts[i].expertId);
        var config = {
      method: 'post',
      url: '/user/myInfo',
      data:data
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
      getTypeText(type) {
      switch (type) {
        case 1:
          return "心理";
        case 2:
          return "学业"; 
        case 3:
          return "就业"; 
        default:
          return "未知"; 
      }},
      
  

        //从接口获取Topic信息，初始化给Topics
        //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
      
  },
  mounted() {
    this.userId = localStorage.getItem('userId');
    console.log("MyCollection页面的userId是" + this.userId);
    this.getFav().then(res => {
      this.Experts = res.data.data;
      // console.log("experts长度是" + this.Experts.length);
      // this.getTopics(this.Experts.length);
      // this.getCity(this.Experts.length);
      // this.getExpertInfo(this.Experts.length);
      // console.log(this.Experts);
    })
  },
  beforeMount() {
  
}
  }
  </script>