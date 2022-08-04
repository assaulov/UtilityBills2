import Vue from 'vue'

const url = 'http://localhost:8888/bills/'
const headers = {
    "Authorization": "Bearer my-token",
    "My-Custom-Header": "foobar"
};
export default {

    login: userToLogin => Vue.http.post('auth/signin', userToLogin, {headers}),
    registration: userToRegistration => Vue.http.post('auth/signup', userToRegistration)

}
