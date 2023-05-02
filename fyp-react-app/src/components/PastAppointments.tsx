import React, { useState, useEffect } from "react";
import "../css/pastappointments.css";
// import useAxiosPrivate from "../../hooks/useAxiosPrivate";

interface PastAppointment {
  id: string;
  clinic: string;
  doctor: string;
  date: string;
  time: string;
  detailsLink: string;
}

function PastAppointments() {
  const dummyData: PastAppointment[] = [
    {
      id: "1",
      clinic: "Singapore General Hospital",
      doctor: "Dr. John Doe",
      date: "2023-04-10",
      time: "09:30",
      detailsLink: "/details/1",
    },
    {
      id: "2",
      clinic: "Mount Elizabeth Hospital",
      doctor: "Dr. Jane Smith",
      date: "2023-04-15",
      time: "14:00",
      detailsLink: "/details/2",
    },
  ];

  const [pastAppointments, setPastAppointments] =
    useState<PastAppointment[]>(dummyData);
  // const axiosPrivate = useAxiosPrivate();

  // useEffect(() => {
  //   const fetchPastAppointments = async () => {
  //     try {
  //       const response = await axiosPrivate.get("<your-backend-api-url>/past-appointments");
  //       setPastAppointments(response.data);
  //     } catch (error) {
  //       console.error("Error fetching past appointments:", error);
  //     }
  //   };

  //   fetchPastAppointments();
  // }, []);

  return (
    <div className="past-appointments">
      <h2>Past Appointments</h2>
      <table>
        <thead>
          <tr>
            <th>Clinic</th>
            <th>Doctor</th>
            <th>Date</th>
            <th>Time</th>
            <th>Details</th>
          </tr>
        </thead>
        <tbody>
          {pastAppointments.map((appointment: PastAppointment) => (
            <tr key={appointment.id}>
              <td>{appointment.clinic}</td>
              <td>{appointment.doctor}</td>
              <td>{appointment.date}</td>
              <td>{appointment.time}</td>
              <td>
                <a href={appointment.detailsLink}>See More</a>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PastAppointments;
