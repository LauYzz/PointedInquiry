<template>
    <div>
        <Header></Header>
        <div style="height: 710px;display: flex;align-items: center;flex-direction: column;">
            <div style="margin-top: 5%;color: rgb(10, 107, 181);font-weight: bold;font-size: larger;">
                成为行家
            </div>
            <div style="margin:18px;color:grey;">
                提交您的行家资料，后台管理员将及时审核您的资料，审核通过后即可成为行家帮助更多人！
            </div>
            <el-form label-width="70px">
                <el-form-item label="手机号" style="margin-bottom: 10px;">
                    {{ expertInfo.phone }}
                </el-form-item>
                <el-form-item label="真实姓名" style="margin-bottom: 10px;">
                    <el-input v-model="expertInfo.realName"></el-input>
                </el-form-item>
                <el-form-item label="自我描述" style="margin-bottom: 10px;">
                    <el-input placeholder="用简单的话描述一下自己" type="textarea" :rows="5" v-model="expertInfo.description">
                    </el-input>
                </el-form-item>
                <el-form-item label="身份证号" style="margin-bottom: 10px;">
                    <el-input v-model="expertInfo.id"></el-input>
                </el-form-item>
                <el-form-item label="行家类型" @change="handleTypeChange" style="margin-bottom: 10px;">
                    <el-select v-model="expertInfo.type">
                        <el-option value="心理" label="心理"></el-option>
                        <el-option value="学业" label="学业"></el-option>
                        <el-option value="就业" label="就业"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="具体方向" style="margin-bottom: 10px;">
                    <el-select v-model="expertInfo.job">
                        <el-option v-for="option in jobOptions" :key="option.value" :label="option.label"
                            :value="option.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>

            <el-button @click="submitInformation">提交审核</el-button>
            

        </div>
        <el-dialog :visible.sync="flag" :center="true" :show-close="false" :close-on-click-modal="false"  >
                <div style="display: flex;align-items: center;flex-direction: column;;">
                <div>您已经是行家，无需重复申请</div>
                <el-button type="primary"  style="align-items: center;margin-top: 20px;" @click="handleConfirmEvent">确定</el-button>
                </div>
            </el-dialog>
        <Footer :num="num" style="bottom: 0;"></Footer>

    </div>
</template>

<script setup>
import Header from '../Screen/Header.vue'
import Footer from '../Screen/Footer.vue'
import { reactive, ref,watch } from 'vue'
import { Message } from 'element-ui';
import router from '../../router/index.js';
import axios from 'axios';
const num = ref("third");
const flag = ref(false);
const expertInfo = reactive({
    phone: "",
    realName: "",
    description: "",
    id: "",
    job: "",
    type: "",
})

expertInfo.phone = localStorage.getItem("userId");
let user_id = localStorage.getItem("userId");
const jobOptionsMap = {
    心理: [
        { value: "心理督导师", label: "心理督导师" },
        { value: "心理咨询师", label: "心理咨询师" },
        { value: "心理咨询员", label: "心理咨询员" },
    ],
    学业: [
        { value: "计算机行家", label: "计算机行家" },
        { value: "土木行家", label: "土木行家" },
        { value: "艺术行家", label: "艺术行家" },
        { value: "文学行家", label: "文学行家" },
        { value: "数学行家", label: "数学行家" },
    ],
    就业: [
        { value: "生涯规划师", label: "生涯规划师" },
        { value: "资深职场行家", label: "资深职场行家" },
    ],
};

const jobOptions = ref(jobOptionsMap[expertInfo.type]);

watch(() => expertInfo.type, (newType) => {
  expertInfo.job = ""; // 清空具体方向选项
  jobOptions.value = jobOptionsMap[newType];
});

const handleTypeChange = ()=>{
    expertInfo.job = "";
}

const submitInformation = ()=>{

    let data = new FormData();
    data.append('phone', expertInfo.phone);
    data.append('realName',expertInfo.realName);
    data.append('description',expertInfo.description);
    data.append('id',expertInfo.id);
    data.append('job',expertInfo.job);
    let typeData = expertInfo.type === "心理"? 1: expertInfo.type === "学业"? 2: 3;
    data.append('type',typeData);
    const config = {
        url:'expert/addExpert',
        method:'post',
        data: data
    }
    axios(config).then((res)=>{
        if(res.data.data=== 1){
            Message({
                type: 'success',
                message:'上传成功'
            })
        }
        else{
            Message({
                type: 'error',
                message:'上传失败，请重新上传'
            })
        }
    })
}

const checkIfExpert = ()=>{
    const config = {
        url: 'user/myInfo',
        method: 'post',
        params:{
            userId: user_id
        }
    }
    axios(config).then((res)=>{
        if(res.data.data.isExpert === 1){
            flag.value = true;
        }
    }).catch((err)=>{});
}

checkIfExpert();

const handleConfirmEvent = ()=>{
    flag.value = true;
    router.push('/UserInfoScreen');
}

</script>

<script>
</script>


<style>
</style>