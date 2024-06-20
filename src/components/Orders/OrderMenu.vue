<template>
  <div>
    <div>
      <img src="../../img/logo.png" alt="" height="40px" />
      <span>对询</span>
    </div>
  </div>
</template>


<script>
import AllOrders from './AllOrders.vue';
import EvaluatedOrders from './EvaluatedOrders.vue';
import OrdersInProgress from './OrdersInProgress.vue';
import OrdersTobeEvaluated from './OrdersTobeEvaluated.vue';
export default {
  data() {
    return {
      activeName: 'first'
    };
  },
  components: {
    AllOrders,
    OrdersInProgress,
    OrdersTobeEvaluated,
    EvaluatedOrders,
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
};
</script>


<style scoped lang="less">
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
</style>