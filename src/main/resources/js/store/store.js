import Vue from 'vue'
import Vuex from 'vuex'
import userApi from '../api/user'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        user:null,
        isLoggedIn:false,
        isRegistrationFormVisible:false,
        userLogin:''
    },
    getters: {
    },

    mutations: {
        registerForm(state, newValue){
            state.isRegistrationFormVisible=newValue

        },
        loginUserMutation(state,user){
            state.isLoggedIn = true
            state.user=user

        },
        registerUserMutation(state, user){
            state.user=user
        }
    },
    actions: {
        async loginUserAction({commit, state}, userToLogin) {
            const response = await userApi.login(userToLogin)
            const data = await  response.json()
            commit('loginUserMutation',data)
        },
        async registerUserAction({commit, state}, userToRegistration) {
            const response = await userApi.registration(userToRegistration)
            const data = await response.json()
            commit('registerUserMutation', data)
        }

    }

})
