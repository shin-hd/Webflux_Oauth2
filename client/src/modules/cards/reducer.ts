import { CardsAction, CardsState } from "./types";
import { createReducer } from "typesafe-actions";
import {
  INITIALIZE_LIST,
  PICK_ONCE,
  PICK_ONCE_ERROR,
  PICK_ONCE_SUCCESS,
  PICK_TEN_TIMES,
  PICK_TEN_TIMES_ERROR,
  PICK_TEN_TIMES_SUCCESS,
} from "./actions";
import { asyncState } from "lib/reducerUtils";

const initialState: CardsState = {
  cardList: asyncState.initial(),
};

const cards = createReducer<CardsState, CardsAction>(initialState, {
  [INITIALIZE_LIST]: (state) => ({
    ...state,
    cardList: asyncState.initial(),
  }),
  [PICK_ONCE]: (state) => ({
    ...state,
    cardList: asyncState.load(),
  }),
  [PICK_ONCE_SUCCESS]: (state, action) => ({
    ...state,
    cardList: {
      loading: false,
      data: [action.payload],
      error: null,
    },
  }),
  [PICK_ONCE_ERROR]: (state, action) => ({
    ...state,
    cardList: asyncState.error(action.payload),
  }),
  [PICK_TEN_TIMES]: (state: CardsState) => ({
    ...state,
    cardList: asyncState.load(),
  }),
  [PICK_TEN_TIMES_SUCCESS]: (state, action) => ({
    ...state,
    cardList: asyncState.success(action.payload),
  }),
  [PICK_TEN_TIMES_ERROR]: (state, action) => ({
    ...state,
    cardList: asyncState.error(action.payload),
  }),
});

export default cards;
