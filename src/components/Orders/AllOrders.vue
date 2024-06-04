<template>
  <div>
    <div>
      <el-card
        :body-style="{ padding: '0px' }"
        v-for="(item, index) in orders"
        :key="index"
      >
        <div style="padding: 14px" :key="index">
          <div>
            <span class="name">{{ orders[index].realName }}&nbsp;&nbsp;</span>
            <span :class="{
              'state1': orders[index].state === '已评价',
              'state2': orders[index].state === '进行中',
              'state3': orders[index].state === '已完成',
              'state4': orders[index].state === '已取消'
            }">
              {{ orders[index].state }}
            </span>
            <br />
          </div>
          <div class="description">
            <div class="subdes">
              <div class="topic-span">{{ "#" + orders[index].title }}</div>
              <span style="color: grey; font-size: 13px;">咨询时间：{{ orders[index].appointTime }}</span>
              <span class="price">{{ orders[index].price }}元/小时</span>
            </div>
            <div class="btns">
              <el-button @click="getExpert(item, index)" style="padding-left: 12px;padding-right: 12px;">查看行家</el-button>
              <el-button @click="cancel(index)" v-if="orders[index].state == '进行中'" style="padding-left: 12px;padding-right: 12px;">取消订单</el-button>
              <el-button @click="confirm(index)" v-if="orders[index].state == '进行中'" style="padding-left: 12px;padding-right: 12px;">完成订单</el-button>
              <el-button @click="linkToComment(index)" v-else-if="orders[index].state == '已完成'" style="padding-left: 12px;padding-right: 12px;">评价订单</el-button>
              <el-button @click="linkToComplaint(index)" v-else-if="orders[index].state == '已评价'" style="padding-left: 12px;padding-right: 12px;">投诉行家</el-button>
              <el-button @click="linkToDelete(index)" icon="el-icon-delete"
                style="padding-left: 10px;padding-right: 10px;float:right;" v-if="orders[index].state ==='已完成'|| orders[index].state === '已评价' || orders[index].state === '已取消'"></el-button>

              <el-dialog
                title="取消订单"
                :visible.sync="CancelVisible"
                width="95%"
                :before-close="handleClose"
              >
                <span>您确认要取消订单吗？</span><br /><br /><br />
                <el-button @click="cancelNow(temp)">确定</el-button>
                <el-button type="primary" @click="handleCancleEvent3"
                  >我再想想</el-button
                >
              </el-dialog>

              <el-dialog
                title="完成订单"
                :visible.sync="ConfirmVisible"
                width="95%"
                :before-close="handleClose"
              >
                <span>您确认订单已完成吗？</span><br /><br /><br />
                <el-button type="primary" @click="confirmFinish(temp)"
                  >确定</el-button
                >
                <el-button @click="handleCancleEvent4">我再想想</el-button>
              </el-dialog>

              <el-dialog
                title="评价"
                :visible.sync="CommentVisible"
                width="85%"
                :before-close="handleClose"
              >
                <el-rate
                  style="margin-bottom: 10px"
                  v-model="mark"
                  show-score
                ></el-rate>
                <el-input
                  type="textarea"
                  v-model="commentContent"
                  placeholder="请输入您的评价"
                ></el-input>
                <div style="margin-top: 20px">
                  <el-button @click="submitComment" type="primary"
                    >提交</el-button
                  >
                  <el-button @click="handleCancleEvent2">取消</el-button>
                </div>
              </el-dialog>

              <el-dialog
                title="投诉"
                :visible.sync="ComplaintVisible"
                width="85%"
                :before-close="handleClose"
              >
                <el-input
                  type="textarea"
                  v-model="complaintContent"
                  placeholder="请输入您的意见"
                ></el-input>
                <div style="margin-top: 20px">
                  <el-button @click="submitComplaint" type="primary"
                    >提交</el-button
                  >
                  <el-button @click="handleCancleEvent">取消</el-button>
                </div>
              </el-dialog>

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
    <br />
    <br /><br />
  </div>
</template>

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
  margin: 10px 0;
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

.state1 {
  border-radius: 100px;
  font-size: small;
  color: green;
  float: right;
  font-weight: bold;
  padding: 2px 6px;
  background-color: rgba(0, 128, 0, 0.35);
}

.state2 {
  border-radius: 100px;
  font-size: small;
  color: rgb(17, 80, 174);
  float: right;
  font-weight: bold;
  padding: 2px 6px;
  background-color: rgb(17, 80, 174, 0.35);
}

.state3 {
  border-radius: 100px;
  font-size: small;
  color: orange;
  float: right;
  font-weight: bold;
  padding: 2px 6px;
  background-color: rgba(255, 166, 0, 0.35);
}

.state4 {
  border-radius: 100px;
  font-size: small;
  color: grey;
  float: right;
  font-weight: bold;
  padding: 2px 6px;
  background-color: rgb(128, 128, 128, 0.35);
}


.name {
  color: black;
  font-size: 20px;
}
</style>

<script>
import axios from "axios";
import { Message } from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import Vue from "vue";
import MakeComment from "../Comments/MakeComment.vue";
import MakeComplaint from "../Complaints/MakeComplaint.vue";
import Test4 from "../Test4.vue";
Vue.prototype.$message = Message;
export default {
  name: "Evaluated",
  components: {
    MakeComment,
    MakeComplaint,
    Test4,
  },
  data() {
    return {
      Orders: [],
      CommentVisible: false,
      ComplaintVisible: false,
      CancelVisible: false,
      ConfirmVisible: false,
      DeleteVisible: false,
      ComplaintFalseVisible: false,
      userId: "",
      orders: [],
      mark: 0,
      temp: 0,
      commentform: {
        name: "",
      },
      complaintform: {
        name: "",
      },
      formLabelWidth: "120px",
      complaintContent: "",
      commentContent: "",
    };
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
    handleCancleEvent4() {
      this.ConfirmVisible = false;
    },
    submitComment() {
      let data = new FormData();
      let index = this.selectedIndex;
      data.append('userId', this.userId);
      data.append('expertId', this.orders[index].expertId);
      data.append('topicId', this.orders[index].topic.topicId);
      data.append('orderId', this.orders[index].orderId);
      data.append('text', this.commentContent);
      data.append('score', this.mark);
      var config = {
        method: "post",
        url: "/review/CreateReview",
        data: data,
      };
      axios(config).then((res) => {
        if (res.data.status === 100) {
          this.$message({
            message: "评价成功",
            type: "success",
          });
          this.queryData().then((res) => {
            this.orders = res.data.data;
          });
        } else {
          this.$message({
            message: "评价失败",
            type: "error",
          });
        }
        this.CommentVisible = false;
      });
    },
    handleCancleEvent5(){
      this.DeleteVisible = false;
    },
    handleCancleEvent3() {
      this.CancelVisible = false;
    },
    handleCancleEvent2() {
      this.CommentVisible = false;
    },
    handleCancleEvent() {
      this.ComplaintVisible = false;
    },
    submitComplaint() {
      let userId = localStorage.getItem("userId");
      let data = new FormData();
      let index = this.selectedIndex;
      data.append("orderId", this.orders[index].orderId);
      data.append("userId", userId);
      data.append("beUserId", this.orders[index].expertId);
      data.append("contents", this.complaintContent);

      var config = {
        method: "post",
        url: "/complaint/CreateComplaint",
        data: data,
      };
      axios(config)
        .then((res) => {
          if (res.data.data == 0) {
            this.$message({
              message: "请勿重复投诉",
              type: "warning",
            });
            this.queryData().then((res) => {
              this.orders = res.data.data;
            });
          } else {
            this.$message({
              message: "投诉成功",
              type: "success",
            });
          }
          this.ComplaintVisible = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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
      data.append("customer_id", this.userId);
      data.append("order_id", this.orders[index].orderId);
      console.log("haidh");
      var config = {
        method: "post",
        url: "/order/ModifyOrderStatusToFinish",
        data: data,
      };
      axios(config)
        .then((res) => {
          if (res.data.status == 100) {
            this.$message({
              message: "操作成功",
              type: "success",
            });
            this.queryData().then((res) => {
              this.orders = res.data.data;
            });
          } else {
            this.$message({
              message: "操作失败",
              type: "error",
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });

      this.ConfirmVisible = false;
    },

    cancelNow(index) {
      var data = new FormData();
      data.append("customer_id", this.userId);
      data.append("order_id", this.orders[index].orderId);

      var config = {
        method: 'post',
        url: '/order/CancleOrder',
        params: {
          orderId: this.orders[index].orderId
        }
      }
      axios(config)
        .then((res) => {
          if (res.data.status === 100) {
            this.$message({
              message: "操作成功",
              type: "success",
            });
            this.queryData().then((res) => {
              this.orders = res.data.data;
            });
          } else {
            this.$message({
              message: "操作失败",
              type: "error",
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });

      this.CancelVisible = false;
    },
    getExpert(item, index) {
      console.log(this.orders[index].expertId);
      var id = this.orders[index].expertId;
      this.$router.push(`/ExpertDetailInfo/${id}`);
    },
    linkToComment(index) {
      this.CommentVisible = true;
      this.temp = index;
      this.selectedIndex = index;
    },
    linkToComplaint(index) {
      this.ComplaintVisible = true;
      this.temp = index;
      this.selectedIndex = index;
    },
    handleClose(done) {
      done();
    },
    async queryData() {
      console.log(this.userId);
      console.log(typeof this.userId);
      var config = {
        method: "get",
        url: "/order/GetOrderByID",
        params: {
          customerId: this.userId
        },
      };
      var res = await axios(config);
      return res;
    },
    ConfirmComplaint(temp) {
      var data = new FormData();

      data.append("Order_id", this.orders[temp].orderId);
      data.append("user_id", this.userId);
      data.append("be_user_id", this.orders[temp].expertId);
      data.append("contents", this.complaintform.name);
      var config = {
        method: "post",
        url: "/complaint/CreateComplaint",
        data: data,
      };

      var that = this;

      axios(config).then(function (response) {
        console.log("response是" + JSON.stringify(response));
        console.log("response.data是" + JSON.stringify(response.data));
        if (response.data.data == "0") {
          that.ComplaintVisible = false;
          that.ComplaintFalseVisible = true;
        } else {
          that.$message.success("投诉成功");
        }
        // location.reload();
        //this.$router.push('/Home');
      });
    },

    //确认评价的函数
    ConfirmComment(temp) {
      var data = new FormData();
      data.append("userId", this.userId);
      data.append("expertId", this.orders[temp].expertId);
      data.append("topicId", this.orders[temp].topicId);
      data.append("orderId", this.orders[temp].orderId);
      data.append("text", this.commentform.name);
      data.append("score", 8);
      var config = {
        method: "post",
        url: "/review/CreateReview",
        data: data,
      };

      var that = this;

      axios(config).then(function (response) {
        console.log("response是" + JSON.stringify(response));
        console.log("response.data是" + JSON.stringify(response.data));
        var data1 = new FormData();
        data1.append("customer_id", that.userId);
        data1.append("order_id", that.orders[temp].orderId);
        var config1 = {
          method: "get",
          url: "/order/ModifyOrderStatusToReview",
          data: data1,
        };
        axios(config1)
          .then(function (res) {
            console.log(res.data);
          })
          .catch(function (error) {
            console.log(error);
          });
        location.reload();
        //this.$router.push('/Home');
      });
    },
    pullData() {
      this.queryData().then((res) => {
        this.orders = res.data.data;
        console.log("jhjjjihi");
      });
    },
  },
  mounted() {
    this.userId = localStorage.getItem("userId");
    this.queryData().then((res) => {
      this.orders = res.data.data;
    });
  },
  activated() {
    this.pullData();
  },
};
</script>
