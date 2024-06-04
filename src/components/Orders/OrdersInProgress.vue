<template>
  <div>
    <div>
      <el-card :body-style="{ padding: '0px' }" v-if="orders[index].state == '进行中'" v-for="(item, index) in orders"
        :key="index">
        <div style="padding: 14px;" :key="index">
          <div>
            <span class="name">{{ orders[index].realName }}&nbsp;&nbsp;</span>
            <span :class="{
              'state1': orders[index].state === '已评价',
              'state2': orders[index].state === '进行中',
              'state3': orders[index].state === '已完成'
            }">
              {{ orders[index].state }}
            </span>
            <br>
          </div>


          <div class="subdes">
            <div class="topic-span">{{ orders[index].title }}</div>
            <span style="color: grey; font-size: 13px;">咨询时间：{{ orders[index].appointTime }}</span>
            <span class="price">{{ orders[index].price }}元/小时</span>
          </div>
          <div class="btns">
            <el-button @click="getExpert(item, index)" style="padding-left: 12px;padding-right: 12px;">查看行家</el-button>
            <el-button @click="CancelVisible = true" v-if="orders[index].state == '进行中'" style="padding-left: 12px;padding-right: 12px;">取消订单</el-button>
            <el-button @click="ConfirmVisible = true, selectedIndex = index"
              v-if="orders[index].state == '进行中'" style="padding-left: 12px;padding-right: 12px;">完成订单</el-button>
            <el-button @click="linkToComment()" v-else-if="orders[index].state == '已完成'" style="padding-left: 12px;padding-right: 12px;">评价订单</el-button>
            <el-button @click="linkToComplaint()" v-else-if="orders[index].state == '已评价'" style="padding-left: 12px;padding-right: 12px;">投诉行家</el-button>
            <el-button @click="linkToDelete(index)" icon="el-icon-delete"
                style="padding-left: 10px;padding-right: 10px;float:right;"></el-button>

            <el-dialog title="取消订单" :visible.sync="CancelVisible" width="95%" :before-close="handleClose">
              <span>您确认要取消订单吗？</span><br><br><br>
              <el-button @click="cancleOrder(index)">确定</el-button>
              <el-button type="primary" @click="handleCancleEvent">我再想想</el-button>
            </el-dialog>

            <el-dialog title="完成订单" :visible.sync="ConfirmVisible" width="95%" :before-close="handleClose">
              <span>您确认订单已完成吗？</span><br><br><br>
              <el-button type="primary" @click="handleCompleteEvent">确定</el-button>
              <el-button @click="handleCloseEvent2">我再想想</el-button>
            </el-dialog>

            <el-dialog title="删除" :visible.sync="DeleteVisible" width="85%" :before-close="handleClose">
                <span>您确定删除订单吗？</span><br><br><br>
                <el-button type="primary" @click="deleteOrder(temp)">确定</el-button>
                <el-button @click="handleCancleEvent5">我再想想</el-button>
              </el-dialog>

          </div>
        </div>

    </el-card>

  </div>
  <br>
  <br><br>
</div></template>
 
 
<script>
import axios from 'axios';
import MakeComment from '../Comments/MakeComment.vue';
import MakeComplaint from '../Complaints/MakeComplaint.vue';
import { Message } from 'element-ui';
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
    cancleOrder(index) {
      var config = {
        method: 'post',
        url: '/order/CancelOrder',
        params:{
          orderId: this.orders[index].orderId
        }
      }
      axios(config)
        .then(res => {
          if (res.data.status == 100) {
            this.$message({
              message: '操作成功',
              type: 'success'
            });
            this.queryData().then(res => {
              this.orders = res.data.data;
            })
          }
          else {
            this.$message({
              message: '操作失败',
              type: 'error'
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });

      this.CancelVisible = false;
      this.queryData().then(res => {
        this.orders = res.data.data;
      })

    },
    handleCancleEvent() {
      this.CancelVisible = false;
    },
    storeSelectedIndex(index) {
      this.selectedIndex = index;
    },
    handleCloseEvent2() {
      this.ConfirmVisible = false;
    },
    handleCompleteEvent() {
      var data = new FormData();
      data.append("customer_id", this.userId);
      data.append("order_id", this.orders[this.selectedIndex].orderId);
      var config = {
        method: 'post',
        url: '/order/ModifyOrderStatusToFinish',
        data: data
      }
      axios(config)
        .then(res => {
          if (res.data.status == 100) {
            this.$message({
              message: '操作成功',
              type: 'success'
            });
            this.queryData().then(res => {
              this.orders = res.data.data;
            })
          }
          else {
            this.$message({
              message: '操作失败',
              type: 'error'
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      this.ConfirmVisible = false;
    },

    getExpert(item, index) {
      console.log(this.orders[index].expertId);
      var id = this.orders[index].expertId;
      this.$router.push(`/ExpertDetailInfo/${id}`);
    },
    linkToComment() {
      this.CommentVisible = true;
    },
    linkToComplaint() {
      this.ComplaintVisible = true;
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
    pullData() {
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
  activated() {
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

.btns {
  margin-top: 20px;
  text-align: center;

}

.el-button {
  border-radius: 10px;
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