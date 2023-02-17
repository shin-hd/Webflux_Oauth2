import { useScript } from "hooks/useScript";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  kakaoSrc,
  initializeKakaoLogin,
  loginFormWithKakao,
} from "utils/login/kakaoUtils";

const LoginKakao = () => {
  const navigate = useNavigate();
  const status = useScript(kakaoSrc);

  useEffect(() => {
    if (status === "ready") initializeKakaoLogin();
  }, [status]);

  return (
    <>
      <div className="flex">
        {status === "ready" && (
          <div
            id="kakaoIdLogin"
            className="hover:cursor-pointer"
            onClick={() => {
              loginFormWithKakao();
              navigate("/");
            }}
          >
            <img
              src="https://developers.kakao.com/tool/resource/static/img/button/login/full/ko/kakao_login_medium_narrow.png"
              alt="카카오톡 로그인"
            />
          </div>
        )}
      </div>
    </>
  );
};

export default LoginKakao;
