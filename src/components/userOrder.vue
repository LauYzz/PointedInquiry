<script>
import axios from "axios";
import Vue from 'vue';
import Vuex from 'vuex';
import Expert from './expert.vue';
import Test5 from "./Test5.vue";
Vue.use(Vuex);


export default {
  props:
      ['value']
    ,
  data() {
      return {
        url:"https://www.fastmock.site/mock/250ee180a111c6d746fee10f8955645e/UserOrders/Orders",
        Orders:[],
        currentDate: new Date()
      };
  },
  components: {
      Expert,
      Test5
  },
    methods: {
      getExpert(item, index) {
        console.log(this.Orders[index].promoter);
        var id = this.Orders[index].promoter;
        this.$router.push({
          path: `/expert/${id}`
        })
      },
  

        //从接口获取Topic信息，初始化给Orders
        //点击某个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
      
  },
  mounted() {
    axios.get(this.url).then(res => {
          this.Orders = res.data;
      console.log(this.Orders);
      var length = this.Orders.length;
      console.log(length);
      for (var i = 0; i < length; i++)
      {
        if (this.Orders[i].way === '0') {
          console.log(this.Orders[i].way);
          this.Orders[i].by = '线上约聊';
        }
        else {
          console.log(this.Orders[i].way)
          this.Orders[i].by = '线下约聊';
        }
      }
        })
    }
  }
  </script>
<template>
    <div class="bg">
    <el-row>
        <br>
        <el-col :span="24" v-for="(item,index) in Orders" :key="index" :offset=3 >
          <el-card :body-style="{ padding: '0px'}" >
            <div style="padding: 14px;" :key="index"  @click="getExpert(item,index)">
              <div class="Tname">{{Orders[index].orderTopic}}</div>
              
              <div class="description" >
                <br>
                <span class="leftsubdes">下单时间：{{Orders[index].orderTime}}</span>
                <span class="rightsubdes">约聊方式：{{Orders[index].by}}</span>
                <br>
                <br>
                <span class="leftsubdes">咨询行家：{{Orders[index].promoter}}</span>
                <span class="rightsubdes">话题id：{{Orders[index].topic_id}}</span>
                <br>
                <br>
                <span class="leftsubdes">订单状态：{{Orders[index].status}}</span>
                <span class="rightsubdes">订单id：{{Orders[index].orderId}}</span>

                <br>
                <br>
              </div>
              
              <span class="price">已支付￥{{Orders[index].price}}&nbsp;&nbsp;&nbsp;&nbsp;</span>
              <Test5 :fatherScore="Orders[index].rating" class="rating"/>
              <span class="rating">{{Orders[index].rating}}分</span>
              <br>
              <br>
              <el-button-group class="btn" round>
                  <el-button class="detail">详情</el-button>
                  <el-button class="review">评价</el-button>
                  <el-button class="complaint">投诉</el-button>
              </el-button-group>
            </div>
          </el-card>
        </el-col>
      </el-row>
      </div>
    </template>
    
<style scoped lang="less">

.btn{
    
    position:relative;
    width:100%;
    text-align:center;
    float:right;
}
.review{
    margin-left:100px;
}

.complaint{
    margin-right:100px;
}
.bg{
    width: 100%;
    height: 100vh;
    background-size: 100% 100%;
    background-position: center center;
    background-image: url("../img/galaxy.jpg");
    overflow: auto;
    position: relative
}

    .price{
      margin-left:20px;
      font-size:28px;
      color:red;
    }
    

    .rating{
      font-size:28px;
      color:coral;
      float:right;
      margin-right:20px;
    }
      .leftsubdes{
        display:inline-block;
        font-size:20px;
        width:240px;
        margin-right:60px;
      }

      .rightsubdes{
          width:40%;
          font-size:20px;
      }
    /deep/.Tname{
      font-size:50px;
    }
    
    /deep/.description{
      font-size:2px;
    }
    
    .el-card {
        border-radius: 24px;
        border: 1px solid #EBEEF5;
        background-color: #FFF;
        overflow: hidden;
        color: #303133;
        transition: .3s;
        width: 560px; 
        margin-bottom: 30px;
        height: 320px;
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

.btn{
    
    position:relative;
    width:100%;
    text-align:center;
    float:right;
}
.review{
    margin-left:100px;
}

.complaint{
    margin-right:100px;
}
.bg{
    width: 100%;
    height: 100vh;
    background-size: 100% 100%;
    background-position: center center;
    background-image: url("../img/galaxy.jpg");
    overflow: auto;
    position: relative
}

    .price{
      margin-left:20px;
      font-size:28px;
      color:red;
    }
    

    .rating{
      font-size:28px;
      color:coral;
      float:right;
      margin-right:20px;
    }
      .leftsubdes{
        display:inline-block;
        font-size:20px;
        width:240px;
        margin-right:60px;
      }

      .rightsubdes{
          width:40%;
          font-size:20px;
      }
    /deep/.Tname{
      font-size:50px;
    }
    
    /deep/.description{
      font-size:2px;
    }
    
    .el-card {
        border-radius: 24px;
        border: 1px solid #EBEEF5;
        background-color: #FFF;
        overflow: hidden;
        color: #303133;
        transition: .3s;
        width: 560px; 
        margin-bottom: 30px;
        height: 320px;
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

.btn{
    
    position:relative;
    width:100%;
    text-align:center;
    float:right;
}
.review{
    margin-left:100px;
}

.complaint{
    margin-right:100px;
}
.bg{
    width: 100%;
    height: 100vh;
    background-size: 100% 100%;
    background-position: center center;
    background-image: url("../img/galaxy.jpg");
    overflow: auto;
    position: relative
}

    .price{
      margin-left:20px;
      font-size:28px;
      color:red;
    }
    

    .rating{
      font-size:28px;
      color:coral;
      float:right;
      margin-right:20px;
    }
      .leftsubdes{
        display:inline-block;
        font-size:20px;
        width:240px;
        margin-right:60px;
      }

      .rightsubdes{
          width:40%;
          font-size:20px;
      }
    /deep/.Tname{
      font-size:50px;
    }
    
    /deep/.description{
      font-size:2px;
    }
    
    .el-card {
        border-radius: 24px;
        border: 1px solid #EBEEF5;
        background-color: #FFF;
        overflow: hidden;
        color: #303133;
        transition: .3s;
        width: 560px; 
        margin-bottom: 30px;
        height: 320px;
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


