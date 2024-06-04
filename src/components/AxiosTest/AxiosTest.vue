<template>
    
</template>

<script>
/* 这个组件测试所有后端的接口通讯状态 */
import axios from 'axios';
export default {
    data() {
        return{}
    },
    component: {
        
    },
    methods: {
        /*测试给后端用户id，后端返回是否为行家*/
        isExpert() {
            var config = {
            method: 'post',
      url: 'http://localhost:8080/user/login',
      data: data
    };

    var that = this;
    axios.get("https://www.fastmock.site/mock/edc8f6926e9ba279a9e6a85407dd71aa/PointedInquiry/AllExpert").then(res => {
          this.Experts = res.data;
      var length = this.Experts.length;
        })
    axios(config)
      .then(function (response) {
        that.accept = response;
        console.log(JSON.stringify(response.data));
        if (response.data.status == "1002")
        {
          alert("用户名或密码输入错误");
        }
        if (response.data.status == "100")
        {
          that.linkToHome();
        }
      })
      .catch(function (error) {
        console.log(error);
      });
        },
    
        /*测试给后端行家id，后端返回行家具体信息 */
        /*测试给后端行家id，后端返回下单所需话题信息*/
        /*测试用户信息编辑*/
        /*测试给后端用户id，后端返回所有订单信息*/
        /*测试给后端用户id，后端返回所有收藏信息*/
        /*测试给后端用户id，话题id，后端创建新订单*/
        /*测试给后端评论内容，话题id，用户id，后端生成新评论*/
        /*测试给后端投诉内容，行家id，用户id，后端生成新投诉*/
        /*测试给后端行家id，话题信息，时间，价格，后端生成新的话题申请*/
        /*测试给后端title，description和photo，后端生成新的行家身份申请*/
    }
}
</script>
