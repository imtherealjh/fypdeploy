import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import axios from "../api/axios";
import { Stomp } from "@stomp/stompjs";

interface Props {
  appointmentId: number | null;
  room: number | null;
}

export default function QueueStatus({ appointmentId, room }: Props) {
  const [stompClient, setStompClient] = useState<any>(null);
  const [time, setTime] = useState<any>(null);
  const [queueStatus, setQueueStatus] = useState<any>(null);

  const fetchQueueStatus = async () => {
    try {
      const response = await axios.get(
        `/queue/getCurrentQueueStatus?apptId=${appointmentId}`
      );
      setQueueStatus(response.data);
    } catch (error) {
      console.error("Error fetching queue status:", error);
    }
  };

  const connect = () => {
    const socket = new SockJS(import.meta.env.VITE_SERVER_URL + "ws");
    const stompClient = Stomp.over(socket);
    setStompClient(stompClient);
    stompClient.connect({}, () => {
      fetchQueueStatus();
      stompClient.subscribe(`/queue/${room}`, () => {
        fetchQueueStatus();
      });
    });
  };

  useEffect(() => {
    const interval = setInterval(() => {
      var dt = new Date();
      var hours = dt.getHours(); // gives the value in 24 hours format
      var AmOrPm = hours >= 12 ? "pm" : "am";
      hours = hours % 12 || 12;
      var minutes =
        dt.getMinutes() < 10 ? `0${dt.getMinutes()}` : dt.getMinutes();
      var finalTime = hours + ":" + minutes + " " + AmOrPm;
      setTime(finalTime);
    }, 1000);
    connect();

    return () => {
      clearInterval(interval);
      stompClient && stompClient.disconnect();
    };
  }, []);

  return (
    <>
      <div>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Queue Status For Appointment ID</th>
            </tr>
            <tr>
              <td>{`#${appointmentId}`}</td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th>As At</th>
              <th>Current Queue Number</th>
            </tr>
            <tr>
              <td>{time || "-"}</td>
              <td>
                {queueStatus?.currentServingAppt !== undefined
                  ? `#${queueStatus?.currentServingAppt}`
                  : "-"}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
}
