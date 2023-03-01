import { login } from "lib/api/auth";
import handleResponse from "lib/login/handleResponse";

export const kakaoSrc = "https://developers.kakao.com/sdk/js/kakao.js";

export const loginFormWithKakao = () => {
  window.Kakao.Auth.login({
    success(authObj: any) {
      if (authObj.access_token) {
        login({
          from: "kakao",
          access_token: authObj.access_token,
        }).then((res: any) => handleResponse(res));
      }
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
