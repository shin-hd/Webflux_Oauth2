/// <reference types="react-scripts" />

declare namespace NodeJS {
  interface ProcessEnv {
    NODE_ENV: "development" | "production";
    REACT_APP_REDIRECT_URL: string;
    REACT_APP_NAVER_CLIENT_ID: string;
    REACT_APP_KAKAO_JAVASCRIPT_KEY: string;
    REACT_APP_GOOGLE_CLIENT_ID: string;
    REACT_APP_GOOGLE_CLIENT_SECRET: string;
    REACT_APP_GITHUB_CLIENT_ID: string;
    REACT_APP_GITHUB_CLIENT_SECRET: string;
    REACT_APP_API_BASE_URL: string;
  }
}
