import { githubSrcWithClientId } from "lib/login/githubUtils";
import { Link } from "react-router-dom";

const LoginGithub = () => {
  return (
    <Link to={githubSrcWithClientId} id="githubIdLogin">
      <div className="flex items-center justify-center w-[186px] h-11 outline outline-gray-100 outline-1 shadow-sm rounded-sm shadow-slate-400 space-x-2">
        <img
          className="w-6"
          src={process.env.PUBLIC_URL + "/button/github-mark/github-mark.svg"}
          alt=""
        />
        <span className="text-gray-500 font-bold text-sm">
          Sign in with Github
        </span>
      </div>
    </Link>
  );
};

export default LoginGithub;
