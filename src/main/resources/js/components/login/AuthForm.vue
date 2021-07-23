<template>
  <v-layout row class="my-5">
    <v-col
        cols="12"
        sm="6"
        md="3">
      <v-text-field
          label="Login"
          placeholder="Login"
          v-model="login"/>
    </v-col>
    <v-col
        cols="12"
        sm="6"
        md="3">
      <v-text-field
          label="Password"
          placeholder="Password"
          type="password"
          v-model="password"/>
    </v-col>
    <v-btn class="my-5" v-on:click="loginUser" large>Login</v-btn>
  </v-layout>
</template>

<script>
import {mapActions, mapState} from 'vuex'
export default {
  name: "AuthForm",
  computed: {
    ...mapState(['loggedIn'])
  },
  data() {
    return {
      login:'',
      password:''
    }
  },
  methods:{
    ...mapActions(['loginUserAction']),
    async loginUser(){
      await this.loginUserAction({
        login: this.login,
        password: this.password
      })
      if(this.loggedIn){
      await  this.$router.push("/")
      }
    },
  }
}
</script>

<style scoped>

</style>