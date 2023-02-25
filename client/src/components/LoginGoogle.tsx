import { Link } from "react-router-dom";
import { googleSrcWithClientId } from "../lib/login/googleUtils";

const LoginGoogle = () => {
  return (
    <div className="flex items-center justify-center h-10" id="googleIdLogin">
      <Link to={googleSrcWithClientId}>
        <img
          src={
            process.env.PUBLIC_URL +
            "/button/google/btn_google_signin_light_normal_web.png"
          }
          alt="구글 로그인"
        />
      </Link>
    </div>
  );
};

export default LoginGoogle;
