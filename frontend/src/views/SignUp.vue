<template>
  <div class="card">
    <header class="card-header">
      <p class="card-header-title">Username Registration</p>
    </header>
    <div class="card-content">
      <div class="content">
        <b-field label="Name">
          <b-input v-model="name" />
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
  name!: string;
  submit() {
    axios
      .post("/signup", name)
      .then(response => (window.location.href = response.request.responseURL));
  }
}
</script>
