<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Utility Bills-ServerEdition</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn v-if="isLoggedIn" @click="meters">Meters</v-btn>
      <v-spacer></v-spacer>
      <v-btn v-if="isLoggedIn" @click="logout" >
<!--        <v-icon>exit_to_app</v-icon>--> LOG OUT
      </v-btn>
    </v-app-bar>
    <v-main >
      <router-view></router-view>
      <span class="message" v-if="userData && isLoggedIn">{{user}}</span>
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
import Vue from 'vue';

export default {
  name: 'app',
  created () {
    // Read sessionStorage on page load
    if (sessionStorage.getItem('store')) {
      this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem('store'))))
    }
    // Save the store to sessionStorage when the page is refreshed
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('store', JSON.stringify(this.$store.state))
    })
  },
  components: {Access},
  data() {
    return {
      userData: true,
      access: true
    }
  },
  computed: {
    ...mapState(['isLoggedIn', 'user', 'isRegistrationFormVisible'])
  },
  methods: {
    ...mapMutations(['registerForm', 'logoutUserMutation']),

    login(){
      this.$router.push('/auth')
    },
    logout(){
      Vue.http.post('/bills/logout')
      this.logoutUserMutation(false)
      sessionStorage.clear()
      this.$router.push('/bills')
    },
    showAccessForm(){
      this.registerForm(true)
    },
    meters(){
      this.$router.push('/bills/meters')
      this.userData = false;
    },
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