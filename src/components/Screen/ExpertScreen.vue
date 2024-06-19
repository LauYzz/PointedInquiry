<template>
  <div class="bg">
    <link
      rel="stylesheet icon"
      href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css"
    />
    <div style="display: flex; background-color: white">
      <el-input
        style="flex: 8; border-color: rgb(11, 141, 130)"
        v-model="keywords"
      ></el-input>
      <el-button style="flex: 1" type="primary" @click="search">搜索</el-button>
    </div>
    <div class="icons">
      <span
        class="com"
        :class="{ active: selectedType === 1 }"
        @click="changeView(1)"
      >
        <i class="iconfont icon-shangyehezuo-copy"></i>
        <div style="text-align: center">心理</div>
      </span>
      <span
        class="com"
        :class="{ active: selectedType === 2 }"
        style="margin-left: 50px; margin-right: 50px"
        @click="changeView(2)"
      >
        <i class="iconfont icon-bianji_edit"></i>
        <div style="text-align: center">学业</div>
      </span>
      <span
        class="com"
        :class="{ active: selectedType === 3 }"
        @click="changeView(3)"
      >
        <i class="iconfont icon-gongzuotai_workbench"></i>
        <div style="text-align: center">就业</div>
      </span>
    </div>
    <div class="sort-buttons">
      <el-button class="sort-button" @click="sortByRating">
        评分
        <i class="el-icon-sort" style="margin-left: 5px"></i>
      </el-button>
      <el-button class="sort-button" @click="sortByReviews">
        评论量
        <i class="el-icon-sort" style="margin-left: 5px"></i>
      </el-button>
    </div>
    <el-card
      :body-style="{ padding: '0px' }"
      v-for="(item, index) in Experts"
      :key="index"
    >
      <div style="padding: 14px" @click="linkToExpertDetail(item, index)">
        <div>
          <span class="name">{{ item.realName }}&nbsp;&nbsp;</span>
          <span class="price">最低价格：{{ item.price }}元/小时</span>
          <br />
          <span class="title">{{ item.job }}</span>
        </div>
        <br />
        <el-rate
          class="rate"
          v-model="item.rating"
          disabled
          show-score
        ></el-rate>
        <div class="description">
          <div class="subdes">
            <span
              class="topic"
              v-for="(subItem, subIndex) in item.topics"
              :key="subIndex"
            >
              <i style="color: white; font-size: 20px">#&nbsp;</i
              >{{ subItem.title }}
              <br v-if="subIndex < item.topics.length - 1" />
            </span>
            <span class="type">{{ getTypeText(item.type) }}</span>
            <i :class="getTypeIcon(item.type)" class="tagIcon"></i>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
import Expert from "../expert.vue";
import Test4 from "../Test4.vue";
import Test5 from "../Test5.vue";

export default {
  data() {
    return {
      selectedType: 1,
      keywords: "",
      Experts: [],
    };
  },
  components: {
    Expert,
    Test4,
    Test5,
  },
  methods: {
    search() {
      const config = {
        url: "/expert/search",
        method: "get",
        params: {
          content: this.keywords,
        },
      };
      axios(config)
        .then((res) => {
          this.Experts = res.data.data;
        })
        .catch((error) => {
          console.log(error);
        });
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
      }
    },
    getTypeIcon(type) {
      switch (type) {
        case 1:
          return "iconfont icon-shangyehezuo-copy";
        case 2:
          return "iconfont icon-bianji_edit";
        case 3:
          return "iconfont icon-gongzuotai_workbench";
        default:
          return "未知";
      }
    },
    sortByRating() {
      console.log("sortByRating clicked");
      const config = {
        url: "/expert/getAllByRating",
        method: "get",
        params: {
          type: this.selectedType,
        },
      };
      axios(config)
        .then((res) => {
          if (res.data && res.data.data) {
            this.Experts = res.data.data;
          } else {
            console.error("Unexpected response format:", res);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    sortByReviews() {
      console.log("sortByReviews clicked");
      const config = {
        url: "/expert/getAllByCommentCount",
        method: "get",
        params: {
          type: this.selectedType,
        },
      };
      axios(config)
        .then((res) => {
          if (res.data && res.data.data) {
            // console.log("sortByReviews response:", res.data.data);
            this.Experts = res.data.data;
          } else {
            console.error("Unexpected response format:", res);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async queryData() {
      const config = {
        method: "get",
        url: "/expert/getAll",
        params: {
          type: this.selectedType,
        },
      };
      const res = await axios(config);
      if (res.data && res.data.data) {
        this.Experts = res.data.data;
      } else {
        console.error("Unexpected response format:", res);
      }
      return res;
    },
    getExpert(item, index) {
      return this.Experts[index].phone;
    },
    linkToExpertDetail(item, index) {
      const id = this.getExpert(item, index);
      this.$router.push(`/ExpertDetailInfo/${id}`);
    },
    async changeView(index) {
      this.selectedType = index;
      const config = {
        url: "/expert/getAll",
        params: {
          type: index,
        },
      };
      axios(config).then((res) => {
        if (res.data && res.data.data) {
          this.Experts = res.data.data;
        } else {
          console.error("Unexpected response format:", res);
        }
      });
    },
  },
  mounted() {
    this.queryData();
  },
};
</script>

<style scoped lang="less">
.active {
  color: rgb(44, 130, 130) !important;
}

.tagIcon {
  position: absolute;
  float: right;
  font-size: 70px !important;
  opacity: 0.2;
  right: 25px;
  top: 50px;
}

.icons {
  padding: 10px;
  background-color: white;
  display: flex;
  justify-content: center;
}

.sort-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 10px;
  margin-bottom: 10px;
}

.sort-button {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.com {
  display: inline-block;
  color: gray;
}

.iconfont {
  font-size: 60px;
}

.el-input {
  border-radius: 100px;
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
  padding: 6px;
  padding-left: 16px;
  padding-right: 18px;
  line-height: 40px;
  font-size: 18px;
  width: fit-content;
  border-radius: 18px;
  background-color: rgba(0, 128, 92, 0.573);
  color: #fff;
}

.type {
  display: inline-block;
  float: right;
  margin: 8px;
  padding-left: 12px;
  padding-right: 12px;
  border-radius: 8px;
  font-size: 20px;
  font-weight: bold;
  background-color: rgb(44, 136, 157);
  color: white;
  text-align: center;
  line-height: 36px;
}

.bg {
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
  background-color: rgb(44, 136, 157);
  color: #fff;
  border-radius: 5px;
  font-size: 1.1em;
  float: right;
}

.rating {
  margin-top: 8px;
  font-size: 24px;
  color: coral;
  float: left;
}

.subdes {
  font-size: 16px;
  margin-bottom: 0px;
}

.el-card {
  position: relative;
  border-radius: 2px;
  background-color: #fff;
  overflow: hidden;
  transition: 0.3s;
  margin-top: 20px;
  height: auto;
}
</style>
