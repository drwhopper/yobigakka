<template>
  <div id="chatapp">
    <Nabar msg="" />
    <router-view />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import HelloWorld from "@/components/HelloWorld.vue";
import Nabar from "@/components/Nabar.vue";

@Component({
  components: {
    HelloWorld,
    Nabar
  }
})
export default class App extends Vue {}
</script>

<style lang="scss">
@import "~bulma/sass/utilities/_all";

@import "~bulma";
@import "~buefy/src/scss/buefy";
</style>
