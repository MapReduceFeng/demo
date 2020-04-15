<template>
  <div id="koa2">
    koa2: {{data1}}
    |
    Token: {{getToken}}
    <Button @click="getTokens" class="input" type="primary">getToken</Button>
    |
    <Button @click="getKoa2JWT" class="input" type="primary">getKoa2JWT</Button>
  </div>
</template>

<script>
  import store from "../../store";

  export default {
    name: 'koa2',
    data() {
      return {
        data1: '',
        getToken: ''
      }
    },
    methods: {
      getTokens() {
        let this_ = this;
        this.$apiConfigSchedule.getTokens({}).then(function (resp) {
          console.log(resp);
          this_.getToken = resp;
          this_.$store.state.token = resp;
        })
      },
      getKoa2JWT() {
        let this_ = this;
        this.$apiConfigSchedule.getKoa2JWT({}).then(function (resp) {
          console.log(resp);
          this_.data1 = resp.title;
        })
      }
    }
  }
</script>


