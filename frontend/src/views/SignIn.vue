<template>
  <div class="card">
    <header class="card-header">
      <p class="card-header-title">Sign in</p>
    </header>
    <div class="card-content">
      <div class="content">
        <b-field label="Sign in">
          <b-input v-model="userID" />
          <b-input v-model="password" />
          <p class="control">
            <b-button class="button is-primary" @click="submit">
              Submit
            </b-button>
          </p>
        </b-field>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import axios from "@/utils/axios";

@Component
export default class SignIn extends Vue {
  userID = "";
  password = "";
  submit() {
    axios
      .post("/signin", {
        userID: this.userID,
        password: this.password
      })
      .then(response => {
        console.log(response);
        this.$router.push("/");
      })
      .catch(error => console.log(error.response));
  }
}
</script>
