import Header from "components/Header";
import LoginGoogle from "components/auth/LoginGoogle";
import LoginNaver from "components/auth/LoginNaver";
import LoginKakao from "components/auth/LoginKakao";
import LoginGithub from "components/auth/LoginGithub";

const Login = () => {
  return (
    <>
      <Header title="Login" back />
      <div className="absolute left-0 top-16 bottom-0 right-0 bg-slate-100 flex flex-col items-center justify-center -z-10 min-w-[520px]">
        <div className="grid bg-white rounded-sm shadow-md p-8 space-y-6 place-items-center min-w-max">
          <LoginGithub />
          <LoginGoogle />
          <LoginNaver />
          <LoginKakao />
        </div>
      </div>
    </>
  );
};

export default Login;
