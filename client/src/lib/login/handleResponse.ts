import { setToken } from "./../api/client";

export interface loginResponse {
  data?: {
    success: boolean;
    code: number;
    msg: string;
    token: string;
  };
  status: number;
  statusText: string;
}

const handleResponse = (res: loginResponse) => {
  console.log(res);
  if (res.status === 200) {
    window.localStorage.setItem("token", res.data?.token ?? "none");
    setToken();
  } else if (res.status === 400) {
    alert(
      "토큰 인증 과정에서 문제가 발생했습니다. 로그인 정보를 다시 확인해 주세요."
    );
  } else {
    alert("로그인 중 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.");
  }
};

export default handleResponse;
