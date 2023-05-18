import { useEffect, useState } from "react";
import axios from "../api/axios";
import QueueStatus from "./QueueStatus";

interface Appointment {
  clinic: string;
  doctor: string;
  doctorId: number;
  date: string;
  time: string;
}

export default function Queue() {
  const [appointmentId, setAppointmentId] = useState(0);
  const [connected, setConnected] = useState(false);
  const [appointment, setAppointment] = useState<Appointment | null>(null);

  const handleConnect = async () => {
    try {
      const response = await axios.get(
        `/queue/getAppointmentDetails?apptId=${appointmentId}`
      );

      setAppointment(response.data);
      setConnected(true);
    } catch (err: any) {
      if (!err?.response) {
        alert("No Server Response");
      } else if (err.response?.status === 400) {
        alert(err.response?.data.errors);
      } else {
        alert("Unknown error");
      }
    }
  };

  return (
    <>
      <h1>Queue</h1>
      <div>
        {!connected && (
          <div className="input-group mb-3">
            <input
              type="number"
              className="form-control"
              name="appoinment-id"
              placeholder="Appointment Id"
              aria-label="appointment-id"
              aria-describedby="appointment-id"
              onChange={(e) => setAppointmentId(e.target.valueAsNumber)}
            />
            <button
              className="btn btn-outline-secondary"
              type="button"
              id="appointment-id"
              onClick={handleConnect}
            >
              Submit
            </button>
          </div>
        )}
        {connected && (
          <table className="table table-striped">
            <thead>
              <tr>
                <th>Clinic</th>
                <th>Doctor</th>
                <th>Time</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{appointment?.clinic || "No data available"}</td>
                <td>{appointment?.doctor || "No data available"}</td>
                <td>{appointment?.time || "No data available"}</td>
                <td></td>
              </tr>
            </tbody>
          </table>
        )}
        {connected && (
          <QueueStatus
            appointmentId={appointmentId}
            room={appointment!.doctorId}
          />
        )}
      </div>
    </>
  );
}
