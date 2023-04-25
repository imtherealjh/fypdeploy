import React from "react";
import { useNavigate } from "react-router-dom";

function Appointments() {
  const navigate = useNavigate();

  const handleViewAppointments = () => {
    navigate("/appointments/view-all");
  };

  const handleUpdateAppointments = () => {
    navigate("/appointments/update");
  };

  return (
    <div>
      <h1>Appointments</h1>
      <button onClick={handleViewAppointments}>View All Appointments</button>
      <button onClick={handleUpdateAppointments}>Update Appointments</button>
    </div>
  );
}

export default Appointments;
