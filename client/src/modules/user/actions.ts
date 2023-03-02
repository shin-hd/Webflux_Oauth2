import { AxiosError } from "axios";
import { UserProfile } from "lib/api/users";
import { createAction, createAsyncAction } from "typesafe-actions";

export const INITIALIZE_PROFILE = "user/INITIALIZE_PROFILE";

export const GET_PROFILE = "user/GET_PROFILE";
export const GET_PROFILE_SUCCESS = "user/GET_PROFILE_SUCCESS";
export const GET_PROFILE_ERROR = "user/GET_PROFILE_ERROR";

export const initializeProfile = createAction(INITIALIZE_PROFILE)();
export const getProfileAsync = createAsyncAction(
  GET_PROFILE,
  GET_PROFILE_SUCCESS,
  GET_PROFILE_ERROR
)<undefined, UserProfile, AxiosError>();
