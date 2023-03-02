import { Button } from "@mui/material";
import Header from "components/Header";
import { useEffect } from "react";
import { RootState } from "modules";
import { useDispatch, useSelector } from "react-redux";
import { getProfileAsync, initializeProfile } from "modules/user";
import { deleteToken } from "lib/api/client";

const Home = () => {
  const { data, loading, error } = useSelector(
    (state: RootState) => state.user.profile
  );
  const dispatch = useDispatch();

  const onLogout = () => {
    deleteToken();
    dispatch(initializeProfile());
  };

  useEffect(() => {
    if (!data && !error && !loading) dispatch(getProfileAsync.request());
  }, [data, dispatch, error, loading]);

  useEffect(() => {
    if (error) deleteToken();
  }, [error]);

  return (
    <>
      <Header
        title="Pobaba"
        loading={loading}
        profile={data}
        onLogout={onLogout}
      />
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
