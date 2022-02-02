<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>
        <router-link to="/" tag="span" style="cursor: pointer">
        UtilityBills-2
        </router-link>
      </v-toolbar-title>
      <v-btn class="meters" v-if="isLoggedIn" @click="meters">Meters</v-btn>
      <v-spacer></v-spacer>
      <v-btn
          v-if="!isLoggedIn && $route.path !== '/auth'"
          @click="showAccessForm"
      >Войти </v-btn>
      <v-btn  v-if="isLoggedIn" @click="logout" >
       LOG OUT
      </v-btn>
    </v-app-bar>
    <access v-show="isRegistrationFormVisible"></access>

    <v-content>
      <router-view></router-view>
    </v-content>
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
      this.userData = false;
      this.$router.push('/bills/meters')
    },
  },
}
</script>

<style>

.meters{
margin: 1%;
}
</style>
