import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import qs from "qs";
import { From, LoginParams } from "lib/api/auth";
import { RootState } from "modules";
import { useDispatch, useSelector } from "react-redux";
import { loginAsync } from "modules/auth";
import { setToken } from "lib/api/client";
import Header from "components/Header";

const Auth = () => {
  const navigate = useNavigate();
  const params = useParams();

  const { data, error } = useSelector(
    (state: RootState) => state.auth.loginResult
  );
  const dispatch = useDispatch();

  useEffect(() => {
    const uri =
      window.location.href.split("#")[1] ?? window.location.href.split("?")[1];

    const data = qs.parse(uri);
    data.from = params.from as From;

    const loginParams: LoginParams = {
      from: params.from as From,
      access_token: (data.access_token ?? data.code) as string,
    };

    dispatch(loginAsync.request(loginParams));
  }, [dispatch, params.from]);

  useEffect(() => {
    if (data?.success && data?.token) {
      localStorage.setItem("token", data.token);
      setToken(data.token);
    }
    (data || error) && navigate("/");
  }, [data, error, navigate]);

  return (
    <>
      <Header title="Pobaba" />
      <div className="absolute left-0 top-16 bottom-0 right-0 bg-slate-100 flex flex-col items-center justify-center -z-10 min-w-[520px]" />
    </>
  );
};

export default Auth;
