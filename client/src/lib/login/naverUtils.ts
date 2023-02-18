export const naverSrc =
  "https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js";

export const initializeNaverLogin = () => {
  const { naver } = window;
  const naverLogin = new naver.LoginWithNaverId({
    clientId: process.env.REACT_APP_NAVER_CLIENT_ID,
    callbackUrl: process.env.REACT_APP_REDIRECT_URL + "naver",
    isPopup: false,
    loginButton: { color: "green", type: 3, height: "60" },
  });
  naverLogin.init();
};
