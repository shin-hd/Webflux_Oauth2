import { loginWithToken } from "lib/api/auth";

export const kakaoSrc = "https://developers.kakao.com/sdk/js/kakao.js";

export const loginFormWithKakao = () => {
  window.Kakao.Auth.login({
    success(authObj: any) {
      if (authObj.access_token) {
        // window.localStorage.setItem(
        //   "token",
        //   (authObj.access_token as string) ?? "none"
        // );
        loginWithToken("kakao", authObj);
      }
      console.log(authObj);
      window.localStorage.setItem("token", authObj.access_token);
    },
    fail(err: any) {
      console.log(err);
    },
  });
};

export const logoutKakao = () => {
  const { localStorage } = window;
  localStorage.removeItem("token");
  window.Kakao.Auth.logout(() => console.log("카카오 로그아웃"));
};

export const initializeKakaoLogin = () => {
  try {
    if (!window.Kakao.isInitialized() && window.Kakao)
      window.Kakao.init(process.env.REACT_APP_KAKAO_JAVASCRIPT_KEY);
  } catch (e) {
    console.log(e);
  }
};
