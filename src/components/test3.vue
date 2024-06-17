<template>
  <div>
    <link rel="stylesheet icon" href="//at.alicdn.com/t/c/font_3828548_f8oepiumx94.css">
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from './Screen/Footer.vue';
export default {
  data() {
    return {
      name: ["111", "222", "333"]
    }
  },
  components: {
    Footer
  }
}
</script>
<style lang="less" scoped></style>