<template>
  <v-layout row class="my-5">
    <v-col
        cols="12"
        sm="6"
        md="3">
      <v-text-field
          label="New Message"
          placeholder="New Message"
          v-model="text"/>
      <v-text-field
          label="New Message"
          placeholder="New Message"
          v-model="text"/>
    </v-col>
    <v-btn class="ma-15" v-on:click="save" large>Login</v-btn>
  </v-layout>
</template>

<script>
export default {
  name: "LoginForm"
}
</script>

<style scoped>

</style>