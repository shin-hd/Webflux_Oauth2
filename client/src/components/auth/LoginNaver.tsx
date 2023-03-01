import { useScript } from "hooks/useScript";
import { useEffect } from "react";
import { naverSrc, initializeNaverLogin } from "lib/login/naverUtils";

const LoginNaver = () => {
  const status = useScript(naverSrc);

  useEffect(() => {
    if (status === "ready") initializeNaverLogin();
  }, [status]);

  return (
    <div className="w-[183px]">
      {status === "ready" && <div id="naverIdLogin" />}
    </div>
  );
};

export default LoginNaver;
