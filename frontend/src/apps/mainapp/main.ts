import Vue from "vue";
import Vuex from "vuex";
import App from "@/apps/mainapp/App.vue";
import Buefy from "buefy";
import router from "@/apps/mainapp/router";

Vue.config.productionTip = false;
Vue.use(Buefy);
Vue.use(Vuex);

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
