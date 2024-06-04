<template>
  <div>
  
      <el-form :model="form">
  <el-form-item label="真实姓名" :label-width="formLabelWidth">
    <el-input v-model="form.name" autocomplete="off"></el-input>
  </el-form-item>
  <el-form-item label="身份证号" :label-width="formLabelWidth">
    <el-input v-model="form.ID" autocomplete="off"></el-input>
  </el-form-item>
  <el-form-item label="行业领域" :label-width="formLabelWidth">
    <el-radio-group v-model="radio">
    <el-radio :label="0">心理</el-radio>
    <el-radio :label="1">学业</el-radio>
    <el-radio :label="2">就业</el-radio>
  </el-radio-group>
  </el-form-item>
  <el-form-item label="行家头衔" :label-width="formLabelWidth">
    <el-input v-model="form.title" autocomplete="off"></el-input>
  </el-form-item>
  <el-form-item label="个人介绍" :label-width="formLabelWidth">
    <el-input type="textarea" v-model="form.text" autocomplete="off"></el-input>
  </el-form-item>
</el-form>

<el-upload style="text-align:center;" class="avatar-uploader"
      ref="otherLicense"
      action
      :auto-upload="false"
      :on-preview="handlePicPreview"
      :on-remove="handleRemove"
      :file-list="fileList"
      list-type="picture-card"
      multiple>
      <i class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" class="avatar">
    </el-dialog>
    <br><br>
    <div style="text-align:center;" slot="footer" class="dialog-footer">
  <el-button type="primary" @click="confirmInfo">确 定</el-button>

</div>
  </div>
</template>

<script>
import axios from 'axios';

import Footer from '../Screen/Footer.vue';
import Header from '../Screen/Header.vue';
export default {
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      dialogTableVisible: false,
      dialogFormVisible: false,
      SuccessVisible:false,
      radio: 0,
      form: {
        name: '',
        ID:'',
        title: '',
        text:'',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
        formLabelWidth: '80px',
        fileList: [],
        dialogVisible: false,
      dialogImageUrl: '',
      num: "fourth",
      userId:"",
    };
  },
  methods: {
      handlePicPreview(file){
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
handleRemove(file, fileList) {
      this.fileList.map((item,index)=>{
        if(item.uid==file.uid){
          this.fileList.splice(index,1)
        }
      })
    },
beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },

    async queryData() {
      var data1 = new FormData();
      data1.append("userId",this.userId);
      data1.append("name",this.form.name);
      data1.append("ID", this.form.ID);
      console.log(this.userId);
      console.log(this.form.name);
      console.log(this.form.ID);

      var config = {
      method: 'post',
        url: '/expert/addExpert',
      data:data1
    }
      var res = await axios(config)
      console.log(res.data)
      return res;
    },


    confirmInfo() {
      if (this.form.name != '' && this.form.ID != '' && this.form.text != ''&&this.form.title!='')
      {
        this.$message.success('申请成功')
      }
      else {
        this.$message('请填写完整信息');
      }
    }
     /* var data = new FormData();
      data.append("phone",this.userId);
      data.append("qid",this.radio);
      data.append("qname", this.form.title); 
      data.append("content", this.form.text);*/




/*
     var config = {
            method: 'post',
            url: '/qualification/uploadInfo',
            data: data
            }
            var that = this;
            axios(config)
                .then(function (response) {
                  console.log(response.data);
                  that.ConfirmVisible = true;
         })
         .catch(function (error) {
           console.log(error);
         });*/
},

otherSectionFile(file){
      const typeArr = ['image/png', 'image/gif', 'image/jpeg', 'image/jpg'];
        const isJPG = typeArr.indexOf(file.raw.type) !== -1;
        const isLt3M = file.size / 1024 / 1024 < 3;
        if (!isJPG) {
          this.$message.error('只能是图片!');
          this.$refs.upload.clearFiles();
          this.otherFiles = null;
          return;
        }
        if (!isLt3M) {
          this.$message.error('上传图片大小不能超过 3MB!');
          this.$refs.upload.clearFiles();
          this.otherFiles = null;
          return;
        }
        this.otherFiles = file.raw;
        // FormData 对象
        var formData = new FormData();
        // 文件对象
  formData.append('files', this.otherFiles);
  console.log("图片"+this.otherFiles);
  console.log("选择领域"+this.radio);
  console.log(this.userId);
  formData.append('qid', this.radio);
  formData.append('userId', this.userId);
   var config = {
        method: 'post',
        url: '/qualification/uploadPhoto',
        data: formData,
  }
      /*axios(config).then(res=>{
        this.fileList.push(file)
        if(res.data.flag == 'S'){
            this.otherPhotoObj = res.data.data.objectId
            this.otherPhoto = res.data.data.url
            this.otherLicense.push({
              certificationName: this.form.certificationName,
              certificationObj: this.otherPhotoObj,
              certificationUrl: this.otherPhoto
            })
        } else {
          ;
        }
      })*/
  },
  mounted() {
    this.userId = localStorage.getItem('userId');
    console.log("GetQualification页面的userId是"+this.userId);
  }

};
</script>

<style scoped lang="less">
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