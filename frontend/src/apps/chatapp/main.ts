import Vue from "vue";
import App1 from "./App1.vue";
import Buefy from "buefy";
import router from "./router";

Vue.config.productionTip = false;
Vue.use(Buefy);

new Vue({
  router,
  render: h => h(App1)
}).$mount("#app");
