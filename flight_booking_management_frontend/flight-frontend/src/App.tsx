import "./App.css";
import { BrowserRouter } from "react-router-dom";

import AppRoutes from "./routes/AppRoutes";
import Navbar from "./components/layout/Navbar";

function App() {
  return (
    <BrowserRouter>
      <div className="app-container">
        <Navbar />
        <div className="main-content">
          <AppRoutes />
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;