import { login } from "lib/api/auth";
import { LOGIN, loginAsync } from "./actions";
import { takeLeading } from "redux-saga/effects";
import createAsyncSaga from "lib/createAsyncSaga";

const loginSaga = createAsyncSaga(loginAsync, login);

export function* authSaga() {
  yield takeLeading(LOGIN, loginSaga);
}
