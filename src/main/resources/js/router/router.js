import Vue from 'vue'
import VueRouter from 'vue-router'
import App from "../pages/App.vue";
import Login from "../pages/Login.vue";


Vue.use(VueRouter)

const routes = [
    { path: '/', component:  App},
    { path: '/login', component:  Login}

]

export default new VueRouter({
    mode: 'history',
    routes
})