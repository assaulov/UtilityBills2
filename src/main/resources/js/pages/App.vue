<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Utility Bills</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn  icon @click="login">Login
      </v-btn>
    </v-app-bar>

    <v-main >
      <router-view></router-view>
    </v-main>

  </v-app>
</template>

<script>
export default {
methods: {
  login(){
    this.$router.push('/login')

  }
}
}
</script>

<style>

</style>