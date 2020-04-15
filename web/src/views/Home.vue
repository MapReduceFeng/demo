<template>
  <div class="home">
    <login msg="Welcome to Your Vue"/>
  </div>
</template>

<script>
  import login from '@/components/login.vue'

  export default {
    name: 'home',
    components: {
      login
    }
  }
</script>
