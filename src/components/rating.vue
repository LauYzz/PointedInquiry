<template>
    <div class="Rating-gray">
        <i v-for="(item,index) in itemClasses" :key="index" class="fa" :class="item"></i>
    </div>
</template>

<script>
    const LENGTH = 5;

    const CLS_ON = "fa-star";
    const CLS_HALF = "fa-star-half-empty";
    const CLS_OF = "fa-star-0";

    export default {
        name:"Rating",
        props:{
            rating:Number
        },
        computed:{
            itemClasses(){
                let result = [];
                // 如 4.8 对分数进行处理, 向下取0.5的倍数
                let score = Math.floor(this.rating*2)/2;
                // 用来判断哪种星星的标准
                let integer = Math.floor(score);
                let hasDecimal = score % 1 !== 0;
                for (let i = 0; i < integer; i++) {
                    result.push(CLS_ON);
                };
                if(hasDecimal) {
                    result.push(CLS_HALF);
                };
                while(result.length < LENGTH) {
                    result.push(CLS_OF);
                };
                return result;
            }
        }
    }
</script>

<style scoped>
    .Rating-gray {
        margin-right: 1.066667vw;
        color: #ffbe00;
        display: inline-block;
    }
</style>