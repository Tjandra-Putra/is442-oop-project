import { BrowserRouter, Routes, Route } from "react-router-dom";
import axios from "axios";
import { Toaster } from "react-hot-toast";
import { useSelector } from "react-redux";
import ProtectedRoute from "./routes/ProtectedRoute";

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
  const { user, loading, error, isAuth } = useSelector((state) => state.userReducer);

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
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/create-portfolio"
          element={
            <ProtectedRoute>
              <CreatePortfolio />
            </ProtectedRoute>
          }
        />
        <Route
          path="/portfolio/:id"
          element={
            <ProtectedRoute>
              <PortfolioDetailed />
            </ProtectedRoute>
          }
        />
        <Route path="/forget-password" element={<ForgetPassword />} />
        <Route path="/change-password" element={<ChangePassword />} />
      </Routes>

      {/* Static content goes here */}
      <Footer />
    </BrowserRouter>
  );
}

export default App;
