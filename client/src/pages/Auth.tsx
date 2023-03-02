import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import qs from "qs";
import { From, LoginParams } from "lib/api/auth";
import { RootState } from "modules";
import { useDispatch, useSelector } from "react-redux";
import { loginAsync } from "modules/auth";
import { setToken } from "lib/api/client";

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

  return <></>;
};

export default Auth;
