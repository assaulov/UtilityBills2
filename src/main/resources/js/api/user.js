import Vue from 'vue'
const users = Vue.resource('/auth/signin')
export default {
    add: user => users.save({}, user),
}