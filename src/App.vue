<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import Expert from './components/expert.vue';
import Header from './components/Screen/Header.vue';
import HelloWorld from './components/HelloWorld.vue';
import Login from './components/Login.vue';
import Topic1 from './components/Topic1.vue';
export default {
  name: 'App',
  components: {
    HelloWorld,
    Login,
    Header,
    Topic1,
    Expert,
  }
}
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 844px;
  position: relative;
}
</style>
