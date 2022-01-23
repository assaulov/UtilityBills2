<template>
  <v-dialog v-model="isRegistrationFormVisible" persistent max-width="600px" min-width="360px">
    <div>
      <v-tabs v-model="tab" show-arrows background-color="deep-purple accent-4" icons-and-text dark grow>
        <v-tabs-slider color="purple darken-4"></v-tabs-slider>
        <v-tab v-for="i in tabs" :key="i">
          <v-icon large>{{ i.icon }}</v-icon>
          <div class="caption py-1">{{ i.name }}</div>
        </v-tab>
        <v-tab-item>
          <v-card class="px-4">
            <v-card-text>
              <v-form ref="loginForm" v-model="valid" lazy-validation>
                <v-row>
                  <v-col cols="12">
                    <v-text-field v-model="login" label="Логин" required></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field v-model="loginPassword" :append-icon="show1?'eye':'eye-off'" :rules="[rules.required, rules.min]" :type="show1 ? 'text' : 'password'" name="input-10-1" label="Пароль" hint="At least 4 characters" counter @click:append="show1 = !show1"></v-text-field>
                  </v-col>
                  <v-col class="d-flex" cols="12" sm="6" xsm="6" align-center>
                    <v-btn
                        x-medium block
                        @click="closeForm"
                    >
                      Закрыть
                    </v-btn>
                    <v-btn x-medium block :disabled="!valid" color="success" @click="loginUser"> Войти </v-btn>
                  </v-col>
                </v-row>
              </v-form>
            </v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card class="px-4">
            <v-card-text>
              <v-form ref="registerForm" v-model="valid" lazy-validation>
                <v-row>
                  <v-col cols="12">
                    <v-text-field v-model="login" :rules="[rules.required, rules.min]"  type="text" label="Логин" maxlength="20"  hint="At least 4 characters" required counter></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="6">
                    <v-text-field v-model="firstName" :rules="[rules.required]" label="Имя" maxlength="20" required></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="6">
                    <v-text-field v-model="lastName" :rules="[rules.required]" label="Фамилия" maxlength="20" required></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field v-model="email" :rules="emailRules" label="E-mail" required></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field v-model="password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :rules="[rules.required, rules.min]" :type="show1 ? 'text' : 'password'" name="input-10-1" label="Пароль" hint="At least 8 characters" counter @click:append="show1 = !show1"></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field block v-model="verify" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :rules="[rules.required, passwordMatch]" :type="show1 ? 'text' : 'password'" name="input-10-1" label="Подтвердите пароль" counter @click:append="show1 = !show1"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="2" md="2">
                    <v-select
                        v-model="genders.abbr"
                        :items="genders"
                        item-value="abbr" item-text="gender"
                        label="Пол"
                    ></v-select>
                  </v-col>
                  <v-col class="d-flex" md="auto">
                    <v-btn x-large block :disabled="!isDisable" color="success" @click="validate" >Зарегестрироваться</v-btn>
                  </v-col>
                  <v-col class="d-flex" md="auto">
                    <v-btn
                        x-large block
                        @click="closeForm"
                    >
                      Закрыть
                    </v-btn>
                  </v-col>
                </v-row>
              </v-form>
            </v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </div>
  </v-dialog>
</template>

<script>
import {mapActions, mapMutations, mapState} from "vuex";

export default {
  name: "Access",
  computed: {
    ...mapState(['isRegistrationFormVisible']),
    passwordMatch() {
      return () => this.password === this.verify || "Password must match";
    },

    isDisable(){
      return this.login && this.firstName && this.lastName && this.email && this.password;
    }
  },
  methods: {
    ...mapActions(['loginUserAction', 'registerUserAction']),
    ...mapMutations(['registerForm']),
    async loginUser(){
      await this.loginUserAction({
        login: this.login,
        password: this.loginPassword
      })
      this.login = ''
      this.password = ''
      this.registerForm(false)
    },
    async validate() {
      await this.registerUserAction({
        login: this.login,
        password: this.password,
        firstName:this.firstName,
        lastName:this.lastName,
        gender: this.genders.abbr,
        email: this.email
      })
      this.login = ''
      this.password = ''
      this.firstName = ''
      this.lastName = ''
      this.genders.abbr = ''
      this.email = ''

      this.closeForm()
    },
    closeForm(){
      this.registerForm(false)
    }
  },
  data: () => ({
    tab: 0,
    tabs: [
      {name:"Войти", icon:"mdi-account"},
      {name:"Регистрация", icon:"mdi-account-outline"}
    ],
    valid: true,
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    verify: "",
    loginPassword: "",
    login: "",
    select: null,
    genders: [
      {gender: 'Ж', abbr: 'FEMALE'},
      {gender: 'М', abbr: 'MALE'}
    ],
    emailRules: [
      v => !!v || "Required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],

    show1: false,
    rules: {
      required: value => !!value || "Required.",
      min: v => (v && v.length >= 4) || "Min 4 characters"
    }
  })

}
</script>

<style scoped>

</style>