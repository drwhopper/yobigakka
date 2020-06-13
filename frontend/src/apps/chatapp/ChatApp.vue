<template>
  <div id="chatapp">
    <Nabar />
    <section class="hero is-bold is-primary">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">ChatRoom</h1>
          <h2>0101</h2>
        </div>
      </div>
    </section>
    <section class="section">
      <div class="columns">
        <div class="column is-3">
          <div class="box">
            <p class="title">Cards</p>
          </div>
        </div>
        <div class="column is-7">
          <section>
            <b-field label="会話: " grouped>
              <b-input
                placeholder="たまにストップする議論"
                expanded
                v-model="myMessage"
              >
              </b-input>
              <b-button class="button is-info" v-on:click="send()">
                発言
              </b-button>
            </b-field>
          </section>
          <section>
            <TalkView v-bind:chatmsg="messages" />
          </section>
        </div>
        <div class="column is-2">
          <div class="box">
            <p class="title">Members</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Nabar from "@/components/Nabar.vue";
import TalkView from "@/components/chat/TalkView.vue";

@Component({
  components: {
    Nabar,
    TalkView
  }
})
export default class ChatApp extends Vue {
  messages: string[] = [];
  myMessage = "";
  websocket!: WebSocket;
  mounted() {
    this.websocket = new WebSocket("ws://localhost:9000/chatconnect");
    this.websocket.onmessage = this.onMessage;
  }
  onMessage(event: MessageEvent) {
    this.messages.push(event.data);
  }
  send() {
    const text = this.myMessage && this.myMessage.trim();
    if (!text) {
      return;
    }
    this.websocket.send(this.myMessage);
    this.myMessage = "";
  }
}
</script>

<style lang="scss">
@import "~bulma/sass/utilities/_all";

@import "~bulma";
@import "~buefy/src/scss/buefy";
</style>
