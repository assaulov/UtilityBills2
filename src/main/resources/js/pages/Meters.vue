<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div class="title mb-3">User Profile</div>
        <v-layout row justify-space-between>
          <v-flex class="px-1">
            <v-layout column>
              <v-flex>{{this.user.login}}</v-flex>
              <v-flex>{{this.user.fullName}}</v-flex>
              <v-flex>{{this.user.gender}}</v-flex>
              <v-flex>{{this.user.email}}</v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
<
    <MetersTable></MetersTable>

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
}
</script>

<style scoped>

</style>