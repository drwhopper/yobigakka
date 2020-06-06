<template>
  <div id="chat">
    <div id="messages" v-for="message in chatmsg" :key="message">
      <div class="box" id="message">
        <p>{{ message }}</p>
      </div>
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
