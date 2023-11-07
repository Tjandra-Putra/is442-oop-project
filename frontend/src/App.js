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
  ForgetPassword,
  ChangePassword,
} from "./routes/Routes";
import "./App.css";

function App() {
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
        <Route path="/forget-password" element={<ForgetPassword />} />
        <Route path="/change-password" element={<ChangePassword />} />
      </Routes>

      {/* Static content goes here */}
      <Footer />
    </BrowserRouter>
  );
}

export default App;
