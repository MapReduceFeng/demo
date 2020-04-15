<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <Input v-model="value4" icon="ios-clock-outline" placeholder="Enter something..." style="width: 200px"
           @on-click="onClick"/>
    <br/>
    <br/>
    <Input v-model="value18" type="password" password placeholder="Enter something..." style="width: 200px"/>
  </div>
</template>

<script>
  export default {
    name: 'HelloWorld',
    props: {
      msg: String
    },
    data() {
      return {
        value4: '',
        value18: ''
      }
    },
    methods: {
      onClick() {
        alert("检证通过！")
      }
    }
  }
</script>
