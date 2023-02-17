import React from "react";
import { Helmet } from "react-helmet-async";
import { useNavigate } from "react-router-dom";

const NotFound = () => {
  const navigate = useNavigate();
  return (
    <>
      <Helmet>
        <title>Not found</title>
      </Helmet>
      <div className="fixed flex h-full w-full items-center justify-center bg-gray-100">
        <div className="flex w-full flex-col items-center justify-center text-center">
          <h1 className="text-3xl">404 Not Found</h1>
          <h3 className="mb-8">페이지가 존재하지 않습니다.</h3>
          <span
            className="cursor-pointer select-none"
            onClick={() => navigate(-1)}
          >
            뒤로가기
          </span>
        </div>
      </div>
    </>
  );
};

export default NotFound;
