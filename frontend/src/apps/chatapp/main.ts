import Vue from "vue";
import ChatApp from "./ChatApp.vue";
import Buefy from "buefy";

Vue.config.productionTip = false;
Vue.use(Buefy);

new Vue({
  render: h => h(ChatApp)
}).$mount("#app");
