import Vue from 'vue'


export default {

    add: (meter) => Vue.http.post('/bills/meters/'+ meter.userLogin, meter),
    getAll: user => Vue.http.get('/bills/meters/' + user.login)

}