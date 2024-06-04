<template>
    <el-table :data="T" border stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="话题名称" prop="topic" width="160px"></el-table-column>
          <el-table-column
          label="话题内容"
          prop="title"
          width="400px"
          >
          </el-table-column>
          <el-table-column
          label="咨询价格"
          prop="price"
           width="120px"
          >
          </el-table-column>
          <el-table-column
          label="咨询专家"
          prop="promoter"
          width="120px"
          >
          </el-table-column>
      </el-table>

  </template>
  
  <style scoped></style>
  


  

<script>
import axios from 'axios';
export default {
    data() {
        return {
            T: []
        }
    },
    methods: {
    },
    mounted(){
        axios.get("https://www.fastmock.site/mock/e31676a60c7bc77b9f15db12616b2efc/Topic/topic").then(res => {
            this.T = res.data;
            console.log(this.T);
        })
    }
}

</script>
