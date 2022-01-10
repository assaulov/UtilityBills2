import Vue from 'vue'
import Vuex from 'vuex'
import userApi from '../api/user'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        user:{},
        isLoggedIn:localStorage.getItem("token"),
        isRegistrationFormVisible:false,
        responseMessage:''
    },
    getters: {
    },

    mutations: {
        registerForm(state, newValue){
            state.isRegistrationFormVisible=newValue

        },
        loginUserMutation(state,responseMessage ){
            state.isLoggedIn = true
            state.responseMessage=responseMessage

        },
        registerUserMutation(state, responseMessage){
            state.responseMessage=responseMessage
        }
    },
    actions: {
        async loginUserAction({commit, state}, userToLogin) {
            const response = await userApi.login(userToLogin)
            const data = await  response.json()
            localStorage.setItem("token", "JWT")
            commit('loginUserMutation',data)
        },
        async registerUserAction({commit, state}, userToRegistration) {
            const response = await userApi.registration(userToRegistration)
            const data = await response.json()
            commit('registerUserMutation', data)
        }

    }

})
