<template>
    <div>
        <Header></Header>
        <div style="height:710px;background-color: #eaedf1;">
            <el-card :body-style="{ padding: '0px' }" v-if="orders[index].state === '进行中'" v-for="(item, index) in orders"
                :key="index">
                <div style="padding: 14px;" :key="index">
                    <div>
                        <span :class="{
                            'state1': orders[index].state === '已评价',
                            'state2': orders[index].state === '进行中',
                            'state3': orders[index].state === '已完成',
                            'state4': orders[index].state === '已取消'
                        }">
                            {{ orders[index].state }}
                        </span>
                        <br>
                    </div>
                    <div class="description">
                        <div class="subdes">
                            <div style="font-size: larger;">{{ orders[index].user.name }}</div>
                            <div class="topic-span">{{ "#" + orders[index].title }}</div>
                            <span style="color: grey; font-size: 13px;">咨询时间：{{ orders[index].appointTime }}</span>
                            <span class="price">{{ orders[index].price }}元/小时</span>
                        </div>
                        <div class="btns">
                            <el-button @click="detailInfo">查看详情</el-button>
                            <el-button @click="cancel(index)">取消订单</el-button>
                            <el-dialog title="取消订单" :visible.sync="CancelVisible" width="95%" :before-close="handleClose">
                                <span>您确认要取消订单吗？</span><br><br><br>
                                <el-button  @click="cancelNow(index)">确定</el-button>
                                <el-button type="primary" @click="thinkMore">我再想想</el-button>
                            </el-dialog>
                           
                        </div>
                    </div>
                </div>
                <el-dialog  title="详情信息" :visible.sync="detailInfoVisible">
                                <el-form>
                                    <el-form-item label="咨询人">
                                        {{orders[index].user.name}}
                                    </el-form-item>
                                    <el-form-item label="联系方式">
                                        {{ orders[index].user.phone }}
                                    </el-form-item>
                                    <el-form-item label="学校">
                                        {{ orders[index].user.school}}
                                    </el-form-item>
                                    <el-form-item label="咨询话题">
                                        {{ orders[index].topic.title}}
                                    </el-form-item>
                                    <el-form-item label="咨询方式">
                                        {{ orders[index].topic.way}}
                                    </el-form-item>
                                    <el-form-item label="话题已被咨询次数">
                                        {{ orders[index].topic.topicTime}}
                                    </el-form-item>
                                    <el-form-item label="话题详情">
                                        {{ orders[index].topic.text }}
                                    </el-form-item>
                                </el-form>
                            </el-dialog>

            </el-card>
            
        </div>
        <Footer num="forth"></Footer>
    </div>
</template>
    
<script>
//个人信息的编辑，涉及到省份和城市接口的调用，先选省份，选好后得到pid再传给city接口才能得到city信息。
import axios from 'axios';
import Footer from '../Screen/Footer.vue';
import Header from '../Screen/Header.vue';

export default {
    name: "Info",
    components: {
        Header,
        Footer,
    },
    data() {
        return {
            orders: [],
            CancelVisible: false,
            detailInfoVisible: false
        };
    },
    mounted() {
        this.userId = localStorage.getItem('userId');
        var config = {
            method: 'get',
            url: '/order/GetOrderByExpertID',
            params: {
                expert_id: this.userId
            }
        }
        axios(config)
            .then((res) => {
                this.orders = res.data.data;
            })
            .catch(function (error) {
                console.log(error);
            });

    },
    methods: {
        handleClose(){
            this.CancelVisible = false;
        },
        detailInfo(){
            this.detailInfoVisible = true;
        },  
        thinkMore(){
            this.CancelVisible = false;
        },
        cancelNow(index) {
            const config = {
                url: '/order/CancleOrder',
                method: 'post',
                params: {
                    orderId: this.orders[index].orderId,
                }
            };

            axios(config).then((res) => {
                if (res.data.status === 100) {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.queryData().then(res => {
                        this.orders = res.data.data;
                    })
                }
                else {
                    this.$message({
                        message: '操作失败',
                        type: 'error'
                    });
                }

            }).catch(error => {
            })

        },
        cancel(index) {

            this.CancelVisible = true;
        },
        async queryData() {
            var config = {
                method: 'get',
                url: '/order/GetOrderByExpertID',
                params: {
                    expert_id: this.userId
                },
            }
            var res = await axios(config)
            return res;
        },
    },
};
</script>
    
<style>
.topic-span {
    width: fit-content;
    padding: 2px 10px;
    margin: 4px;
    border-radius: 100px;
    font-size: 14px;
    cursor: pointer;
    background-color: rgba(0, 128, 92, 0.573);
    color: #fff;
    margin: 10px 0
}

.btns {
    margin-top: 20px;
    text-align: center;

}

.el-button {
    border-radius: 10px;
}

.price {
    font-size: 17px;
    color: rgb(230, 0, 0);
    float: right;
}

.topic {
    padding: 0px;
    margin-top: 60px;
    line-height: 28px;
}

.state1 {
    border-radius: 100px;
    font-size: small;
    color: green;
    float: right;
    font-weight: bold;
    padding: 2px 6px;
    background-color: rgba(0, 128, 0, 0.35);
}

.state2 {
    border-radius: 100px;
    font-size: small;
    color: rgb(17, 80, 174);
    float: right;
    font-weight: bold;
    padding: 2px 6px;
    background-color: rgb(17, 80, 174, 0.35);
}

.state3 {
    border-radius: 100px;
    font-size: small;
    color: orange;
    float: right;
    font-weight: bold;
    padding: 2px 6px;
    background-color: rgba(255, 166, 0, 0.35);
}

.state4{
    border-radius: 100px;
    font-size: small;
    color: grey;
    float: right;
    font-weight: bold;
    padding: 2px 6px;
    background-color: rgb(128, 128, 128, 0.35);
}

.el-form-item{
    margin-bottom: 0;
}
.el-dialog{
    width: 70%;
}
.name {
    color: black;
    font-size: 20px;
}
</style>
    
    
    
    
    