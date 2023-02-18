import { Button } from "@mui/material";
import Header from "components/Header";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import qs from "qs";
import { From, loginGithub, loginWithToken } from "lib/api/auth";
import handleResponse from "lib/login/handleResponse";

const Home = () => {
  const navigate = useNavigate();
  const params = useParams();

  useEffect(() => {
    const uri =
      window.location.href.split("#")[1] ?? window.location.href.split("?")[1];

    const data = qs.parse(uri);

    if (data.access_token) {
      const from: From = (params.from as From) ?? undefined;
      if (from) loginWithToken(from, data).then((res) => handleResponse(res));
      navigate("/");
    }

    if (data.code) {
      loginGithub(data).then((res) => handleResponse(res));
      navigate("/");
    }
  }, [navigate, params?.from]);

  return (
    <>
      <Header title="Pobaba" login />
      <div className="mx-auto h-full w-full max-w-xl">
        <div className="justify-center content-center space-x-5 flex">
          <Button className="">1회 뽑기</Button>
          <Button>10회 뽑기</Button>
        </div>
      </div>
    </>
  );
};

export default Home;
