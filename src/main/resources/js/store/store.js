import Vue from 'vue'
import Vuex from 'vuex'
import userApi from '../api/user'
import metersApi from '../api/meters'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        user:{},
        // isLoggedIn:localStorage.getItem("token"),
        isLoggedIn:false,
        isRegistrationFormVisible:false,
        metersList: []
    },
    getters: {

    },

    mutations: {
        registerForm(state, newValue){
            state.isRegistrationFormVisible=newValue

        },
        loginUserMutation(state,user){
            state.isLoggedIn = true
            state.user = user

        },
        registerUserMutation(state, responseMessage){
            state.responseMessage=responseMessage
        },
        addMetersMutation(state,meter){
                state.metersList = [
                    ...state.metersList,
                    meter
                ]
            console.log('addMetersMutation', this.metersList)
        },
        setMetersMutation(state, meters) {
            // Products are the products fetched from the API.
            state.metersList = meters;
        },
    },
    actions: {
        async loginUserAction({commit, state}, userToLogin) {
            const response = await userApi.login(userToLogin)
            const data = await  response.json()
            // localStorage.setItem("token", "JWT")
            console.log(data)
            commit('loginUserMutation',data)
        },
        async registerUserAction({commit, state}, userToRegistration) {
            const response = await userApi.registration(userToRegistration)
            const data = await response.json()
            commit('registerUserMutation', data)
        },
        async addMeterAction({commit, state}, meter){
            const result = await metersApi.add(meter)
            const data = await result.json()
            commit('addMetersMutation', data)
        },
        async getMeterAction({ commit }){
            const result = await metersApi.getAll()
            const data = await result.data
            console.log('getActions', data)
            commit('setMetersMutation', data);
        }

    }

})
