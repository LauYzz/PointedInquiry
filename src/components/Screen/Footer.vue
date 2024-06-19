<template>
  <div class="icons">
    <link
      rel="stylesheet icon"
      href="//at.alicdn.com/t/c/font_3828548_qlyozufzbpd.css"
    />

    <el-tabs v-model="activeName" type="card" :stretch="true">
      <el-tab-pane label="行家" name="first"
        ><span @click="linkToAllExpert()" slot="label"
          ><i class="iconfont icon-icon-test4"></i>&nbsp;行家</span
        ></el-tab-pane
      >
      <el-tab-pane label="消息" name="second"
        ><span @click="linkToOrders()" slot="label"
          ><i class="iconfont icon-icon-test1"></i>&nbsp;订单</span
        ></el-tab-pane
      >
      <el-tab-pane label="接单" name="forth"
        ><span
          @click="linkToHandleOrders()"
          slot="label"
          class="el-icon-suitcase"
          >&nbsp;接单</span
        ></el-tab-pane
      >
      <el-tab-pane label="我的" name="third"
        ><span @click="linkToUser()" slot="label"
          ><i class="iconfont icon-icon-test3"></i>&nbsp;我的</span
        ></el-tab-pane
      >
    </el-tabs>
  </div>
</template>

<style scoped lang="less">
.icons {
  background-color: darkslategray;
  border: 1px;
  padding: 8px;
  border-radius: 2px;
  justify-content: center;
  color: white;
}
/deep/.el-tabs__item.is-active {
  color: lightseagreen;
}
/deep/.el-tabs__item:hover {
  color: lightseagreen;
  cursor: pointer;
}

/deep/.el-tabs__item {
  padding: 0 20px;
  height: 40px;
  box-sizing: border-box;
  line-height: 40px;
  display: inline-block;
  list-style: none;
  font-size: 14px;
  font-weight: 500;
  color: white;
  position: relative;
}

.com {
  display: inline-block;
}
</style>

<script>
export default {
  props: {
    num: { type: String, default: "first" },
    test: { type: Number, default: 2 },
  },
  data() {
    return {
      activeName: "first",
    };
  },
  methods: {
    linkToAllExpert() {
      this.$router.push({
        path: "/Home",
      });
      this.activeName = this.num;
    },
    linkToUser() {
      this.$router.push({
        path: "/UserInfoScreen",
      });
      this.activeName = this.num;
    },
    linkToOrders() {
      this.$router.push({
        path: "/OrdersScreen",
      });
      this.activeName = this.num;
    },
    linkToHandleOrders() {
      this.$router.push({
        path: "expertOrder",
      });
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
  },
  mounted() {
    this.activeName = this.num;
  },
};
</script>
