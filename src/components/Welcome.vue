<template>
  <div class="login">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0" />
    <meta name="theme-color" content="#183C5E" />
    <link rel="manifest" href="manifest.json" />
    <link rel="icon" href="logo.png" />
    <div id="w1" class="act-photo" style="position: relative; height: 300rpx; width: 100%; margin: auto">
      <div id="w2" class="vert-center text-center" style="height: 150rpx; margin-top: 700px">
        <el-button class="cu-btn bg-black text-white shadow-blur text-xs"
          style="opacity: 0.7; width: 150rpx; margin-right: 80px; font-size: 30px" @click="linkToLogin()">登录</el-button>

        <el-button class="margin-left cu-btn bg-black text-white shadow-blur text-xs"
          style="opacity: 0.7; width: 150rpx; font-size: 30px" @click="linkToRegister()">注册</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.text-center {
  text-align: center;
}

.vert-center {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  margin: auto;
  right: 0;
}

.el-button--login {
  background: white;
  border-color: white;
  color: black;
  font-size: 24px;
  margin-right: 20px;
}

.el-button--register {
  background: white;
  border-color: white;
  color: black;
  font-size: 24px;
}

.login {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../img/new_welcome.jpg");
  overflow: auto;
  position: relative;
}
</style>

<script>
import axios from "axios";

export default {
  data() {
    return {
      T: [],
    };
  },
  methods: {
    linkToLogin() {
      this.$router.push("/login");
    },
    linkToRegister() {
      this.$router.push("/register");
    },
  },
  mounted() {
    axios
      .get("https://www.fastmock.site/mock/e31676a60c7bc77b9f15db12616b2efc/Topic/topic")
      .then((res) => {
        this.T = res.data;
        console.log(this.T);
      });
  },
};

/*export default defineComponent({
data() {
  return {

  };
},
components: {
  Register,
  Login,
},
methods: {
  doLogin() { this.$router.push("/Home");},
  doRegister(){}
}
})*/
</script>
