// src/pages/ClerkDashboard.tsx
import React from "react";
import AppointmentList from "../../components/AppointmentList";

function ClerkDashboard() {
  const appointmentsList = [
    {
      id: 1,
      patientName: "John Doe",
      doctorName: "Dr. Smith",
      date: "2023-04-20",
      time: "09:00",
      queue: 1,
    },
    {
      id: 2,
      patientName: "Jane Smith",
      doctorName: "Dr. Brown",
      date: "2023-04-20",
      time: "10:00",
      queue: 2,
    },
    // Add more appointments here
  ];

  return (
    <div>
      <h1>Today's Appointments</h1>
      <AppointmentList appointmentsList={appointmentsList} />
    </div>
  );
}

export default ClerkDashboard;
