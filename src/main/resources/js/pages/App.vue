<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Utility Bills</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon
             v-if="!isLoggedIn && $route.path !== '/auth'"
             @click="login">Login
      </v-btn>
      <v-btn v-if="isLoggedIn" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main >
      <router-view></router-view>
      <v-container>
      <span v-if="responseMessage !== null">{{responseMessage.message}}</span>
      </v-container>
    </v-main>
  </v-app>

</template>

<script>
import {mapState} from "vuex";

export default {
  components: {},

  computed: {
    ...mapState(['isLoggedIn', 'responseMessage'])
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