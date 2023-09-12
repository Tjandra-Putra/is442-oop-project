import { BrowserRouter, Routes, Route } from "react-router-dom";

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

function App() {
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
      </Routes>

      {/* Static content goes here */}
      <Footer />
    </BrowserRouter>
  );
}

export default App;
