import client from "./client";

export const pickOnce = async (): Promise<Card> => {
  const response = await client.post<Card>("/cards/once");
  return response.data;
};

export const pickTenTimes = async (): Promise<Card[]> => {
  const response = await client.post<Card[]>("/cards/ten-times");
  return response.data;
};

export type CardType = "Fire" | "Water" | "Air" | "Earth" | "Aether";

export interface Card {
  no: number;
  name: string;
  description: string;
  attack?: number;
  defense?: number;
  type: CardType;
  rank: number;
}
