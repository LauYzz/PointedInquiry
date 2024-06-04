<template>
    <div>{{expert_id}}
    </div>
    
</template>

<script>

export default{
    data() {
        return {
            expert_id:"20"
        }
    },
    methods: {},
    mounted() {
        alert("mounted");
        this.expert_id = this.$route.params.id;
        console.log("专家ID:"+this.expert_id);
    },
}
</script>
