import { login, LoginResult } from "lib/api/auth";
import { LOGIN, loginAsync } from "./actions";
import { call, put, takeLeading } from "redux-saga/effects";

function* loginSaga(action: ReturnType<typeof loginAsync.request>) {
  try {
    const loginResult: LoginResult = yield call(login, action.payload);
    yield put(loginAsync.success(loginResult));
  } catch (e: any) {
    yield put(loginAsync.failure(e));
  }
}

export function* authSaga() {
  yield takeLeading(LOGIN, loginSaga);
}
