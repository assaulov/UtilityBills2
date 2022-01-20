import Vue from 'vue'
import {mapState} from "vuex";

export default {

    add: meter => Vue.http.post('/meters/test_user', meter),
    getAll: meter => Vue.http.get('/meters/test_user')

}