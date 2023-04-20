import React from "react";
import "../../css/dashboard.css";
import Calendar from "../../components/Calendar";
import PatientList from "../../components/PatientList";

function Dashboard() {
  // Fetch data from the backend here
  const visitsToday = 10;
  const newPatients = 5;
  const oldPatients = 25;

  const patientsList = [
    { id: 1, name: "John Doe", appointmentDateTime: "2023-04-19 10:00 AM" },
    { id: 2, name: "Jane Smith", appointmentDateTime: "2023-04-19 11:00 AM" },
    // Add more patients here
  ];

  return (
    <div className="dashboard-container">
      <div className="calendar">
        <Calendar />
      </div>
      <div className="dashboard-stats">
        <div className="visits-today">Visits for Today: {visitsToday}</div>
        <div className="new-patients">New Patients: {newPatients}</div>
        <div className="old-patients">Old Patients: {oldPatients}</div>
      </div>
      <PatientList patients={patientsList} />
    </div>
  );
}

export default Dashboard;
