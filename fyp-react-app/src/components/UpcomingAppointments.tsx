import React, { useState, useEffect } from "react";
import "../css/upcomingappointments.css";
// import useAxiosPrivate from "../../hooks/useAxiosPrivate";

interface Appointment {
  id: string;
  clinic: string;
  doctor: string;
  date: string;
  time: string;
}

function UpcomingAppointments() {
  const dummyData: Appointment[] = [
    {
      id: "1",
      clinic: "Singapore General Hospital",
      doctor: "Dr. John Doe",
      date: "2023-05-10",
      time: "09:30",
    },
    {
      id: "2",
      clinic: "Mount Elizabeth Hospital",
      doctor: "Dr. Jane Smith",
      date: "2023-05-15",
      time: "14:00",
    },
  ];

  const [appointments, setAppointments] = useState<Appointment[]>(dummyData);
  // const axiosPrivate = useAxiosPrivate();

  // useEffect(() => {
  //   const fetchAppointments = async () => {
  //     try {
  //       const response = await axiosPrivate.get("<your-backend-api-url>/upcoming-appointments");
  //       setAppointments(response.data);
  //     } catch (error) {
  //       console.error("Error fetching appointments:", error);
  //     }
  //   };

  //   fetchAppointments();
  // }, []);

  return (
    <div className="upcoming-appointments">
      <h2>Upcoming Appointments</h2>
      <table className="upcoming-appointments-table">
        <thead>
          <tr>
            <th>Clinic</th>
            <th>Doctor</th>
            <th>Date</th>
            <th>Time</th>
          </tr>
        </thead>
        <tbody>
          {appointments.map((appointment: Appointment) => (
            <tr key={appointment.id}>
              <td>{appointment.clinic}</td>
              <td>{appointment.doctor}</td>
              <td>{appointment.date}</td>
              <td>{appointment.time}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UpcomingAppointments;
