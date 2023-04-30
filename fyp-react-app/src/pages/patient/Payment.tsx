import React, { useState, useEffect } from "react";
import "../../css/patientbilling.css";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

function Payments() {
  const dummyData = [
    {
      invoiceId: "INV001",
      appointmentId: "APT001",
      doctor: "Dr. John Doe",
      date: "2023-01-15",
      amount: 150,
      status: "Paid",
    },
    {
      invoiceId: "INV002",
      appointmentId: "APT002",
      doctor: "Dr. Jane Smith",
      date: "2023-02-10",
      amount: 200,
      status: "Unpaid",
    },
  ];

  const [payments, setPayments] = useState(dummyData);

  // const axiosPrivate = useAxiosPrivate();

  // const fetchPayments = async () => {
  //   try {
  //     const response = await axiosPrivate.get("<your-backend-api-url>/payments");
  //     setPayments(response.data);
  //   } catch (error) {
  //     console.error("Error fetching payment data:", error);
  //   }
  // };

  // useEffect(() => {
  //   fetchPayments();
  // }, []);

  const handleDownload = (payment) => {
    console.log("Download payment data:", payment);
  };

  const handlePay = (payment) => {
    console.log("Pay for invoice:", payment.invoiceId);
  };

  return (
    <div className="payments">
      <h1>Payments</h1>
      <table>
        <thead>
          <tr>
            <th>Invoice ID</th>
            <th>Appointment ID</th>
            <th>Doctor</th>
            <th>Date</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {payments.map((payment) => (
            <tr key={payment.invoiceId}>
              <td>{payment.invoiceId}</td>
              <td>{payment.appointmentId}</td>
              <td>{payment.doctor}</td>
              <td>{payment.date}</td>
              <td>${payment.amount}</td>
              <td className={payment.status === "Paid" ? "paid" : "unpaid"}>
                {payment.status}
              </td>
              <td>
                <button
                  className="download-btn"
                  onClick={() => handleDownload(payment)}
                >
                  Download
                </button>
                {payment.status === "Unpaid" && (
                  <button
                    className="pay-btn"
                    onClick={() => handlePay(payment)}
                  >
                    Pay
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Payments;
