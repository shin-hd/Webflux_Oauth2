import { combineReducers } from "redux";
import { all } from "redux-saga/effects";
import auth, { authSaga } from "./auth";
import cards, { cardsSaga } from "./cards";
import user, { userSaga } from "./user";

const rootReducer = combineReducers({
  auth,
  user,
  cards,
});

export default rootReducer;

export type RootState = ReturnType<typeof rootReducer>;

export function* rootSaga() {
  yield all([authSaga(), userSaga(), cardsSaga()]);
}
