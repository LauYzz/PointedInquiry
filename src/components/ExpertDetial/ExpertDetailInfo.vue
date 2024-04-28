<template>
  <el-container>
    <Header />
    <el-container>
      <el-main style="height: 710px">
        <el-card>
          <link
            rel="stylesheet icon"
            href="//at.alicdn.com/t/c/font_3828548_ji2jrn939.css"
          />
          <span class="name">{{ ExpertDetailInfo.realName }}</span>
          <span class="title">{{ ExpertDetailInfo.job }}</span>
          <div class="infos">
            <span class="sub"
              ><i class="iconfont icon-shoucang"></i
              >{{ ExpertDetailInfo.rating }}分</span
            >
            <span class="sub">￥{{ ExpertDetailInfo.price }}元</span>
          </div>
          <div>
            <br />
            <span class="subtitle"
              ><i class="el-icon-chat-dot-round"></i>&nbsp;咨询话题</span
            >
            <div
              v-for="(item, index) in ExpertDetailInfo.topics"
              :key="'new' + index"
              class="topic-container"
            >
              <span class="topic-span">{{ "#" + item.title }}</span>
            </div>
          </div>
          <br />
          <div>
            <span class="subtitle"
              ><i class="el-icon-tickets"></i>&nbsp;个人简介</span
            >
            <br />
            <span>{{ ExpertDetailInfo.description }}</span>
          </div>
          <br />
          <span class="subtitle"
            ><i class="el-icon-medal"></i>&nbsp;用户评价</span
          >
          <el-card
            v-for="(item, index1) in ExpertDetailInfo.reviews"
            :key="index1"
          >
            <div
              style="
                display: flex;
                justify-content: space-between;
                align-items: center;
              "
            >
              <div style="display: flex; align-items: center">
                <img
                  src="../../img/avatar.svg"
                  style="width: 30px; height: 30px"
                />
                <div
                  style="
                    display: flex;
                    flex-direction: column;
                    margin-left: 5px;
                  "
                >
                  <div
                    style="
                      font-weight: bolder;
                      display: flex;
                      align-items: center;
                    "
                  >
                    {{ item.userId }}
                  </div>
                  <div
                    style="
                      display: flex;
                      align-items: center;
                      font-size: 12px;
                      color: rgb(163, 162, 162);
                    "
                  >
                    {{ item.time }}
                  </div>
                </div>
              </div>
              <div style="margin-left: auto">
                <el-rate v-model="item.score" disabled></el-rate>
              </div>
            </div>
            <br />
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ item.text }}</div>
            <div style="float: right; margin-bottom: 8px"></div>
          </el-card>
          <div class="btns">
            <br />
            <el-button @click="favoriteAction()" :class="favoriteClass" round>
              {{ favoriteText }}
            </el-button>
            <el-button @click="linkToConfirmOrder()" round>立即咨询</el-button>
            <el-dialog
              title="订单支付"
              :visible.sync="dialogVisible"
              width="95%"
              :before-close="handleClose"
            >
              <ConfirmOrder
                :expert_id="expert_id"
                :name="ExpertDetailInfo.realName"
                @close="handleCloseEvent"
              />
            </el-dialog>
          </div>
        </el-card>
      </el-main>
    </el-container>
    <Footer :num="num"></Footer>
  </el-container>
</template>

<style scoped lang="less">
.topic-container {
  display: flex;
  flex-wrap: wrap;
}

/deep/.favorited-button {
  background-color: darkred;
  /* 自定义喜欢时按钮的背景颜色 */
  color: white;
}

/deep/.unfavorited-button {
  background-color: darkgreen;
  /* 自定义喜欢时按钮的背景颜色 */
  color: white;
}

/deep/ .el-rate__icon {
  font-size: 15px;
  margin-right: 0px;
}

.topic-span {
  width: fit-content;
  padding: 2px 10px;
  margin: 5px;
  border-radius: 100px;
  font-size: 16px;
  background-color: rgba(0, 128, 92, 0.573);
  color: #fff;
}

.el-card {
  border-radius: 4px;
  border: 1px solid #ebeef5;
  background-color: #fff;
  overflow: hidden;
  color: #303133;
  transition: 0.3s;
  width: 100%;
}

.btns {
  text-align: center;
}

.subtitle {
  font-size: 20px;
  height: 30px;
  line-height: 30px;
}

.topics {
  padding: 0px;
  line-height: 28px;
  height: 28px;
}

.sub {
  margin-right: 12px;
  margin-left: 12px;
  color: darkgray;
}

.infos {
  text-align: center;
}

.name {
  width: 100%;
  display: inline-block;
  text-align: center;
  height: 48px;
  line-height: 48px;
  font-size: 36px;
  white-space: nowrap;
  /*强制span不换行*/
  overflow: hidden;
  /*超出宽度部分隐藏*/
  text-overflow: ellipsis;
  /*超出部分以点号代替*/
}

.title {
  width: 100%;
  display: inline-block;
  text-align: center;
  height: 28px;
  line-height: 28px;
  font-size: 16px;
  white-space: nowrap;
  /*强制span不换行*/
  overflow: hidden;
  /*超出宽度部分隐藏*/
  text-overflow: ellipsis;
  /*超出部分以点号代替*/
  color: darkgray;
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
import Footer from "../Screen/Footer.vue";
import Header from "../Screen/Header.vue";
import ConfirmOrder from "../Orders/confirmOrder.vue";
import Test5 from "../Test5.vue";
export default {
  components: {
    Footer,
    Header,
    Test5,
    ConfirmOrder,
  },
  data() {
    return {
      num: "none",
      ExpertDetailInfo: [],
      dialogVisible: false,
      expert_id: "",
      topics: [],
      comments: [],
      price: Number,
      city: "",
      NewFavVisible: false,
      HaveFavVisible: false,
      isFavorited: false,
    };
  },
  computed: {
    favoriteClass() {
      return {
        "favorited-button": this.isFavorited === 1,
        "unfavorited-button": this.isFavorited === 0,
      };
    },
    favoriteText() {
      return this.isFavorited === 0 ? "添加收藏" : "移除收藏";
    },
  },
  methods: {
    getFavoriteMethod() {
      return this.isFavorited === 0
        ? this.addToFavorites
        : this.removeFromFavorites;
    },
    favoriteAction() {
      // 动态调用绑定的方法
      this.getFavoriteMethod()();
      // 更新 isFavorited 的值
      this.isFavorited = 1 - this.isFavorited;
    },

    handleCloseEvent(data) {
      this.dialogVisible = data;
      console.log(data);
    },
    linkToConfirmOrder() {
      this.dialogVisible = true;
    },
    removeFromFavorites() {
      console.log("移除收藏");
      var data = new FormData();
      data.append("phone", this.userId);
      data.append("expertId", this.expert_id);
      var config = {
        method: "post",
        url: "/favoritedirs-expert/DeleteDirsByUserid",
        data: data,
      };
      axios(config).then((res) => {
        console.log(res.data.msg);
        if (res.data.msg == "操作成功") {
          this.NewFavVisible = true;
        } else if (res.data.msg == "操作失败") {
          this.HaveFavVisible = true;
        }
      });
    },
    addToFavorites1() {
      console.log("添加收藏");
    },
    addToFavorites() {
      var data = new FormData();
      data.append("phone", this.userId);
      data.append("expertId", this.expert_id);
      var config = {
        method: "post",
        url: "/favoritedirs-expert/CreateDirsByUserid",
        data: data,
      };
      axios(config).then((res) => {
        console.log(res.data.msg);
        if (res.data.msg == "操作成功") {
          this.NewFavVisible = true;
        } else if (res.data.msg == "操作失败") {
          this.HaveFavVisible = true;
        }
      });
    },
    handleClose(done) {
      done();
    },

    async getTopics() {
      var data = new FormData();
      data.append("expertId", this.expert_id);
      var config = {
        method: "get",
        url: "/topic/getById",
        params: {
          expertId: this.expert_id,
        },
      };
      var that = this;
      await axios(config)
        .then(function (response) {
          that.topics = response.data.data.topics;
          that.price = response.data.data.minPrice[0];
          console.log(that.topics);
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    async getComments() {
      var data = new FormData();
      data.append("expert_id", this.expert_id);
      var config = {
        method: "post",
        url: "/review/GetReviewByExpertID",
        data: data,
      };
      var that = this;
      await axios(config)
        .then(function (response) {
          that.comments = response.data.data;
          var length = that.comments.length;
          for (var i = 0; i < length; i++) {
            that.comments[i].year =
              that.comments[i].time[0] +
              that.comments[i].time[1] +
              that.comments[i].time[2] +
              that.comments[i].time[3];
            that.comments[i].month =
              that.comments[i].time[4] + that.comments[i].time[5];
            that.comments[i].day =
              that.comments[i].time[6] + that.comments[i].time[7];
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    async getCity() {
      var data = new FormData();
      data.append("userId", this.expert_id);
      console.log(this.expert_id);
      var config = {
        method: "post",
        url: "/user/myInfo",
        data: data,
      };
      var that = this;
      await axios(config)
        .then(function (response) {
          that.city = response.data.data.city;
          console.log(that.city);
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    async queryData() {
      var config = {
        method: "get",
        url: "/expert/getOne",
        params: {
          expertId: this.expert_id,
        },
      };
      var res = await axios(config);
      return res;
    },

    async isFavorite() {
      var userId = localStorage.getItem("userId");
      console.log("userId:" + userId);
      var expertId = this.$route.params.id;
      console.log("expertId:" + expertId);
      var config = {
        method: "get",
        url: "user/checkCollectDir",
        params: {
          userId: userId,
          expertId: expertId,
        },
      };
      var res = await axios(config);
      console.log("res.data" + res.data.data);
      return res;
    },
  },
  mounted() {
    this.userId = localStorage.getItem("userId");
    this.expert_id = this.$route.params.id;
    var that = this;
    var config = {
      method: "get",
      url: "/expert/getOne",
      params: {
        expertId: this.expert_id,
      },
    };
    axios(config).then((res) => {
      that.ExpertDetailInfo = res.data.data;
    });
    this.isFavorite().then((res) => {
      console.log("这个是回来的值：" + res.data.data);
      that.isFavorited = res.data.data;
    });
  },
};
</script>
