import client from "./client";

export const getMyProfile = async (): Promise<UserProfile> => {
  const response = await client.get<UserProfile>("/users/me");
  return response.data;
};

export type LoginType = "NORMAL" | "GOOGLE" | "GITHUB" | "NAVER" | "KAKAO";

export interface UserProfile {
  id: string;
  email?: string;
  name: string;
  picture?: string;
  oauthId?: string;
  type: LoginType;
  roles: Array<string>;
  locked: boolean;
}
