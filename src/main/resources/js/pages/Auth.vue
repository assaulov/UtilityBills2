<template>

  <v-container>
    <auth-form v-if="!isRegistrationFormVisible" class="pa-10 ma-20"></auth-form>
    <v-col  class="align-self-center pt-16">
      <registration-form class="py-20" v-if="isRegistrationFormVisible"></registration-form>
    </v-col>
    <v-btn v-if="!isRegistrationFormVisible" @click="register" class="pa-10 ma-30">
      Зарегистрироваться
    </v-btn>
  </v-container>
</template>

<script>
import AuthForm from "../components/login/AuthForm.vue";
import RegistrationForm from "../components/registration/RegistrationForm.vue";
import {mapMutations, mapState} from "vuex";
export default {
  name: "Auth",
  components: {RegistrationForm, AuthForm},
  computed: {
    ...mapState(['isRegistrationFormVisible'])
  },
  methods:{
    ...mapMutations(['registerForm']),

    register(){
      this.registerForm(true)
    }
  }
}
</script>

<style scoped>

</style>