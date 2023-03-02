import NotFound from "pages/NotFound";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Auth from "./pages/Auth";

const App = () => {
  return (
    <div className="min-w-[520px]">
      <BrowserRouter>
        <Routes>
          <Route path="/" key="Home" element={<Home />} />
          <Route path="/login/:from" element={<Auth />} />
          <Route path="/login" element={<Login />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
};

export default App;
