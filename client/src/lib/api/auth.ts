import client from "./client";

export const login = async ({
  from,
  access_token,
}: LoginParams): Promise<LoginResult> => {
  const response = await client.post<LoginResult>(`/auth/login/${from}`, {
    access_token,
  });
  return response.data;
};

export type From = "google" | "github" | "naver" | "kakao" | undefined;

export interface LoginParams {
  from: From;
  access_token: string;
}

export interface LoginResult {
  success: boolean;
  code: number;
  msg: string;
  token: string;
}
