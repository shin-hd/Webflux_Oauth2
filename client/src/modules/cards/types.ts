import { Card } from "lib/api/cards";
import { AsyncState } from "lib/reducerUtils";
import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type CardsAction = ActionType<typeof actions>;

export type CardsState = {
  cardList: AsyncState<Card[], Error>;
};
