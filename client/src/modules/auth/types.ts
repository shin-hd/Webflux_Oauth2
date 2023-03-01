import { LoginResult } from "lib/api/auth";
import { AsyncState } from "lib/reducerUtils";
import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type LoginAction = ActionType<typeof actions>;

export type LoginState = {
  loginResult: AsyncState<LoginResult, Error>;
};
