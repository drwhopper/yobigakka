import { Module, VuexModule, Mutation, Action } from "vuex-module-decorators";
import { User } from "./types";

@Module
export default class UserModule extends VuexModule {
  user?: User;

  get getUser() {
    return this.user;
  }

  @Action
  setUser(user: User) {
    this.context.commit("setUserData", user);
  }

  @Mutation
  setUserData(user: User) {
    this.user = user;
  }
}
