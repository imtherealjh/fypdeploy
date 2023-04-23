import { useState } from "react";
import AppointmentList from "../../components/AppointmentList";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

function ClerkHome() {
  const axiosPrivate = useAxiosPrivate();

  const dummyAppointments = [
    {
      id: 1,
      patientName: "John Doe",
      doctorName: "Dr. Smith",
      date: "19/04/2023",
      time: "10:00",
      queue: 1,
    },
    {
      id: 2,
      patientName: "Jane Doe",
      doctorName: "Dr. Johnson",
      date: "19/04/2023",
      time: "10:30",
      queue: 2,
    },
  ];

  return (
    <>
      <h1>Dashboard</h1>
      <div className="dashboard-container">
        <div className="mt-3">
          <AppointmentList appointmentsList={dummyAppointments} />
        </div>
      </div>
    </>
  );
}

export default ClerkHome;
