<template>
  <section
    class="hero is-bold is-primary"
    v-bind:class="{ 'is-medium': isHome }"
  >
    <div class="hero-body">
      <div class="container">
        <h1 class="title">{{ msg }}</h1>
        <h2>Yobigakka is here. {{ this.$route.path }}, {{ isHome }}</h2>
      </div>
    </div>
  </section>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class HelloWorld extends Vue {
  @Prop() private msg!: string;
  get isHome() {
    return this.$route.path === "/";
  }
}
</script>
