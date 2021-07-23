import Vue from 'vue'
import Vuex from 'vuex'
import userApi from '../api/user'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        user:{},
        loggedIn:''
    },
    getters: {
    },
    mutations: {
        loginUserMutation(state){
            state.loggedIn = true
        }
    },
    actions: {
        async loginUserAction({commit, state}, user) {
            const response = await userApi.add(user)
            const data = await  response.json()
            commit('loginUserMutation',data)
        }
    }

})
