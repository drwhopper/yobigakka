<template>
  <div id="chatapp">
    <Nabar />
    <HelloWorld msg="ちゃちゃっと" />
    <div id="chat">
      <div id="messages" v-for="message in messages" :key="message">
        <div id="message">{{ message }}</div>
      </div>
    </div>
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
export default class ChatApp extends Vue {
  messages: string[] = [];
  websocket: WebSocket | null = null;
  mounted() {
    this.websocket = new WebSocket("ws://localhost:19000/chat");
    this.websocket.onmessage = this.onMessage;
  }
  onMessage(event: MessageEvent) {
    this.messages.push(event.data);
  }
}
</script>

<style lang="scss">
@import "~bulma/sass/utilities/_all";

@import "~bulma";
@import "~buefy/src/scss/buefy";
</style>
