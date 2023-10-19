import { BrowserRouter, Routes, Route } from "react-router-dom";
import axios from "axios";
import { Toaster } from "react-hot-toast";

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
  const postData = {
    data: [
      {
        fieldName: "email",
        value: "ryan.gugu@gmail.com",
      },
      {
        fieldName: "password",
        value: "123456",
      },
      { fieldName: "username", value: "Ryan bapok" },
    ],
  };

  useEffect(() => {
    // get user data
    // axios
    //   .get("http://localhost:8080/api/user/getUser")
    //   .then((res) => {
    //     console.log(res.data);
    //   })
    //   .catch((error) => {
    //     console.error(error);
    //   });
    // console.log("POST REQUEST");
    // axios
    //   .post("http://localhost:8080/api/user/addUser2", postData)
    //   .then((res) => {
    //     console.log(res.data);
    //   })
    //   .catch((error) => {
    //     console.error(error);
    //   });
  }, []);

  return (
    <BrowserRouter>
      {/* Static content goes here */}
      <Navbar />
      <AppBarOffSet />
      <Toaster />

      {/* Dynamic content goes here */}
      <Routes>
        <Route path="/" element={<Login />} />
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
