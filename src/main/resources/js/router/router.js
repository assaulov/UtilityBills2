import Vue from 'vue'
import VueRouter from 'vue-router'
import App from "../pages/App.vue";
import Auth from "../pages/Auth.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/auth', component: Auth},
]

export default new VueRouter({
    mode: 'history',
    routes
})