import { ProfileAction, ProfileState } from "./types";
import { createReducer } from "typesafe-actions";
import {
  GET_PROFILE,
  GET_PROFILE_ERROR,
  GET_PROFILE_SUCCESS,
  INITIALIZE_PROFILE,
} from "./actions";
import { asyncState } from "lib/reducerUtils";

const initialState: ProfileState = {
  profile: asyncState.initial(),
};

const user = createReducer<ProfileState, ProfileAction>(initialState, {
  [INITIALIZE_PROFILE]: (state) => ({
    ...state,
    profile: asyncState.initial(),
  }),
  [GET_PROFILE]: (state) => ({
    ...state,
    profile: asyncState.load(),
  }),
  [GET_PROFILE_SUCCESS]: (state, action) => ({
    ...state,
    profile: asyncState.success(action.payload),
  }),
  [GET_PROFILE_ERROR]: (state, action) => ({
    ...state,
    profile: asyncState.error(action.payload),
  }),
});

export default user;
