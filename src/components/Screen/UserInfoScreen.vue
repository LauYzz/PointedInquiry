<template>
  <el-container>
        <Header />
          <el-container>
            <el-main style="height:710px;">
            <br>
            <link rel="stylesheet icon" href="//at.alicdn.com/t/c/font_3828548_2scs4mmv5kn.css">
            <span @click="linkToUserInfo()" class="sub"><i class="iconfont icon-person"></i>我的信息 <i class="iconfont icon-keyboard_arrow_right"></i></span>
            <span @click="linkToMyCollection()" class="sub"><i class="iconfont icon-check"></i>我的收藏 <i class="iconfont icon-keyboard_arrow_right"></i></span>
            <span @click="linkToExpertApplicationPage()" class="sub"><i class="iconfont icon-person_add"></i>成为行家 <i class="iconfont icon-keyboard_arrow_right"></i></span>
            <el-dialog
            title="提交信息"
            :visible.sync="dialogVisible"
            width="95%"
            :before-close="handleClose">
            <getQualification />
            </el-dialog>
            <span @click="MyExpertInfo" class="sub"><i class="iconfont icon-contacts"></i>我的行家信息 <i class="iconfont icon-keyboard_arrow_right"></i></span>
            <span class="sub" @click="linkToAboutUs()"><i class="iconfont icon-more_horiz"></i>关于我们 <i class="iconfont icon-keyboard_arrow_right"></i></span>
            <el-dialog
            :visible.sync="NotExpertVisible"
            width="95%"
            :before-close="handleClose">
            <div style="text-align:center">
              <span style="font-size:18px;">您还不是行家！</span>
              <br><br><br><br>
            <el-button  primary @click="ToConfirm()">去认证</el-button>
          </div>
            </el-dialog>

            <el-dialog
            :visible.sync="HaveExpertVisible"
            width="95%"
            :before-close="handleClose">
            <div style="text-align:center">
              <span style="font-size:18px;">您已经是行家了！</span>
          </div>
            </el-dialog>
            <span class="sub" @click="linkToSpecialNotice()"><i class="iconfont icon-info"></i>特别声明 <i class="iconfont icon-keyboard_arrow_right"></i></span>
            </el-main>
          </el-container>
        <Footer :num="num"></Footer>
    </el-container>
</template>

<script>
import axios from 'axios';
import Footer from './Footer.vue';
import getQualification from '../getQualification/getQualification.vue';
import Header from './Header.vue';
import { Message } from 'element-ui';
import "element-ui/lib/theme-chalk/index.css";
import Vue from 'vue';
Vue.prototype.$message = Message;
export default {
    data() {
    return {
      num: "third",
      dialogVisible: false,
      NotExpertVisible: false,
      userId: "",
      isExpert: "",
      HaveExpertVisible: false,
        };
    },
    components: {
        Footer,
      Header,
      getQualification
  },
  methods: {
    linkToExpertApplicationPage(){
      this.$router.push('/expertApplication')
    },
    ToConfirm() {
      this.NotExpertVisible = false;
      this.dialogVisible = true;
    },
    linkToUserInfo() {
      this.$router.push('/UserInfo');
      },
      linkToGetQualification() {
        this.dialogVisible = true;
    },
    linkToMyCollection() {
      this.$router.push('/MyCollection');
    },
    linkToAboutUs() {
      this.$router.push('/AboutUs');
    },
    linkToSpecialNotice() {
      this.$router.push('/SpecialNotice');
    },
      handleClose(done) {
            done();
    },
    MyExpertInfo() {
      this.$router.push('/MyExpertInfo');
      },
    personalExpert() {
      var data = new FormData();
      data.append("userId", this.userId);
      
        var config = {
            method: 'post',
            url: '/user/isExpert',
            data: data
            }
            var that = this;
            axios(config)
                .then(function (response) {
                  var s = response.data.status;
                  if (s == '100')
                  {
                    that.HaveExpertVisible = true;
                    that.isExpert = false;
                    console.log("是不是行家");
                    console.log(that.HaveExpertVisible);
                    console.log("是行家");
                  }
                  else if (s == '999')
                  {
                    that.isExpert = false;
                    console.log("不是行家");
                    that.dialogVisible = true;
                  }
         })
         .catch(function (error) {
           console.log(error);
         });
      }
  },
  mounted() {
    this.userId = localStorage.getItem('userId');
    console.log("Me页面的userId是"+this.userId);
    }
}
</script>

<style scoped lang="less">
  
  .sub{
    padding-left:12px;
    margin-top:15px;
      background-color:darkslategrey;
      white-space: nowrap; /*强制span不换行*/
      display: inline-block; /*将span当做块级元素对待*/
      width: 97%;  /*限制宽度*/
      overflow: hidden; /*超出宽度部分隐藏*/
      text-overflow: ellipsis; /*超出部分以点号代替*/
      line-height: 60px; /*数字与之前的文字对齐*/
      height:60px;
      font-size:24px;
      color:lavender;
  }

  .iconfont{
    margin-right:15px;
    margin-left:4px;
    font-size:28px;
  }
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
.el-card__body, .el-main {
   padding: 0px; 
}


.element.style {
  height: 100px;
}
.toggle-button{
background-color: #4A5064;//背景色(浅灰)
font-size: 10px;//字体大小10像素
line-height: 24px;//行高24像素
color: #fff;//字体颜色白色
text-align: center;//字体居中
letter-spacing: 0.2em;//字体之间的距离
cursor: pointer;//鼠标的形状（手形）
}
.submenu{
   display:inline-flex;
   text-align:left;
   margin-left:15px;
}

.el-aside{
   background-color: #333744;
}
.el-main{
   background-color: #eaedf1;
}

/deep/.home-container{
  height: 100vh;
  width:70%;
}
</style>