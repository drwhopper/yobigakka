<template>
  <div class="card">
    <header class="card-header">
      <p class="card-header-title">UserID Registration</p>
    </header>
    <div class="card-content">
      <div class="content">
        <b-field label="Sign Up">
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
import axios from "axios";

@Component
export default class SignUp extends Vue {
  userID!: string;
  submit() {
    axios
      .post("/signup", {
        userID: this.userID
      })
      .then(() => this.$router.push("/"))
      .catch(error => console.log(error.response));
  }
}
</script>
