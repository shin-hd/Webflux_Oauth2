import { Button, Card, Paper } from "@mui/material";
import Header from "components/Header";
import { useEffect } from "react";
import { RootState } from "modules";
import { useDispatch, useSelector } from "react-redux";
import { getProfileAsync, initializeProfile } from "modules/user";
import { deleteToken } from "lib/api/client";
import {
  initializeList,
  pickOnceAsync,
  pickTenTimesAsync,
} from "./../modules/cards/actions";
import PickResult from "components/PickResult";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const profile = useSelector((state: RootState) => state.user.profile);
  const cardList = useSelector((state: RootState) => state.cards.cardList);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const onLogout = () => {
    deleteToken();
    dispatch(initializeProfile());
  };

  useEffect(() => {
    if (!profile.data && !profile.error && !profile.loading)
      dispatch(getProfileAsync.request());
  }, [dispatch, profile.data, profile.error, profile.loading]);

  useEffect(() => {
    if (profile.error) deleteToken();
  }, [profile.error]);

  useEffect(() => {
    if (cardList.error) {
      window.alert("뽑기 오류!!");
    }
  }, [cardList.error]);

  return (
    <>
      <Header title="Pobaba" showButton profile={profile} onLogout={onLogout} />
      <div className="absolute left-0 top-16 bottom-0 right-0 bg-slate-100 flex flex-col items-center justify-center -z-10 min-w-[520px]">
        <div className="grid bg-white rounded-sm shadow-md p-8 space-y-16 place-items-center min-w-max">
          {cardList.data || cardList.loading ? (
            <PickResult cardList={cardList} />
          ) : (
            <div className="flex flex-col space-y-6">
              <div className="grid grid-flow-row rounded-xl shadow-lg">
                <span className="text-md text-slate-800 px-4 py-1 bg-orange-200 font-bold rounded-t-lg">
                  픽업 이벤트
                </span>
                <div className="text-xl bg-yellow-50 p-4 rounded-b-lg">
                  <span className="font-semibold text-amber-500">⨺뿔⨺</span>
                  <span> 획득 확률 UP!!</span>
                </div>
              </div>
              <div className="grid grid-flow-row rounded-xl shadow-lg">
                <span className="text-md text-slate-800 px-4 py-1 bg-purple-200 font-bold rounded-t-lg">
                  확률 공지
                </span>
                <div className="grid grid-flow-row text-xl bg-violet-50 p-4 rounded-b-lg space-y-2">
                  <div className="grid grid-flow-col content-between">
                    <span>픽업 확률</span>
                    <span className="text-right">1.5%</span>
                  </div>
                  <div className="grid grid-flow-col content-between">
                    <span>5랭크 확률</span>
                    <span className="text-right">3%</span>
                  </div>
                  <div className="grid grid-flow-col content-between">
                    <span>4랭크 확률</span>
                    <span className="text-right">5%</span>
                  </div>
                </div>
              </div>
            </div>
          )}
          {profile.data ? (
            <div className="flex flex-row space-x-10">
              {cardList.data ? (
                <Button
                  className="w-24"
                  variant="contained"
                  color="secondary"
                  onClick={() => dispatch(initializeList())}
                >
                  확인
                </Button>
              ) : (
                <Button
                  className="w-24"
                  variant="contained"
                  color="secondary"
                  onClick={() => dispatch(pickOnceAsync.request())}
                >
                  1회 뽑기
                </Button>
              )}
              <Button
                className="w-24"
                variant="contained"
                color="secondary"
                onClick={() => dispatch(pickTenTimesAsync.request())}
              >
                10회 뽑기
              </Button>
            </div>
          ) : (
            <Button
              fullWidth
              variant="contained"
              color="secondary"
              onClick={() => {
                dispatch(initializeList());
                navigate("/login");
              }}
            >
              로그인
            </Button>
          )}
        </div>
      </div>
    </>
  );
};

export default Home;
