import axios from "axios";
import store from "@/store";

const token = store.getters["getToken"];

axios.defaults.headers.common = {
  "X-Requested-With": "wpwp",
  "CSRF-Token": token
};

export default axios;
