import Vue from 'vue'
import VueRouter from 'vue-router'
import Meters from "../pages/Meters.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/meters', component: Meters }
]

export default new VueRouter({
    // mode: 'history',
    routes
})