import Vue from 'vue'


export default {

    add: (meter) => Vue.http.post('/bills/meters/' + meter.userLogin, meter),
    getAll: user => Vue.http.get('/bills/meters/' + user.login),
    deleteMeter: (meter) => Vue.http.post('/bills/meters/' + meter.userLogin + '/delete', meter),
    updateMeter: (meter) => Vue.http.put('/bills/meters/' + meter.userLogin, meter)

}
