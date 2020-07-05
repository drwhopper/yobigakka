<template>
  <div id="mainapp">
    <Nabar />
    <HelloWorld msg="やふーー" />
    <section class="section">
      <router-view />
    </section>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import store from "@/store";
import Nabar from "@/components/Nabar.vue";
import HelloWorld from "@/components/HelloWorld.vue";

@Component({
  components: {
    Nabar,
    HelloWorld
  }
})
export default class App extends Vue {
  csrfToken = "";
  created() {
    const meta = document.querySelector("meta[name='csrf-token-value']");
    if (meta != null) {
      const token = meta.getAttribute("content");
      this.csrfToken = token == null ? "" : token;
    }
    store.commit("setToken", this.csrfToken);
    console.log(this.csrfToken);
  }
}
</script>

<style lang="scss">
@import "~bulma/sass/utilities/_all";

@import "~bulma";
@import "~buefy/src/scss/buefy";
</style>
