<template>
<el-row>
    <el-col :span="24" v-for="(o, index) in 8" :key="o" :offset=6>
      <el-card :body-style="{ padding: '0px'}">

        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

  <style>
.el-card {
    border-radius: 4px;
    border: 1px solid #EBEEF5;
    background-color: #FFF;
    overflow: hidden;
    color: #303133;
    transition: .3s;
    width: 100%; 
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
  export default {
    data() {
      return {
        Topics,
        currentDate: new Date()
      };
  },
    methods: {
      Init() {
        ;//从接口获取Topic信息，初始化给Topics
        //点击某一个Topic，获取到Topic的id，访问id对应的商品页面（把id给到商品详情页，由这个页面来发送请求给后端id，关注一下怎么传值吧！）
      }
    }
  }
  
  </script>