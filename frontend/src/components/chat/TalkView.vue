<template>
  <div id="chat">
    <div class="box" id="message" v-for="message in chatmsg" :key="message">
      <p>{{ message }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class TalkView extends Vue {
  @Prop() private chatmsg!: string[];
}
</script>
