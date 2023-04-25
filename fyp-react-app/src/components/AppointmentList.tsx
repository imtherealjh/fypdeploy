import React from "react";
import "../css/appointmentlist.css";

export interface Appointment {
  id: number;
  patientName: string;
  doctorName: string;
  date: string;
  time: string;
  queue: number;
}

interface AppointmentListProps {
  appointmentsList: Appointment[];
  onAppointmentSelect: (appointmentId: number) => void; // Add this prop
}

const AppointmentList: React.FC<AppointmentListProps> = ({
  appointmentsList,
  onAppointmentSelect, // Add this prop
}) => {
  const handleCheckIn = (appointmentId: number) => {
    // Perform check-in logic here
    console.log("Checked in appointment:", appointmentId);
  };

  return (
    <div className="appointment-list">
      <h3>Today's Appointments:</h3>
      <table>
        <thead>
          <tr>
            <th>Patient Name</th>
            <th>Doctor</th>
            <th>Date</th>
            <th>Time</th>
            <th>Queue</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {appointmentsList.map((appointment) => (
            <tr
              key={appointment.id}
              onClick={() => onAppointmentSelect(appointment.id)} // Add onClick handler here
            >
              <td>{appointment.patientName}</td>
              <td>{appointment.doctorName}</td>
              <td>{appointment.date}</td>
              <td>{appointment.time}</td>
              <td>{appointment.queue}</td>
              <td>
                <button
                  onClick={(e) => {
                    e.stopPropagation(); // Prevent triggering onClick event of the row
                    handleCheckIn(appointment.id);
                  }}
                >
                  Check In
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AppointmentList;
