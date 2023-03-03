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
        showButton
        loading={loading}
        profile={data}
        onLogout={onLogout}
      />
      <div className="absolute left-0 top-16 bottom-0 right-0 bg-slate-100 flex flex-col items-center justify-center -z-10 min-w-[520px]">
        <div className="grid bg-white rounded-sm shadow-md p-8 space-y-6 place-items-center min-w-max">
          <div className="flex flex-row space-x-10">
            <Button variant="contained" color="secondary">
              1회 뽑기
            </Button>
            <Button variant="contained" color="secondary">
              10회 뽑기
            </Button>
          </div>
        </div>
      </div>
    </>
  );
};

export default Home;
