import {
  pickOnceAsync,
  pickTenTimesAsync,
  PICK_ONCE,
  PICK_TEN_TIMES,
} from "./actions";
import { takeLeading } from "redux-saga/effects";
import { createEmptyAsyncSaga } from "lib/createAsyncSaga";
import { pickOnce, pickTenTimes } from "lib/api/cards";

const pickOnceSaga = createEmptyAsyncSaga(pickOnceAsync, pickOnce);

const pickTenTimesSaga = createEmptyAsyncSaga(pickTenTimesAsync, pickTenTimes);

export function* cardsSaga() {
  yield takeLeading(PICK_ONCE, pickOnceSaga);
  yield takeLeading(PICK_TEN_TIMES, pickTenTimesSaga);
}
