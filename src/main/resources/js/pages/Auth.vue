<template>
  <v-container>
  <v-container>
  <v-layout>
  <auth-form v-if="!isRegistrationFormVisible" class="pa-10 ma-20"></auth-form>
  </v-layout>
    </v-container>
  <v-container class="my-20">>
    <v-col  class="align-self-center">
      <registration-form v-if="isRegistrationFormVisible"></registration-form>
    </v-col>
  </v-container>
  <v-container>
    <v-layout>
    <v-btn v-if="!isRegistrationFormVisible" @click="register" class="pa-10 ma-30">
      Зарегистрироваться
    </v-btn>
    </v-layout>
  </v-container>
  </v-container>
</template>

<script>
import AuthForm from "../components/login/AuthForm.vue";
import RegistrationForm from "../components/registration/RegistrationForm.vue";
export default {
  name: "Auth",
  components: {RegistrationForm, AuthForm},
  data(){
      return{
        isRegistrationFormVisible: false
      }
  },
  methods:{
    register(){
    this.isRegistrationFormVisible=true
    }
  }
}
</script>

<style scoped>

</style>