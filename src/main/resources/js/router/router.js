import Vue from 'vue'
import VueRouter from 'vue-router'
import Meters from "../pages/Meters.vue";
import App from "../pages/App.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/bills/meters', component: Meters },
    { path: '/bills', component: App }
]

export default new VueRouter({
     mode: 'history',
    routes
})