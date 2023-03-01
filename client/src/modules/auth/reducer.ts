import { LoginAction, LoginState } from "./types";
import { createReducer } from "typesafe-actions";
import { loginAsync } from "./actions";
import {
  asyncState,
  createAsyncReducer,
  transformToArray,
} from "lib/reducerUtils";

const initialState: LoginState = {
  loginResult: asyncState.initial(),
};

const auth = createReducer<LoginState, LoginAction>(initialState).handleAction(
  transformToArray(loginAsync),
  createAsyncReducer(loginAsync, "loginResult")
);

export default auth;
