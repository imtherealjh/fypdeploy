import React, { useEffect, useState } from "react";
import axios from "axios";

function ViewAllAppointments() {
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        const response = await axios.get("/api/endpoint-for-appointments");
        setAppointments(response.data);
      } catch (error) {
        console.error("Error fetching appointments:", error);
      }
    };

    fetchAppointments();
  }, []);

  const handleDeleteAppointment = async (appointmentId: string) => {
    if (
      window.confirm(
        "Are you sure you want to delete the appointment information?"
      )
    ) {
      try {
        await axios.delete(`/api/endpoint-for-appointments/${appointmentId}`);
        setAppointments(
          appointments.filter((appt: any) => appt.id !== appointmentId)
        );
      } catch (error) {
        console.error("Error deleting appointment:", error);
      }
    }
  };

  return (
    <div>
      <h1>View All Appointments</h1>
      <table>
        <thead>
          <tr>
            <th>Patient Name</th>
            <th>Appointment Date</th>
            <th>Appointment Slot</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {appointments.map((appointment: any) => (
            <tr key={appointment.id}>
              <td>{appointment.patientName}</td>
              <td>{appointment.date}</td>
              <td>{appointment.slot}</td>
              <td>
                <button onClick={() => handleDeleteAppointment(appointment.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ViewAllAppointments;
