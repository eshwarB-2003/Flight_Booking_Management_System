import { useState } from "react";
import type { ChangeEvent, FormEvent } from "react";
import "./PaymentPage.css";

type FormErrors = {
  passengerName?: string;
  passengerEmail?: string;
  paymentMethod?: string;
  cardNumber?: string;
  expiryDate?: string;
  cvv?: string;
  upiId?: string;
  bank?: string;
};

function PaymentPage() {
  const [paymentDetails, setPaymentDetails] = useState({
    cardNumber: "",
    expiryDate: "",
    cvv: "",
    paymentMethod: "",
    passengerName: "",
    passengerEmail: "",
    amount: 1000,
    upiId: "",
    bank: "",
  });

  const [formErrors, setFormErrors] = useState<FormErrors>({});
  const [isSubmitted, setIsSubmitted] = useState(false);

  const formatCardNumber = (cardNumber: string) => {
    return cardNumber
      .replace(/\D/g, "")
      .replace(/(\d{4})(?=\d)/g, "$1 ")
      .slice(0, 19);
  };

  const formatExpiryDate = (expiryDate: string) => {
    return expiryDate
      .replace(/\D/g, "")
      .replace(/(\d{2})(\d{0,2})/, (_, mm, yy) =>
        yy ? `${mm}/${yy}` : mm
      )
      .slice(0, 5);
  };

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;

    if (name === "cardNumber") {
      setPaymentDetails((prev) => ({
        ...prev,
        cardNumber: formatCardNumber(value),
      }));
    } else if (name === "expiryDate") {
      setPaymentDetails((prev) => ({
        ...prev,
        expiryDate: formatExpiryDate(value),
      }));
    } else if (name === "cvv") {
      if (/^\d*$/.test(value)) {
        setPaymentDetails((prev) => ({
          ...prev,
          cvv: value.slice(0, 3),
        }));
      }
    } else {
      setPaymentDetails((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsSubmitted(true);

    const errors: FormErrors = {};
    let isValid = true;

    if (!paymentDetails.passengerName.trim()) {
      errors.passengerName = "Passenger name is required";
      isValid = false;
    }

    if (
      !paymentDetails.passengerEmail ||
      !/\S+@\S+\.\S+/.test(paymentDetails.passengerEmail)
    ) {
      errors.passengerEmail = "Valid email is required";
      isValid = false;
    }

    if (!paymentDetails.paymentMethod) {
      errors.paymentMethod = "Please select a payment method";
      isValid = false;
    }

    if (paymentDetails.paymentMethod === "Card") {
      if (
        paymentDetails.cardNumber.replace(/\D/g, "").length !== 16
      ) {
        errors.cardNumber = "Card number must be 16 digits";
        isValid = false;
      }

      if (
        !/^(0[1-9]|1[0-2])\/\d{2}$/.test(paymentDetails.expiryDate)
      ) {
        errors.expiryDate = "Invalid expiry date (MM/YY)";
        isValid = false;
      }

      if (paymentDetails.cvv.length !== 3) {
        errors.cvv = "CVV must be 3 digits";
        isValid = false;
      }
    }

    if (paymentDetails.paymentMethod === "UPI") {
      if (
        !/^[\w.-]+@[\w.-]+$/.test(paymentDetails.upiId)
      ) {
        errors.upiId = "Invalid UPI ID";
        isValid = false;
      }
    }

    if (paymentDetails.paymentMethod === "Net Banking") {
      if (!paymentDetails.bank) {
        errors.bank = "Select a bank";
        isValid = false;
      }
    }

    setFormErrors(errors);

    if (isValid) {
      console.log("Payment Data:", paymentDetails);
      alert("Payment successful!");
    }
  };

  return (
    <div className="payment-container">
      <h1>Flight Booking Payment</h1>

      <div className="booking-summary">
        <h2>Booking Summary</h2>
        <p><strong>Total Amount:</strong> ${paymentDetails.amount}</p>
      </div>

      <form onSubmit={handleSubmit}>
        <h2>Passenger Information</h2>

        <label>Passenger Name:</label>
        <input
          type="text"
          name="passengerName"
          value={paymentDetails.passengerName}
          onChange={handleChange}
        />
        {isSubmitted && formErrors.passengerName && (
          <span className="error">{formErrors.passengerName}</span>
        )}

        <label>Email:</label>
        <input
          type="email"
          name="passengerEmail"
          value={paymentDetails.passengerEmail}
          onChange={handleChange}
        />
        {isSubmitted && formErrors.passengerEmail && (
          <span className="error">{formErrors.passengerEmail}</span>
        )}

        <h2>Select Payment Method</h2>
        <select
          name="paymentMethod"
          value={paymentDetails.paymentMethod}
          onChange={handleChange}
        >
          <option value="">Select Payment Method</option>
          <option value="Card">Card</option>
          <option value="Net Banking">Net Banking</option>
          <option value="UPI">UPI</option>
        </select>

        {isSubmitted && formErrors.paymentMethod && (
          <span className="error">{formErrors.paymentMethod}</span>
        )}

        {paymentDetails.paymentMethod === "Card" && (
          <>
            <h2>Card Information</h2>

            <label>Card Number:</label>
            <input
              type="text"
              name="cardNumber"
              value={paymentDetails.cardNumber}
              onChange={handleChange}
              placeholder="Enter card number"
            />
            {isSubmitted && formErrors.cardNumber && (
              <span className="error">{formErrors.cardNumber}</span>
            )}

            <label>Expiry Date (MM/YY):</label>
            <input
              type="text"
              name="expiryDate"
              value={paymentDetails.expiryDate}
              onChange={handleChange}
              placeholder="MM/YY"
            />
            {isSubmitted && formErrors.expiryDate && (
              <span className="error">{formErrors.expiryDate}</span>
            )}

            <label>CVV:</label>
            <input
              type="text"
              name="cvv"
              value={paymentDetails.cvv}
              onChange={handleChange}
              placeholder="CVV"
            />
            {isSubmitted && formErrors.cvv && (
              <span className="error">{formErrors.cvv}</span>
            )}
          </>
        )}

        {paymentDetails.paymentMethod === "Net Banking" && (
          <>
            <h2>Select Bank</h2>
            <select name="bank" value={paymentDetails.bank} onChange={handleChange}>
              <option value="">Select Bank</option>
              <option value="HDFC">HDFC</option>
              <option value="ICICI">ICICI</option>
              <option value="Axis">Axis</option>
              <option value="AIB">AIB</option>
              <option value="BOI">Bank of Ireland</option>
            </select>
            {isSubmitted && formErrors.bank && (
              <span className="error">{formErrors.bank}</span>
            )}
          </>
        )}

        {paymentDetails.paymentMethod === "UPI" && (
          <>
            <h2>Enter UPI ID</h2>
            <input
              type="text"
              name="upiId"
              value={paymentDetails.upiId}
              onChange={handleChange}
              placeholder="example@upi"
            />
            {isSubmitted && formErrors.upiId && (
              <span className="error">{formErrors.upiId}</span>
            )}
          </>
        )}

        <button type="submit">Complete Payment</button>
      </form>
    </div>
  );
}

export default PaymentPage;