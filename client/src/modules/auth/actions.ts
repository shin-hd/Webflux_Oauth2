import { AxiosError } from "axios";
import { LoginParams, LoginResult } from "lib/api/auth";
import { createAsyncAction } from "typesafe-actions";

export const LOGIN = "auth/LOGIN";
export const LOGIN_SUCCESS = "auth/LOGIN_SUCCESS";
export const LOGIN_ERROR = "auth/LOGIN_ERROR";

export const loginAsync = createAsyncAction(LOGIN, LOGIN_SUCCESS, LOGIN_ERROR)<
  LoginParams,
  LoginResult,
  AxiosError
>();
