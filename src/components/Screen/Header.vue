<template>
  <el-header>
    <div>
      <img src="../../img/logo.png" alt="" height="40px" />
      <span>对询</span>
    </div>
  </el-header>
</template>

<script>
export default {
  data() {
    return {
      input: "",
    };
  },
  methods: {
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/login");
    },
  },
};
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}

.el-header {
  background-color: darkslategrey;
  display: flex;
  justify-content: space-between;
  padding-left: 12px;
  align-items: center;
  color: #fff;
  font-size: 24px;

  >div {
    //内嵌的div样式
    display: flex;
    align-items: center;

    span {
      margin-left: 25px;
    }
  }
}

.el-aside {
  background-color: #333744;
}

.el-main {
  background-color: #eaedf1;
}
</style>
