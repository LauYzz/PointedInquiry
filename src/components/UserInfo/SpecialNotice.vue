<template>
  <div>
    <Header />
    <el-container>
      <el-main style="height:710px;">
        <span>特别声明</span>
      </el-main>
    </el-container>
    <el-container>
      <Footer :num="num" />
    </el-container>
  </div>
</template>


<script>
import Footer from '../Screen/Footer.vue';
import getQualification from '../getQualification/getQualification.vue';
import Header from '../Screen/Header.vue';
export default {
  data() {
    return {
      num: "third",
      dialogVisible: false,
    };
  },
  components: {
    Footer,
    Header,
    getQualification
  },
  methods: {
    linkToUserInfo() {
      this.$router.push('/UserInfo');
    },
    linkToGetQualification() {
      this.dialogVisible = true;
    },
    linkToMyCollection() {
      this.$router.push('/MyCollection');
    },
    handleClose(done) {
      done();
    }
  }
}
</script>

<style scoped>
.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}
</style>