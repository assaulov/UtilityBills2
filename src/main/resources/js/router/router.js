import Vue from 'vue'
import VueRouter from 'vue-router'
import Meters from "../pages/Meters.vue";
import Main from "../pages/Main.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/bills/meters', component: Meters },
    { path: '/bills', component: Main },
    { path: '/', component: Main }
]

export default new VueRouter({
     mode: 'hash',
    routes
})
