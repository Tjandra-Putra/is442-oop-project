import { BrowserRouter, Routes, Route } from "react-router-dom";
import axios from "axios";

// user routes
import {
  Navbar,
  Footer,
  Home,
  Login,
  Register,
  Dashboard,
  AppBarOffSet,
  CreatePortfolio,
  PortfolioDetailed,
} from "./routes/Routes";
import "./App.css";
import { useEffect } from "react";

function App() {
  useEffect(() => {
    console.log("AAXIOSS");
    axios
      .get("http://localhost:8080/api/user/getUser")
      .then((res) => {
        console.log(res.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <BrowserRouter>
      {/* Static content goes here */}
      <Navbar />
      <AppBarOffSet />

      {/* Dynamic content goes here */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/create-portfolio" element={<CreatePortfolio />} />
        <Route path="/portfolio/:id" element={<PortfolioDetailed />} />
        {/* <Route path="/stock/:id" element={<PortfolioDetailed />} /> */}
      </Routes>

      {/* Static content goes here */}
      <Footer />
    </BrowserRouter>
  );
}

export default App;
