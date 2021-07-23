import Vue from 'vue'
import VueRouter from 'vue-router'
import App from "../pages/App.vue";
import Auth from "../pages/Auth.vue";
import store from "../store/store";

Vue.use(VueRouter)

const routes = [
    { path: '/', component:  App},
    { path: '/auth', component:  Auth},
    { path: '*', component: App }

]

export default new VueRouter({
    mode: 'history',
    routes
})