import { AxiosError } from "axios";
import { Card } from "lib/api/cards";
import { createAction, createAsyncAction } from "typesafe-actions";

export const INITIALIZE_LIST = "cards/INITIALIZE_LIST";

export const PICK_ONCE = "cards/PICK_ONCE";
export const PICK_ONCE_SUCCESS = "cards/PICK_ONCE_SUCCESS";
export const PICK_ONCE_ERROR = "cards/PICK_ONCE_ERROR";

export const PICK_TEN_TIMES = "cards/PICK_TEN_TIMES";
export const PICK_TEN_TIMES_SUCCESS = "cards/PICK_TEN_TIMES_SUCCESS";
export const PICK_TEN_TIMES_ERROR = "cards/PICK_TEN_TIMES_ERROR";

export const initializeList = createAction(INITIALIZE_LIST)();
export const pickOnceAsync = createAsyncAction(
  PICK_ONCE,
  PICK_ONCE_SUCCESS,
  PICK_ONCE_ERROR
)<undefined, Card, AxiosError>();
export const pickTenTimesAsync = createAsyncAction(
  PICK_TEN_TIMES,
  PICK_TEN_TIMES_SUCCESS,
  PICK_TEN_TIMES_ERROR
)<undefined, Card[], AxiosError>();
