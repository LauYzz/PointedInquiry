<template>
  <el-container>
    <Header />
    <el-container>
      <el-main style="height:710px;">
        <el-tabs v-model="activeName" type="card" :stretch="true" @tab-click="handleClick">
          <el-tab-pane label="全部" name="first">
            <AllOrders v-if="orderStatus.orderStatus1" />
          </el-tab-pane>
          <el-tab-pane label="进行中" name="second">
            <OrdersInProgress v-if="orderStatus.orderStatus2" />
          </el-tab-pane>
          <el-tab-pane label="已完成" name="third">
            <OrdersTobeEvaluated v-if="orderStatus.orderStatus3" />
          </el-tab-pane>
          <el-tab-pane label="已评价" name="forth">
            <EvaluatedOrders v-if="orderStatus.orderStatus4" />
          </el-tab-pane>
          <el-tab-pane label="已取消" name="fifth">
            <CancledOrders v-if="orderStatus.orderStatus5" />
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
    <Footer :num="num"></Footer>
  </el-container>
</template>
<script>
import Footer from './Footer.vue';
import Header from './Header.vue';
import AllOrders from '../Orders/AllOrders.vue';
import EvaluatedOrders from '../Orders/EvaluatedOrders.vue';
import OrderMenu from '../Orders/OrderMenu.vue';
import OrdersInProgress from '../Orders/OrdersInProgress.vue';
import OrdersTobeEvaluated from '../Orders/OrdersTobeEvaluated.vue';
import CancledOrders from '../Orders/CancledOrders.vue';
export default {
  data() {
    return {
      activeName: 'first',
      num: "second",
      orderStatus: {
        orderStatus1: true,
        orderStatus2: false,
        orderStatus3: false,
        orderStatus4: false,
        orderStatus5: false,
      }
    };
  },
  components: {
    AllOrders,
    OrdersInProgress,
    OrdersTobeEvaluated,
    EvaluatedOrders,
    OrderMenu,
    Footer,
    CancledOrders,
    Header,
  },
  methods: {
    handleClick(tab) {
      switch (tab.name) {
        case 'first':
          this.updateStatus('orderStatus1');
          break;
        case 'second':
          this.updateStatus('orderStatus2');
          break;
        case 'third':
          this.updateStatus('orderStatus3');
          break;
        case 'forth':
          this.updateStatus('orderStatus4');
          break;
        case 'fifth':
          this.updateStatus('orderStatus5');
          break;
      }
    },
    updateStatus(name) {
      for (let key in this.orderStatus) {
        if (key == name) {
          this.orderStatus[key] = true;
        } else {
          this.orderStatus[key] = false;
        }
      }
    }
  }
};
</script>


<style scoped lang="less">
.bg {
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  background-position: center center;
  background-image: url("../../img/galaxy.jpg");
  overflow: auto;
  position: relative;
}

.el-container {
  display: block;
  flex-direction: row;
  flex: 1;
  flex-basis: auto;
  box-sizing: border-box;
  min-width: 0;
}

.el-card__body,
.el-main {
  padding: 0px;
}


.element.style {
  height: 100px;
}

.toggle-button {
  background-color: #4A5064; //背景色(浅灰)
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

.topic-span {
  width: 100%;
  padding: 8px;
  margin: 4px;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  background-color: teal;
  color: #fff;
}

.el-aside {
  background-color: #333744;
}

.el-main {
  background-color: #eaedf1;
}

/deep/.home-container {
  height: 100vh;
  width: 70%;
}
</style>