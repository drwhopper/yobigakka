<template>
  <nav class="navbar is-light">
    <div class="navbar-brand">
      <span class="navbar-item">
        予備学科
      </span>
    </div>
  </nav>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class Nabar extends Vue {
  @Prop() private msg!: string;
}
</script>
