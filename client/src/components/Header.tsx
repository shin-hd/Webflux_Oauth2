import Typography from "@mui/material/Typography";
import { Helmet } from "react-helmet-async";
import "@fontsource/roboto/700.css";
import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";

interface HeaderProps {
  title: string;
  login?: boolean;
  back?: boolean;
}

const Header = ({ title, login, back }: HeaderProps) => {
  const navigate = useNavigate();
  return (
    <>
      <Helmet>
        <title>{title} | Pobaba</title>
      </Helmet>
      <div className="top-0 w-full flex flex-row shadow-xl h-16 bg-purple-100 text-center z-30">
        <div className="grid basis-1/4 content-center justify-center">
          {back ? (
            <button onClick={() => navigate(-1)}>
              <svg
                className="h-6 w-6"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M15 19l-7-7 7-7"
                ></path>
              </svg>
            </button>
          ) : (
            <div />
          )}
        </div>
        <div className="flex basis-1/2 content-center justify-center">
          <Typography
            className="text-purple-800 hover:cursor-pointer"
            variant="h3"
            component="h1"
            onClick={() => navigate("/")}
          >
            {title}
          </Typography>
        </div>
        <div className="grid basis-1/4 content-center justify-center">
          {login && (
            <Button size="small" onClick={() => navigate("/login")}>
              로그인
            </Button>
          )}
        </div>
      </div>
    </>
  );
};

export default Header;
