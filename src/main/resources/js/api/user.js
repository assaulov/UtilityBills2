import Vue from 'vue'
export default {
    login: userToLogin =>Vue.http.post('/auth/signin', userToLogin),
    registration: userToRegistration => Vue.http.post('/auth/signup', userToRegistration)
}