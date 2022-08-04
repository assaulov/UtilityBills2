import Vue from 'vue'
import Vuex from 'vuex'
import userApi from '../api/user'
import metersApi from '../api/meters'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        user: {},
        // isLoggedIn:localStorage.getItem("token"),
        isLoggedIn: false,
        isRegistrationFormVisible: false,
        metersList: []
    },
    getters: {},

    mutations: {
        registerForm(state, newValue) {
            state.isRegistrationFormVisible = newValue

        },
        loginUserMutation(state, user) {
            state.isLoggedIn = true
            state.user = user
            console.log('loginUserMutation', state.user)
        },
        logoutUserMutation(state, newValue) {
            state.isLoggedIn = newValue
        },
        registerUserMutation(state, responseMessage) {
            state.responseMessage = responseMessage
            console.log('registerUserMutation', this.metersList)

        },
        addMetersMutation(state, meter) {
            state.metersList = [
                ...state.metersList,
                meter
            ]
            console.log('addMetersMutation', this.metersList)
        },
        setMetersMutation(state, meters) {
            state.metersList = meters;
        },
    },
    actions: {
        async loginUserAction({commit, state}, userToLogin) {
            const response = await userApi.login(userToLogin)
            const data = await response.json()
            // localStorage.setItem("token", data.toString())
            console.log('loginUserAction', data)
            commit('loginUserMutation', data)
        },
        async registerUserAction({commit, state}, userToRegistration) {
            const response = await userApi.registration(userToRegistration)
            const data = await response.json()
            console.log('registerUserAction', data)
            commit('registerUserMutation', data)
        },
        async addMeterAction({commit, state}, meter, user) {
            const result = await metersApi.add(meter, user)
            const data = await result.json()
            commit('addMetersMutation', data)
        },
        async getMeterAction({commit}, user) {
            const result = await metersApi.getAll(user)
            const data = await result.data
            console.log('getActions', data)
            commit('setMetersMutation', data);
        },
        async deleteMeterAction({}, meter) {
            const response = await metersApi.deleteMeter(meter)
            const data = await response.json()
            console.log('delete', data)
        },
        async updateMeterAction({}, meter) {
            const response = await metersApi.updateMeter(meter)
            const data = await response.json()
            console.log('update', data)
        }


    }

})
