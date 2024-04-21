<template>

    <div class="starScore">

        <link rel="shortcut icon" href="../img/empty_favicon.ico">
        <link rel="shortcut icon" href="../img/empty_favicon.ico">
        <span v-for="(item, index) of star" :key="index">
            <link rel="stylesheet icon" href="//at.alicdn.com/t/c/font_3828548_d5q6n6g65cs.css">
            <i class="iconfont icon-star" :class="{ 'active': starIndex > index, 'half': starIndex === index + 0.5 }">

            </i>

        </span>

        <span class="starScore-key">{{ score }}</span>

    </div>

</template>

<script>

export default {

    name: 'starScore',
    props: { fatherScore: Number },
    data() {

        return {

            score: 0,

            starIndex: -1,

            star: [0, 1, 2, 3, 4] // 分数

        };

    },

    methods: {

        changeScore(index) {

            let offsetX = window.event.offsetX;

            let i = index + 1;

            if (offsetX < 8 * (2 * i - 1)) {

                this.starIndex = index + 0.5;

            } else if (offsetX < 16 * i) {

                this.starIndex = index + 1;

            }

            this.score = Number((this.starIndex) * 2).toFixed(1);
            //首先必须记录分数

        }

    },
    mounted() {
        this.score = this.fatherScore.toFixed(1);
        this.starIndex = this.score / 2;
        if (this.starIndex >= Math.floor(this.score) / 2) {
            this.starIndex = Math.floor(this.score) / 2;
        }
        console.log(this.score);
    }

};

</script>

<style lang="scss" scoped>
.starScore {

    .icon-star {
        font-size: 24px;
        cursor: pointer;

        color: #E4E4E4;

        &.half {

            background: linear-gradient(to right, #FFA940 50%, #E4E4E4 0);

            background-clip: text;

            -webkit-background-clip: text;

            -webkit-text-fill-color: transparent;

        }

        &.active {

            color: #FFA940;

        }

    }

    .starScore-key {

        color: #FFA940;

        padding-left: 5px;

    }

}
</style>