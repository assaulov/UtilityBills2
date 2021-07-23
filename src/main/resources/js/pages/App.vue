<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Utility Bills</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon
             v-if="!loggedIn && $route.path !== '/auth'"
             @click="login">Login
      </v-btn>
      <v-btn v-if="loggedIn" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main >
      <router-view></router-view>
    </v-main>

  </v-app>
</template>

<script>
import {mapState} from "vuex";

export default {
  computed: {
    ...mapState(['loggedIn'])
  },
  methods: {

    login(){
      this.$router.push('/auth')

    }
  }
}
</script>

<style>

</style>