import React from "react";
import "../css/patientlist.css";

interface Patient {
  id: number;
  name: string;
  appointmentDateTime: string;
}

interface PatientListProps {
  patients: Patient[];
}

function PatientList({ patients }: PatientListProps) {
  return (
    <div className="patients-list">
      <h3>List of patients for today:</h3>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Date & Time</th>
          </tr>
        </thead>
        <tbody>
          {patients.map((patient) => (
            <tr key={patient.id}>
              <td>{patient.name}</td>
              <td>{patient.appointmentDateTime}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PatientList;
