import { GET_PROFILE, getProfileAsync } from "./actions";
import { takeLeading } from "redux-saga/effects";
import { getMyProfile } from "lib/api/users";
import { createEmptyAsyncSaga } from "lib/createAsyncSaga";

const getProfileSaga = createEmptyAsyncSaga(getProfileAsync, getMyProfile);

export function* userSaga() {
  yield takeLeading(GET_PROFILE, getProfileSaga);
}
