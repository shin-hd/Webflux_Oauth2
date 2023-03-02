import { UserProfile } from "lib/api/users";
import { AsyncState } from "lib/reducerUtils";
import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type ProfileAction = ActionType<typeof actions>;

export type ProfileState = {
  profile: AsyncState<UserProfile, Error>;
};
