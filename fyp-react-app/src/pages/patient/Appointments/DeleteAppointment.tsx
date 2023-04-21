import { useEffect, useState } from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { useNavigate } from "react-router-dom";

interface Props {
  data: any;
}

export default function DeleteAppointmentComponent({ data }: Props) {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  let isMounted = false;
  const handleClick = async () => {
    if (!isMounted) {
      isMounted = true;

      try {
        await axiosPrivate.delete(
          `/patient/deleteAppointment?apptId=${data.appointmentId}`
        );

        alert("Appointment successfully removed...");
        document.getElementById("closeModalBtn")?.click();
        navigate(0);
      } catch (err: any) {
        if (!err?.response) {
          alert("No Server Response");
        } else if (err.response?.status === 400) {
          alert(err.response?.data.errors);
        } else {
          alert("Unknown error occured...");
        }
      }
    }

    isMounted = false;
  };

  return (
    <>
      <div>
        <h5>{`Removing appointment from doctor ${data.doctorName}`}</h5>
        <h5>{`Appointment Date: ${data.apptDate}, Appointment Time: ${data.apptTime}...`}</h5>
        <div className="d-grid mt-2">
          <button
            type="button"
            onClick={handleClick}
            className="btn btn-success btn-lg"
          >
            Submit
          </button>
        </div>
      </div>
    </>
  );
}
