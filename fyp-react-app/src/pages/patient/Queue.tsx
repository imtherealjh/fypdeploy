import React, { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

interface Appointment {
  clinic: string;
  doctor: string;
  date: string;
  time: string;
  queueNumber: string | null;
}

interface QueueStatus {
  asAt: string;
  currentQueueNumber: string;
  userQueueNumber: string | null;
}

export default function Queue() {
  const axiosPrivate = useAxiosPrivate();
  const [appointment, setAppointment] = useState<Appointment | null>(null);
  const [queueStatus, setQueueStatus] = useState<QueueStatus | null>(null);

  useEffect(() => {
    fetchAppointmentDetails();
    fetchQueueStatus();
  }, []);

  const fetchAppointmentDetails = async () => {
    try {
      const userId = "some-user-id"; // have to replace this with the information used to identify the user
      const response = await axiosPrivate.get(`/appointments/${userId}`); // have to replace this with the correct backend call
      setAppointment(response.data);
    } catch (error) {
      console.error("Error fetching appointment details:", error);
    }
  };

  const fetchQueueStatus = async () => {
    try {
      const userId = "some-user-id"; // have to replace this with the information used to identify the user
      const response = await axiosPrivate.get(`/queue-status/${userId}`); // have to replace this with the correct backend call
      setQueueStatus(response.data);
    } catch (error) {
      console.error("Error fetching queue status:", error);
    }
  };

  const handleGetQueue = async () => {
    try {
      const userId = "some-user-id"; // have to replace this with the information used to identify the user
      const response = await axiosPrivate.post(`/queue/${userId}`); // have to replace this with the correct backend call

      if (appointment) {
        setAppointment({
          ...appointment,
          queueNumber: response.data.queueNumber,
        });
      }

      if (queueStatus) {
        setQueueStatus({
          ...queueStatus,
          userQueueNumber: response.data.queueNumber,
        });
      }
    } catch (error) {
      console.error("Error getting queue number:", error);
    }
  };

  return (
    <>
      <h1>Queue</h1>
      <div>
        <table>
          <thead>
            <tr>
              <th>Clinic</th>
              <th>Doctor</th>
              <th>Date</th>
              <th>Time</th>
              <th>Queue Number</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{appointment?.clinic || "No data available"}</td>
              <td>{appointment?.doctor || "No data available"}</td>
              <td>{appointment?.date || "No data available"}</td>
              <td>{appointment?.time || "No data available"}</td>
              <td>
                {appointment ? (
                  appointment.queueNumber ? (
                    appointment.queueNumber
                  ) : (
                    <button onClick={handleGetQueue}>Get Queue</button>
                  )
                ) : (
                  "No data available"
                )}
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div>
        <table>
          <thead>
            <tr>
              <th>Queue Status For Appointment ID</th>
            </tr>
            <tr>
              <td>{queueStatus?.userQueueNumber || "-"}</td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th>As At</th>
              <th>Current Queue Number</th>
            </tr>
            <tr>
              <td>{queueStatus?.asAt || "-"}</td>
              <td>{queueStatus?.currentQueueNumber || "-"}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
}
