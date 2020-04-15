<template>
  <div id="app">
    <div id="nav">
      <router-link to="/" v-has="'selection'">Home</router-link>
      |
      <router-link to="/about">About</router-link>
      |
      <Button @click="login" class="input" type="primary">login</Button>
    </div>
    <router-view/>
    <loading/>
  </div>
</template>
<script>
  import loading from '@/components/loading.vue'

  export default {
    name: 'index',
    components: {loading},
    methods: {
      login() {
        debugger
        this.$httpApi.test({}).then(function (resp) {
          console.log(resp);
        })
      }
    },
    data() {
      return {
        btnContext: ['selection', 'Action']
      }
    },
    watch: {
      $route: {
        immediate: true,//开启首次监听
        handler: function (val) {
          //调用后端方法
          sessionStorage.setItem("btnContext", this.btnContext);
        },
        //深度监听
        deep: true,
      }
    }
  }
</script>
<style lang="less">
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #nav {
    padding: 30px;

    a {
      font-weight: bold;
      color: #2c3e50;

      &.router-link-exact-active {
        color: #42b983;
      }
    }
  }
</style>
