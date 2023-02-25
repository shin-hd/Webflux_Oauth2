import client from "./client";

export type From = "google" | "github" | "naver" | "kakao" | undefined;

const defaultHandler = (error: any) => error.response;

export const loginWithToken = (from: From, { access_token }: any) =>
  client.post(`/auth/login/${from}`, { access_token }).catch(defaultHandler);

export const loginGithub = ({ code }: any) =>
  client.post("/auth/login/github", { code });
