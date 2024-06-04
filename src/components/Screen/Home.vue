<template>
<el-container>
        <Header/>
        <el-container>
            <el-main style="height:710px;"><ExpertScreen /></el-main>
        </el-container>
        <Footer :num="num"></Footer>
  </el-container>
</template>

<style lang="less" scoped>
.el-card__body,
.el-main {
  padding: 0px;
}


.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
.el-header{
     background-color:darkslategrey;
     display: flex; 
     justify-content: space-between;
     padding-left: 12px;
     align-items: center;
     color: #fff;
     font-size: 24px;
     > div {//内嵌的div样式
         display: flex;
         align-items: center;
         span {
             margin-left: 25px;
         }
     }
 }
 .element.style {
    height: 100px;
 }

.toggle-button {
  background-color: #4a5064; //背景色(浅灰)
  font-size: 10px; //字体大小10像素
  line-height: 24px; //行高24像素
  color: #fff; //字体颜色白色
  text-align: center; //字体居中
  letter-spacing: 0.2em; //字体之间的距离
  cursor: pointer; //鼠标的形状（手形）
}
.submenu {
  display: inline-flex;
  text-align: left;
  margin-left: 15px;
}

.el-aside {
  background-color: #333744;
}
.el-main {
  background-color: #eaedf1;
}

.home-container {
  height: 100vh;
}
</style>

<script>
import ExpertScreen from "./ExpertScreen.vue";
import Footer from "./Footer.vue";
import Header from "./Header.vue";
import Topic1 from "../Topic1.vue";
import userInfo from "../userInfo.vue";
export default {

  data() {
    return {
      isCollapse: false,
      num: "first",
      keyword:"",
    };
  },
  components: {
    userInfo,
    Topic1,
    ExpertScreen,
    Footer,
    Header,
    ExpertScreen
},
  methods: {
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/login");
    },
    userInfoShow() {
      this.$router.push("/userInfo");
    },
    methods:{
        logout(){
            window.sessionStorage.clear();
            this.$router.push('/login');
        },
        userInfoShow() {
            this.$router.push('/userInfo'); 
        }
    },
    mounted() {
        this.userId = localStorage.getItem('userId');
        console.log("Home页面的userId是"+this.userId);
    },
  },
};
</script>
