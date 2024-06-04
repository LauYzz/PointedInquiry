<template>
  <div>
    <Header></Header>
    <div style="height:710px;overflow: scroll;" >
        <el-card>
          <el-descriptions class="margin-top" title="我的行家信息" :column="1" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-phone"></i>
                ID
              </template>
              {{ user.expertId }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-custom"></i>
                真实姓名
              </template>
              {{ user.realName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                描述
              </template>
              {{ user.description }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-data-board"></i>
                行家分类
              </template>
              {{ user.type===1?"心理":user.type ===2?"学业":"就业" }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-data-board"></i>
                职位
              </template>
              {{ user.job }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-thumb"></i>
                用户评分
              </template>
              {{ user.rating }}
            </el-descriptions-item>


          </el-descriptions>
        </el-card>

        <el-button @click="addNewTopic" type="primary" class="el-icon-circle-plus-outline" style="margin: 10px;"> 新建话题</el-button>

        <el-card v-for="(item,index) in user.topics" :key="index" style="margin-bottom: 30px;" v-if="item.state ==='已审核'">
          <el-descriptions class="margin-top" :title="'话题' + (index + 1)"  :column="1" border>
            <el-descriptions-item>
              <template slot="label">
                <i ></i>
                话题名
              </template>
              {{ item.title }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i ></i>
                话题描述
              </template>
              {{ item.text }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i ></i>
                收费标准
              </template>
              {{ item.price }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i ></i>
                咨询方式
              </template>
              {{ item.way }}
            </el-descriptions-item>
          </el-descriptions>
        </el-card>

        <el-dialog :visible.sync="CreateTopicVisible">
          <el-form>
            <el-form-item label="话题名称">
              <el-input v-model="topicTitle"></el-input>
            </el-form-item>
            <el-form-item label="描述">
              <el-input type="textarea" :row="3" v-model="topicText"></el-input>
            </el-form-item>
            <el-form-item label="价格">
              <el-input v-model="topicPrice"></el-input>
            </el-form-item>
            <el-form-item label="咨询方式">
              <el-select v-model="topicWay">
                <el-option label="线上" value="线上"></el-option>
                <el-option label="线下" value="线下"></el-option>
              </el-select>
            </el-form-item>
            <div style="display:flex;justify-content: center;margin-top: 20px;">
            <el-button type="primary" @click="submitTopic">提交</el-button>
            <el-button @click="CreateTopicVisible = false">取消</el-button>
            </div>
          </el-form>
        </el-dialog>
      </div>
      <Footer :num="num" />
  </div>
</template>
  
<script>
//个人信息的编辑，涉及到省份和城市接口的调用，先选省份，选好后得到pid再传给city接口才能得到city信息。
import axios from 'axios';
import Footer from './Screen/Footer.vue';
import Header from './Screen/Header.vue';
import CreateTopics from './Topics/CreateTopics.vue';
import userInfoEdit from './userInfoEdit.vue';
export default {
  name: "Info",
  components: {
    userInfoEdit,
    Header,
    Footer,
    CreateTopics,
  },
  data() {
    return {
      ID: "",
      title: "",
      userId: "",
      userInfo: "",
      num: "third",
      CreateTopicVisible: false,
      user:{},

      topicTitle:"",
      topicText:"",
      topicPrice: "",
      topicWay:"",

    };
  },
  mounted() {
    let expert_id = localStorage.getItem('userId');
    const config = {
      url:'expert/getOne',
      method:'get',
      params:{
        expertId: expert_id
      }
    }
    axios(config).then((res)=>{
      this.user = res.data.data;
    })
  },
  methods: {
    submitTopic(){
      let id = localStorage.getItem('userId');
      const config ={
        url:'/topic/addTopic',
        method:'post',
        params:{
          expertId: id,
          title: this.topicTitle,
          text: this.topicText,
          price: this.topicPrice,
          way: this.topicWay
        }
      }
      axios(config).then((res)=>{
        if (res.data.status === 100) {
          this.$message({
            message: '提交成功，等待管理员审核！',
            type: 'success'
          });
        }
        else {
          this.$message({
            message: '提交失败',
            type: 'error'
          });
        }
      })
      this.CreateTopicVisible = false;
    },
    addNewTopic(){
      this.CreateTopicVisible = true;
    },
    edit() {

      this.$refs.userInfoEdit.init();
    },
    handleClose(done) {
      done();
    },
    linkToMyExpertTopic() {
      this.$router.push('/MyExpertTopic');
    },
    linkToMyExpertOrder() {
      this.$router.push('/MyExpertOrder');
    },
  },
};
</script>
  
<style scoped lang="less">
.img {
  width: 80px;
  height: 80px;
}

.btns {
  text-align: center;
}

/deep/.el-card {
  border-radius: 4px;
  border: 1px solid #EBEEF5;
  background-color: #FFF;
  overflow: hidden;
  color: #303133;
  transition: .3s;
  width: 100%;
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
  
  
  
  
  