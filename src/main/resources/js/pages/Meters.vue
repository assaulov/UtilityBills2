<template>
    <v-container fluid>
        <v-layout row wrap>
            <v-flex xs12 class="text-center" mt-5>
                <h1>User Profile</h1>
                <p>Login: {{ this.user.login }}</p>
                <p>Username: {{ this.user.fullName }}</p>
                <p>Gender: {{ this.user.gender }}</p>
                <p>E-Mail: {{ this.user.email }}</p>
            </v-flex>
        </v-layout>

        <MetersTable/>

    </v-container>

</template>

<script>
import {mapState} from "vuex";
import MetersTable from "../components/MetersTable.vue";


export default {
    name: "Meters",
    components: {MetersTable},
    computed: {
        ...mapState(['user'])
    },
    methods: {
        reload(event) {
            this.$router.push('/bills/meters')
        }
    },
    mounted() {
        window.addEventListener('unload', this.reload)
    },
    beforeDestroy() {
        window.removeEventListener('unload', this.reload)

    }
}


</script>

<style scoped>

</style>
