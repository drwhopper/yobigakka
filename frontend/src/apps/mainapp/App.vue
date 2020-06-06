<template>
  <div id="mainapp">
    <Nabar />
    <HelloWorld msg="やふー" />
    <section class="section">
      <router-view />
    </section>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Nabar from "@/components/Nabar.vue";
import HelloWorld from "@/components/HelloWorld.vue";

@Component({
  components: {
    Nabar,
    HelloWorld
  }
})
export default class App extends Vue {}
</script>

<style lang="scss">
@import "~bulma/sass/utilities/_all";

@import "~bulma";
@import "~buefy/src/scss/buefy";
</style>
