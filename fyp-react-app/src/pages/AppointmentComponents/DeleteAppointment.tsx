import useAuth from "../../lib/useAuth";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import { useNavigate } from "react-router-dom";

interface Props {
  data: any;
}

export default function DeleteAppointmentComponent({ data }: Props) {
  const navigate = useNavigate();
  const { auth } = useAuth();
  const axiosPrivate = useAxiosPrivate();

  const handleClick = async () => {
    try {
      const path = auth.role === "PATIENT" ? "patient" : "staff";
      await axiosPrivate.delete(
        `/${path}/deleteAppointment?apptId=${data.appointmentId}`
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
  };

  return (
    <>
      <div>
        <h6>{`Removing appointment from doctor ${data.doctorName}`}</h6>
        <h6>{`Appointment Date: ${data.apptDate}, Appointment Time: ${data.apptTime}...`}</h6>
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
