import { useEffect, useState } from "react";
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
}

export default function AppointmentList({
  appointmentsList,
}: AppointmentListProps) {
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
            <th>Time</th>
            <th>Status</th>
            {new Date().toDateString() ===
              new Date(appointmentsList[0]?.date).toDateString() && (
              <>
                <th>Queue</th>
                <th>Action</th>
              </>
            )}
          </tr>
        </thead>
        <tbody>
          {appointmentsList.length < 1 && (
            <tr>
              <td colSpan={6}>No data available...</td>
            </tr>
          )}
          {appointmentsList.map((appointment: any, idx: number) => (
            <tr key={idx}>
              <td>{appointment.patientName}</td>
              <td>{appointment.doctorName}</td>
              <td>{appointment.apptTime}</td>
              <td>{appointment.status}</td>
              {new Date().toDateString() ===
                new Date(appointmentsList[0]?.date).toDateString() && (
                <>
                  <td>{appointment.queue}</td>
                  <td>
                    {appointment.status === "BOOKED" && (
                      <button
                        onClick={(e) => {
                          e.stopPropagation();
                          handleCheckIn(appointment.id);
                        }}
                      >
                        Check In
                      </button>
                    )}
                  </td>
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

AppointmentList;
