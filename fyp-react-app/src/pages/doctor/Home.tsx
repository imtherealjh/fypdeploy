import React from "react";
import PatientList from "../../components/PatientList";

import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";

function Dashboard() {
  const [value, setValue] = React.useState<any>(new Date());
  // Fetch data from the backend here
  const visitsToday = 10;

  const patientsList = [
    { id: 1, name: "John Doe", appointmentDateTime: "2023-04-19 10:00 AM" },
    { id: 2, name: "Jane Smith", appointmentDateTime: "2023-04-19 11:00 AM" },
    // Add more patients here
  ];

  console.log(value);

  return (
    <>
      <h1>Dashboard</h1>
      <div className="dashboard-container">
        <div className="d-flex flex-wrap">
          <div style={{ background: "#737373" }} className="col w-100">
            <span>Visits for Today: </span>
            <span>{visitsToday}</span>
          </div>
          <div className="col">
            <Calendar onChange={setValue} value={value} />
          </div>
        </div>

        <PatientList patients={patientsList} />
      </div>
    </>
  );
}

export default Dashboard;
