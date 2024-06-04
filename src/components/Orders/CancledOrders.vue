<template>
    <div>
      <div>
        <el-card :body-style="{ padding: '0px'}" v-if="orders[index].state=='已取消'" v-for="(item,index) in orders" :key="index">
            <div style="padding: 14px;" :key="index">
              <div>
              <span class="name">{{orders[index].realName}}&nbsp;&nbsp;</span>
              <span :class="{
                'state4': orders[index].state === '已取消',
              }">
                {{ orders[index].state }}
              </span>
              <br>
            </div>
  
            <div class="description">
  
  
              <div class="subdes">
                <div class="topic-span">{{"#"+ orders[index].title }}</div>
                <span style="color: grey; font-size: 13px;">咨询时间：{{ orders[index].appointTime }}</span>
                <span class="price">{{ orders[index].price }}元/小时</span>
              </div>
              <div class="btns">
                <el-button @click="getExpert(item, index)"  style="padding-left: 12px;padding-right: 12px;">查看行家</el-button>
                <el-button @click="linkToDelete(index)" icon="el-icon-delete"
                style="padding-left: 10px;padding-right: 10px;float:right;"></el-button>
  
                <el-dialog title="删除" :visible.sync="DeleteVisible" width="85%" :before-close="handleClose">
                <span>您确定删除订单吗？</span><br><br><br>
                <el-button type="primary" @click="deleteOrder(temp)">确定</el-button>
                <el-button @click="handleCancleEvent5">我再想想</el-button>
              </el-dialog>

              
              </div>
            </div>
  
  
  
          </div>
  
        </el-card>
  
      </div>
      <br>
      <br><br>
    </div>
  </template>
   
   
  <script>
  import axios from 'axios';
  import { Message } from 'element-ui';
  import MakeComment from '../Comments/MakeComment.vue';
  import MakeComplaint from '../Complaints/MakeComplaint.vue';
  
  export default {
    name: 'Evaluated',
    components: {
      MakeComment,
      MakeComplaint,
    },
    data() {
      return {
        Orders: [],
        CommentVisible: false,
        ComplaintVisible: false,
        CancelVisible: false,
        ConfirmVisible: false,
        userId: "",
        orders: [],
        selectedIndex: 0,
        complaintContent: "",
        DeleteVisible: false,
      }
    },
    methods: {
        linkToDelete(index){
      this.DeleteVisible = true;
      this.temp = index;
    },
    deleteOrder(index) {

      let data = new FormData();
      data.append('customer_id', this.userId);
      data.append('order_id', this.orders[index].orderId);
      const config = {
        url: '/order/DeleteOrder',
        method: 'post',
        params:{
          customer_id: this.userId,
          order_id: this.orders[index].orderId
        }
      }
      axios(config).then((res) => {
        if (res.data.status === 100) {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.queryData().then(res => {
            this.orders = res.data.data;
          })
        }
        else {
          this.$message({
            message: '删除失败',
            type: 'error'
          });
        }
        this.DeleteVisible = false;
      });
    },
      submitComplaint() {
        let userId = localStorage.getItem('userId');
        let data = new FormData();
        let index = this.selectedIndex;
        data.append('orderId', this.orders[index].orderId);
        data.append('userId', userId);
        data.append('beUserId', this.orders[index].expertId);
        data.append('contents', this.complaintContent);
  
        var config = {
          method: 'post',
          url: '/complaint/CreateComplaint',
          data: data
        };
  
  
        axios(config)
          .then((res) => {
            if (res.data.data == 0) {
              this.$message({
                message: '请勿重复投诉',
                type: 'warning'
              });
            }
            else {
              this.$message({
                message: '投诉成功',
                type: 'success'
              });
            }
            this.ComplaintVisible = false;
          })
          .catch(function (error) {
            console.log(error);
          });
      }
      ,
      handleCancleEvent() {
        this.ComplaintVisible = false;
      },
      getExpert(item, index) {
        console.log(this.orders[index].expertId);
        var id = this.orders[index].expertId;
        this.$router.push(`/ExpertDetailInfo/${id}`);
      },
      cancle(index) {
  
      },
      linkToComment() {
        this.CommentVisible = true;
      },
      linkToComplaint(index) {
        this.ComplaintVisible = true;
        this.selectedIndex = index;
      },
      handleClose(done) {
        done();
      },
      async queryData() {
        var data = new FormData();
        data.append("customer_id", this.userId);
        var config = {
          method: 'get',
          url: '/order/GetOrderByID',
          params: {
            customerId: this.userId
          },
        }
        var res = await axios(config)
        return res;
      },
      pullData(){
        this.queryData().then(res => {
        this.orders = res.data.data;
      })
      }
    },
    mounted() {
      this.userId = localStorage.getItem('userId');
      this.queryData().then(res => {
        this.orders = res.data.data;
      })
    },
    activated(){
      this.pullData();
    }
  }
  </script>
  
   
  <style scoped>
  .topic-span {
    width: fit-content;
    padding: 2px 10px;
    margin: 4px;
    border-radius: 100px;
    font-size: 14px;
    cursor: pointer;
    background-color: rgba(0, 128, 92, 0.573);
    color: #fff;
    margin: 10px 0
  }

  .state4{
    border-radius: 100px;
    font-size: small;
    color: grey;
    float: right;
    font-weight: bold;
    padding: 2px 6px;
    background-color: rgb(128, 128, 128, 0.35);
}
  
  .btns{
    margin-top:20px;
    text-align:center;
  
  }
  
  .el-button {
    border-radius: 10px;
    margin-right: 20px;
    margin-left: 20px;
  }
  
  .price {
    font-size: 17px;
    color: rgb(230, 0, 0);
    float: right;
  }
  
  .topic {
    padding: 0px;
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
  .state1{
    border-radius: 100px;
    font-size: small;
    color: green;
    float: right;
    font-weight: bold;
    padding: 2px 6px;
    background-color: rgba(0, 128, 0, 0.35);
  }
  .state2{
    border-radius: 100px;
    font-size: small;
    color: rgb(17, 80, 174);
    float: right;
    font-weight: bold;
    padding: 2px 6px;
    background-color: rgb(17, 80, 174,0.35);
  }
  
  .state3{
    border-radius: 100px;
    font-size: small;
    color: orange;
    float: right;
    font-weight: bold;
    padding: 2px 6px;
    background-color: rgba(255, 166, 0, 0.35);
  }
  </style>