import Header from "components/Header";
import LoginGoogle from "components/LoginGoogle";
import LoginNaver from "components/LoginNaver";
import LoginKakao from "./../components/LoginKakao";
import LoginGithub from "./../components/LoginGithub";

const Login = () => {
  return (
    <>
      <Header title="Login" />
      <div className="mx-auto h-full w-full max-w-xl my-10">
        <div className="justify-center space-y-5 flex flex-col items-center">
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
