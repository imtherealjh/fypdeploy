import "../css/appointmentlist.css";

export interface Appointment {
  id: number;
  patientName: string;
  doctorName: string;
  time: string;
}

interface AppointmentListProps {
  appointmentsList: Appointment[];
}

export default function AppointmentList({
  appointmentsList,
}: AppointmentListProps) {
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
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

AppointmentList;
