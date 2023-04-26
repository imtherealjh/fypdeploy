import React, { useState } from "react";
import "../../css/payment.css";

function Payment() {
  const [patientName, setPatientName] = useState("");
  const [patientAddress, setPatientAddress] = useState("");
  const [amount, setAmount] = useState("");
  const [invoiceDescription, setInvoiceDescription] = useState("");

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log(
      "Submitted:",
      patientName,
      patientAddress,
      amount,
      invoiceDescription
    );
    // Send the data to the backend here
  };

  return (
    <div className="payments">
      <h1>Payments</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="patient-name">Patient Name:</label>
          <input
            id="patient-name"
            type="text"
            value={patientName}
            onChange={(e) => setPatientName(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="patient-address">Patient Address:</label>
          <input
            id="patient-address"
            type="text"
            value={patientAddress}
            onChange={(e) => setPatientAddress(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="amount">Amount:</label>
          <input
            id="amount"
            type="text"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="invoice-description">Invoice Description:</label>
          <textarea
            id="invoice-description"
            rows={4}
            value={invoiceDescription}
            onChange={(e) => setInvoiceDescription(e.target.value)}
          />
        </div>
        <button type="submit">Submit Bill</button>
      </form>
    </div>
  );
}

export default Payment;
