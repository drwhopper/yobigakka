import { Module, VuexModule, Mutation, Action } from "vuex-module-decorators";

@Module
export default class CSRFModule extends VuexModule {
  token = "";

  get getToken() {
    return this.token;
  }

  @Action
  setNewToken(token: string) {
    this.context.commit("setToken", token);
  }

  @Mutation
  setToken(token: string) {
    this.token = token;
  }
}
