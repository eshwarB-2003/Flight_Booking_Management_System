import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import apiClient from "../../services/apiClient";
import { API_ENDPOINTS } from "../../services/apiEndpoints";

const AddAncillary = () => {
  const { bookingId } = useParams();
  const navigate = useNavigate();

  const [quantity, setQuantity] = useState(0);
  const [added, setAdded] = useState(false);
  const [loading, setLoading] = useState(false);

  const increase = () => setQuantity((prev) => prev + 1);

  const decrease = () => {
    if (quantity > 0) setQuantity((prev) => prev - 1);
  };

  const handleAddAncillary = async () => {
    if (quantity === 0) return;

    try {
      setLoading(true);

      await apiClient.post(
        API_ENDPOINTS.ANCILLARY.ADD(Number(bookingId)),
        {
          serviceName: "Extra Baggage",
          price: 50,
          quantity: quantity,
        }
      );

      setAdded(true);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleConfirm = async () => {
    try {
      await apiClient.put(
        API_ENDPOINTS.BOOKING.CONFIRM(Number(bookingId))
      );

      alert("Booking Confirmed");
      navigate("/");
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="card">
      <h2>Ancillary Services</h2>

      <p>Extra Baggage ($50 each)</p>

      {/* QUANTITY CONTROL */}
      <div className="quantity-control">
        <button onClick={decrease} className="qty-btn">−</button>
        <span className="qty-value">{quantity}</span>
        <button onClick={increase} className="qty-btn">+</button>
      </div>

      {/* ADD SERVICE */}
      <button
        className={`btn ${quantity === 0 ? "disabled-btn" : ""}`}
        onClick={handleAddAncillary}
        disabled={quantity === 0 || added || loading}
      >
        {added ? "Added ✓" : loading ? "Adding..." : "Add Service"}
      </button>

      {/* CONFIRM */}
      <button
        className={`btn ${
          !added && quantity > 0 ? "disabled-btn" : ""
        }`}
        onClick={handleConfirm}
        disabled={!added && quantity > 0}
      >
        Confirm & Pay
      </button>
    </div>
  );
};

export default AddAncillary;