<template>
    <div> 
        <el-container>
            <el-container>
            <Header />
        </el-container>
        <el-container style="height:710px;">
        <el-main >
      <div>
      <el-card :body-style="{ padding: '0px'}" v-for="(item,index) in orders" :key="index">
          <div style="padding: 14px;" :key="index">
            <div>
            <!-- <span>支付时间:{{ this.year }}/{{ this.month }}/{{ this.day }}&nbsp;{{ this.hour }}:{{ this.minute }}:{{ this.second }}</span> -->
            <span style="font-size:13px;">用户支付时间:&nbsp;{{ orders[index].payTime }}</span>
            <span class="state">&nbsp;{{ orders[index].state }}&nbsp;</span>
            <br>
            </div>
  
            <div class="description" >

              
              <div class="subdes">
                <span class="topic"><i style="color:gray; font-size: 20px;">#&nbsp;</i>{{orders[index].title}}</span>  
                <br>
                <span>{{orders[index].appointTime}}</span>
                <span class="price">{{orders[index].price}}元</span>
            </div>
            <br>
            <span style="font-size:13px;">订单编号：{{ orders[index].orderId }}</span>
            <div class="btns">

              <el-dialog
            title="取消订单"
            :visible.sync="CancelVisible"
            width="95%"
            :before-close="handleClose">
            <span>您确认要取消订单吗？</span><br><br><br>
            <el-button primary @click="cancelNow(temp)">确定</el-button>
            </el-dialog>

            <el-dialog
            title="完成订单"
            :visible.sync="ConfirmVisible"
            width="95%"
            :before-close="handleClose">
            <span>您确认订单已完成吗？</span><br><br><br>
            <el-button primary @click="confirmFinish(temp)">确定</el-button>
            </el-dialog>


              <el-dialog
            title="评价"
            :visible.sync="CommentVisible"
            width="95%"
            :before-close="handleClose">
            <!-- <MakeComment :order_id="orders[index].orderId" :expert_id="orders[index].expertId" :topic_id="orders[index].topicId"/> -->
            <div>
                <el-form :model="commentform">
            <el-form-item label="评论内容" :label-width="formLabelWidth">
              <el-input type="textarea" v-model="commentform.name" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div style="margin-left:0px;">
            <div style="float:right;">
            <Test4></Test4>
            <br>

          </div>

                  <div style="text-align:center;" slot="footer" class="dialog-footer">
                    <br>
                <br>
                <br>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="ConfirmComment(temp)">确 定</el-button>
                  </div>
            </div>
            </div>
          </el-dialog>

            <el-dialog
            title="投诉"
            :visible.sync="ComplaintVisible"
            width="95%"
            :before-close="handleClose">
                      <div>
                <el-form :model="complaintform">
            <el-form-item label="投诉内容" :label-width="formLabelWidth">
              <el-input type="textarea" v-model="complaintform.name" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div style="margin-left:0px;">
                  <div style="text-align:center;" slot="footer" class="dialog-footer">
                <br>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="ConfirmComplaint(temp)">确 定</el-button>
                  </div>
            </div>
              </div>
            </el-dialog>

            <el-dialog
            :visible.sync="ComplaintFalseVisible"
            width="95%"
            :before-close="handleClose">
            <span style="font-size:22px;">请勿重复投诉！</span>
             </el-dialog>

            </div>
            </div>
            
            
            
          </div>
  
        </el-card>
        
      </div>
    </el-main>
    <br>
        <br><br>
</el-container>
      <Footer :num="num"></Footer>
    </el-container>   
    </div>

  </template>
   
<style scoped>
.btns{
  margin-top:20px;
  text-align:center;

}

.el-button{
  border-radius:10px;
}
.price{
  font-size:20px;
  color:red;
  float:right;
}
.topic{
    padding:0px;
    font-size:18px;
    margin-top: 60px;
    line-height: 28px;
}
.state{
  border-radius:4px;
  text-align:center;
  line-height: 30px;
  float:right;
  color:forestgreen;
  background-color:lightblue;
  height:30px;
}
.name{
    color:black;
    font-size:20px;
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
import axios from 'axios';
import MakeComment from '../Comments/MakeComment.vue';
import MakeComplaint from '../Complaints/MakeComplaint.vue';
import Footer from '../Screen/Footer.vue';
import Header from '../Screen/Header.vue';
import Test4 from '../Test4.vue';
  export default {
  name: 'Evaluated',
  components: {
    MakeComment,
    MakeComplaint,
      Test4,
      Footer,
    Header
  },
  data() {
    return {
      Orders: [],
      CommentVisible:false,
      ComplaintVisible: false,
      CancelVisible: false,
      ConfirmVisible: false,
      ComplaintFalseVisible: false,
        userId: "",
      
      orders: [],
        temp: Number,
      num:"third",
      commentform: {
        name: '',
      },
      complaintform: {
          name: '',
      },
      formLabelWidth: '120px',
    }
  },
  methods: {
    cancel(index) {
      this.CancelVisible = true;
      this.temp = index;
    },
    confirm(index) {
      this.ConfirmVisible = true;
      this.temp = index;
    },
    confirmFinish(index) {
      var data = new FormData();
      data.append("customer_id",this.userId);
      data.append("order_id", this.orders[index].orderId);
      console.log(this.userId);
      console.log(this.orders[index].orderId);

      var config={
        method: 'post',
        url: '/order/ModifyOrderStatusToFinish',
        data: data
    }
      axios(config)
        .then(function (response) {
          console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
          console.log(error);
        });
      
      this.ConfirmVisible = false;
      location.reload();
      
    },

    cancelNow(index) {
      var data = new FormData();
      data.append("customer_id",this.userId);
      data.append("order_id", this.orders[index].orderId);
      console.log(this.userId);
      console.log(this.orders[index].orderId);

      var config={
        method: 'post',
        url: '/order/DeleteOrder',
        data: data
    }
      axios(config)
        .then(function (response) {
          console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
          console.log(error);
        });
      
      this.ConfirmVisible = false;
      location.reload();
      
    },
    getExpert(item, index) {
        console.log(this.orders[index].expertId);
        var id = this.orders[index].expertId;
        this.$router.push( `/ExpertDetailInfo/${id}`);
    },
    linkToComment(index) {
      this.CommentVisible = true;
      this.temp = index;
    },
    linkToComplaint(index) {
      this.ComplaintVisible = true;
      this.temp = index;
    },
    handleClose(done) {
            done();
    },
    async queryData() {
      var data = new FormData();
        data.append("expert_id", this.userId);
        console.log("这里的id是" + this.userId);
      var config = {
      method: 'post',
          url: '/order/GetOrdersetByExpertID',
          
        data: data,
    }
        var res = await axios(config)
        console.log(res);
      return res;
    },

    ConfirmComplaint(temp) {
      console.log(this.orders[temp].orderId);
      console.log(this.orders[temp].expertId);
      console.log(this.userId);
      console.log(this.complaintform.name);
      console.log("直接跳转到订单页");
      console.log("您已完成投诉！");//改成弹窗提示
      var data = new FormData();
      
      data.append('Order_id', this.orders[temp].orderId);
      data.append('user_id', this.userId);
      data.append('be_user_id', this.orders[temp].expertId);
      data.append('contents', this.complaintform.name);
      var config = {
        method: 'post',
        url: '/complaint/CreateComplaint',
        data: data
      };

      var that = this;

      axios(config)
        .then(function (response) {
          console.log("response是" + JSON.stringify(response));
          console.log("response.data是" + JSON.stringify(response.data));
          if (response.data.data == '0') {
            that.ComplaintVisible = false;
            that.ComplaintFalseVisible = true;
          }
         // location.reload();
          //this.$router.push('/Home');
        })
    },




    //确认评价的函数
    ConfirmComment(temp) {
      console.log(this.orders[temp].orderId);
      console.log(this.orders[temp].expertId);
      console.log(this.userId);
      console.log(this.commentform.name);
      console.log("直接跳转到订单页");
      console.log("您已完成投诉！");//改成弹窗提示
      var data = new FormData();
      data.append('userId', this.userId);
      data.append('expertId', this.orders[temp].expertId);
      data.append('topicId', this.orders[temp].topicId);
      data.append('orderId', this.orders[temp].orderId);
      data.append('text', this.commentform.name);
      data.append('score', 8);
      var config = {
        method: 'post',
        url: '/review/CreateReview',
        data: data
      };

      var that = this;

      axios(config)
        .then(function (response) {

          console.log("response是" + JSON.stringify(response));
          console.log("response.data是" + JSON.stringify(response.data));
          var data1 = new FormData();
          data1.append('customer_id', that.userId);
          data1.append('order_id', that.orders[temp].orderId);
          var config1 = {
            method: 'post',
            url: '/order/ModifyOrderStatusToReview',
            data: data1,
          }
          axios(config1)
            .then(function (res) {
              console.log(res.data);
            }
            )
            .catch(function (error) {
              console.log(error);
            });
          location.reload();
          //this.$router.push('/Home');
        })
    },
  },
  mounted() {
    this.userId = localStorage.getItem('userId');
    this.queryData().then(res => {
      console.log("res是");
      console.log("类型是" + typeof (res.data.data));
        console.log(res.data.data);
        console.log(res.data);
      console.log(res);
        this.orders = res.data.data;
        var length = this.orders.length;
        for (var i = 0; i < length; i++)
        {
            this.$set(this.orders[i], "year", this.orders[i].payTime[0] + this.orders[i].payTime[1] + this.orders[i].payTime[2] + this.orders[i].payTime[3]);
            this.$set(this.orders[i], "month", this.orders[i].payTime[4] + this.orders[i].payTime[5]);
            this.$set(this.orders[i], "day", this.orders[i].payTime[6] + this.orders[i].payTime[7]);
            this.$set(this.orders[i], "hour", this.orders[i].payTime[8] + this.orders[i].payTime[9]);
            this.$set(this.orders[i], "minute", this.orders[i].payTime[10] + this.orders[i].payTime[11]);
            this.$set(this.orders[i], "second", this.orders[i].payTime[12] + this.orders[i].payTime[13]);

            this.orders[i].payTime =
                this.orders[i].year + '/' + this.orders[i].month + '/' + this.orders[i].day + '  ' +
                this.orders[i].hour + ':' + this.orders[i].minute + ':' + this.orders[i].second 
            console.log(this.orders[i]);
            /*
            this.orders[i].month = 
            this.orders[i].day = ;
            this.orders[i].hour = ;
            this.orders[i].minute = ;
            this.orders[i].second = ;*/
        }
      console.log(this.orders);
    })
    
    }
  }
  </script>
   

   
 