<template>
    <div>
      <el-form>
    <el-form-item label="话题名称" :label-width="formLabelWidth">
      <el-input v-model="form.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="话题金额" :label-width="formLabelWidth">
      <el-input v-model="form.price" autocomplete="off"></el-input>
    </el-form-item>

<el-form-item label="约聊方式" :label-width="formLabelWidth">
    <el-radio-group v-model="radio1">
    <el-radio :label="0">线上</el-radio>
    <el-radio :label="1">线下</el-radio>
    <el-radio :label="2">皆可</el-radio>
    </el-radio-group>
</el-form-item>

<el-form-item label="话题类型" :label-width="formLabelWidth">
    <el-radio-group v-model="radio2">
    <el-radio :label="0">心理</el-radio>
    <el-radio :label="1">学业</el-radio>
    <el-radio :label="2">就业</el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="可选时间" :label-width="formLabelWidth">
      <el-input v-model="form.time" autocomplete="off"></el-input>

  </el-form-item>
    
  </el-form>
  <div slot="footer" class="btns">
    <el-button radio2="primary" @click="confirmTopic()">确 定</el-button>
  </div>
    </div>
</template>

<script>
import axios from 'axios';
import { Message } from 'element-ui';
import "element-ui/lib/theme-chalk/index.css";
import Vue from 'vue';
Vue.prototype.$message = Message;
  export default {
    data() {
      return {
        gridData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
        dialogTableVisible: false,
        dialogFormVisible: false,
        radio1: "1",
        radio2:"1",
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          radio2: [],
          resource: '',
          desc: '',
          price: '',
          time:'',
        },
          formLabelWidth: '70px',
          fileList: [],
          dialogVisible: false,
        dialogImageUrl: '',
        userId:'',
      };
    },
    methods: {
      confirmTopic() {
          
        var w='';
        if (this.radio1 == '0')
        {
          w='线上'
        }
        else if(this.radio1 == '1')
        {
          w='线下'
        }
        else if(this.radio1=='2')
        {
          w='皆可'
        }

        var t=''
        if (this.radio2 == '0')
        {
          t='心理'
        }
        else if(this.radio2 == '1')
        {
          t='学业'
        }
        else if(this.radio2=='2')
        {
          t='就业'
        }
        var data = new FormData();
        data.append("expertId",this.userId);
        data.append("title",this.form.name);
        data.append("price", this.form.price);
        data.append("way", w);
        data.append("type", t);
        data.append("appointTime", this.form.time);
        console.log(this.userId);
        console.log(this.form.name);
        console.log(this.form.price);
        console.log(w);
        console.log(t);
        console.log(this.form.time);
        var config = {
          method: 'post',
          url: '/topic/addTopic',
          data: data
        }
        var that = this;
        axios(config)
          .then(function (response) {
            that.$message.success("话题创建成功！");
          console.log(JSON.stringify(response.data));
        })
          .catch(function (error) {
            that.$message("话题创建失败！");
          console.log(error);
        });
        }
  },
  mounted() {
    this.userId = localStorage.getItem('userId');
    console.log("Home页面的userId是" + this.userId);
  }

  };
</script>



<style scoped lang="less">

.btns{
  text-align:center;
}
.avatar-uploader /deep/ .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    background-color: #fff;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 180px;
    height: 180px;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
.avatar-uploader /deep/ .el-upload-list__item {
    overflow: hidden;
    background-color: #fff;
    border: 1px solid #c0ccda;
    border-radius: 6px;
    box-sizing: border-box;
    width: 180px;
    height: 180px;
    margin: 0 8px 8px 0;
    display: inline-block;
}
</style>