import Vue from 'vue'
import axios from "axios";

const url = 'http://localhost:8888/bills/'

export default {

     login: userToLogin =>Vue.http.post('auth/signin', userToLogin),
     registration: userToRegistration => Vue.http.post('auth/signup', userToRegistration)

}