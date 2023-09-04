import { BrowserRouter, Routes, Route } from "react-router-dom";

// user routes
import { Navbar, Footer, Home, Login, Register } from "./routes/Routes";

import "./App.css";

function App() {
  return (
    <BrowserRouter>
      {/* Static content goes here */}
      <Navbar />

      {/* Dynamic content goes here */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>

      {/* Static content goes here */}
      <Footer />
    </BrowserRouter>
  );
}

export default App;
