import { useNavigate } from "react-router-dom";
import { ROUTES } from "../../routes/routeConstants";

const Navbar = () => {
  const navigate = useNavigate();

  return (
    <nav className="navbar">
      {/* LEFT */}
      <div className="logo" onClick={() => navigate(ROUTES.HOME)}>
        ✈ FlightBook
      </div>

      {/* RIGHT */}
      <div className="nav-right">
        <button
          className="nav-link"
          onClick={() => navigate(ROUTES.HOME)}
        >
          Home
        </button>

        <button
          className="nav-link"
          onClick={() => navigate(ROUTES.SEARCH_FLIGHTS)}
        >
          Search Flights
        </button>

        <button
          className="nav-link"
          onClick={() => navigate(ROUTES.LOGIN)}
        >
          Login
        </button>
      </div>
    </nav>
  );
};

export default Navbar;