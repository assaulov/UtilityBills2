<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Utility Bills</v-toolbar-title>
      <v-spacer></v-spacer>
<!--      <v-btn icon-->
<!--             v-if="!isLoggedIn && $route.path !== '/auth'"-->
<!--             @click="showAccessForm">-->
<!--        Login-->
<!--      </v-btn>-->
      <v-btn v-if="isLoggedIn" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main >
      <router-view></router-view>
      <span class="message" v-show="responseMessage !== null">{{responseMessage.message}}</span>
      <access v-show="isRegistrationFormVisible"></access>
      <v-btn
          large
          height="400px"
          width="900px"
          color="indigo"
        style="font-size: 200px"
          class="login"
          v-if="!isLoggedIn && $route.path !== '/auth'"
          @click="showAccessForm">
        Войти
      </v-btn>

    </v-main>
  </v-app>
</template>

<script>
import {mapMutations, mapState} from "vuex";
import Access from "../components/Access.vue";

export default {
  components: {Access},
  data() {
    return {
      access: true
    }
  },
  computed: {
    ...mapState(['isLoggedIn', 'responseMessage', 'isRegistrationFormVisible'])
  },
  methods: {
    ...mapMutations(['registerForm']),

    login(){
      this.$router.push('/auth')
    },
    showAccessForm(){
      this.registerForm(true)
    }
  },
}
</script>

<style>

.login {
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
.message{
  font-size:50px;
  color: forestgreen;
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
</style>