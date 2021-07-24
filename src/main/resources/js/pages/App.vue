<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Utility Bills</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon
             v-if="!isLoggedIn && $route.path !== '/auth'"
             @click="openPopup">Login
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
      <access v-if="!isLoggedIn"></access>
    </v-main>
  </v-app>

</template>

<script>
import {mapMutations, mapState} from "vuex";
import Access from "../components/Access.vue";

export default {
  components: {Access},
  computed: {
    ...mapState(['isLoggedIn', 'responseMessage', 'isRegistrationFormVisible'])
  },
  methods: {
    ...mapMutations(['registerForm']),

    login(){
      this.$router.push('/auth')
    },
    openPopup(){
      this.registerForm(true)
    }
  },
}
</script>

<style>

</style>