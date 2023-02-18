import { Link } from "@mui/material";
import { googleSrcWithClientId } from "../lib/login/googleUtils";

const LoginGoogle = () => {
  return (
    <>
      <div className="flex">
        <div id="githubIdLogin">
          <Link href={googleSrcWithClientId}>구글 로그인</Link>
        </div>
      </div>
    </>
  );
};

export default LoginGoogle;
