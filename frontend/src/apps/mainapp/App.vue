<template>
  <div id="mainapp">
    <Nabar msg="" />
    <router-view />
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
import Nabar from "@/components/Nabar.vue";

export default {
  components: {
    Nabar
  }
};
</script>

<style lang="scss">
@import "~bulma/sass/utilities/_all";

@import "~bulma";
@import "~buefy/src/scss/buefy";
</style>
