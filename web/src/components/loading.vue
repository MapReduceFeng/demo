<template>
  <div v-if="loading" id="loading">
    <img src="../assets/loading.gif" style=" margin: auto">
  </div>
</template>

<script>
  export default {
    name: 'loading',
    computed: {
      loading() {
        return this.$store.state.loading
      }
    }
  }
</script>
<style>
  #loading {
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    position: fixed;
    left: 0;
    top: 0;
    z-index: 20;
    display: flex;
  }
</style>

