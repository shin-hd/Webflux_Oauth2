import Header from "components/Header";
import LoginGoogle from "components/LoginGoogle";
import LoginNaver from "components/LoginNaver";
import LoginKakao from "./../components/LoginKakao";
import LoginGithub from "./../components/LoginGithub";

const Login = () => {
  return (
    <>
      <Header title="Login" back />
      <div className="absolute left-0 top-16 bottom-0 right-0 bg-slate-100 flex flex-col items-center justify-center -z-10">
        <div className="grid bg-white rounded-sm shadow-md p-8 space-y-6 place-items-center">
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
