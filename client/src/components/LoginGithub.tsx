import { Link } from "@mui/material";
import { githubSrcWithClientId } from "lib/login/githubUtils";

const LoginGithub = () => {
  return (
    <>
      <div className="flex">
        <div id="githubIdLogin">
          <Link href={githubSrcWithClientId}>깃허브 로그인</Link>
        </div>
      </div>
    </>
  );
};

export default LoginGithub;
