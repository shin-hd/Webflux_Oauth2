import { call, put } from "redux-saga/effects";
import { AsyncActionCreatorBuilder, PayloadAction } from "typesafe-actions";

type PromiseCreatorFunction<P, T> =
  | ((payload: P) => Promise<T>)
  | (() => Promise<T>);

const isPayloadAction = <P>(action: any): action is PayloadAction<string, P> =>
  action.payload !== undefined;

export default function createAsyncSaga<T1, P1, T2, P2, T3, P3>(
  asyncActionCreator: AsyncActionCreatorBuilder<
    // [액션, 패이로드] or [액션, [패이로드, 메타]]
    [T1, [P1, undefined]],
    [T2, [P2, undefined]],
    [T3, [P3, undefined]]
  >,
  promiseCreator: PromiseCreatorFunction<P1, P2>
) {
  return function* saga(action: ReturnType<typeof asyncActionCreator.request>) {
    try {
      const result: P2 = isPayloadAction<P1>(action)
        ? yield call(promiseCreator, action.payload)
        : yield call(promiseCreator);
      yield put(asyncActionCreator.success(result));
    } catch (e) {
      yield put(asyncActionCreator.failure(e as P3));
    }
  };
}

export function createEmptyAsyncSaga<T1, T2, P2, T3, P3>(
  asyncActionCreator: AsyncActionCreatorBuilder<
    // [액션, 패이로드] or [액션, [패이로드, 메타]]
    [T1, [undefined, undefined]],
    [T2, [P2, undefined]],
    [T3, [P3, undefined]]
  >,
  promiseCreator: PromiseCreatorFunction<undefined, P2>
) {
  return function* saga() {
    try {
      const result: P2 = yield call(promiseCreator);
      yield put(asyncActionCreator.success(result));
    } catch (e) {
      yield put(asyncActionCreator.failure(e as P3));
    }
  };
}
