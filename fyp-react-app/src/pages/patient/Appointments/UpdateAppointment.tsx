import { useEffect, useState } from "react";
import Step2 from "./AppointmentStep/Step2";
import useAxiosPrivate from "../../../lib/useAxiosPrivate";
import { useNavigate } from "react-router-dom";

interface Props {
  data: any;
}

export default function UpdateAppointmentComponent({ data }: Props) {
  const [formData, setFormData] = useState<any>({});
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    setFormData((prev: any) => {
      return {
        ...prev,
        doctorId: data.doctorId,
      };
    });
  }, []);

  const handleClick = async () => {
    try {
      await axiosPrivate.put("/patient/updateAppointment", {
        originalApptId: data.appointmentId,
        apptId: formData.apptId,
      });

      alert("Appointment successfully updated...");
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
        <h6>{`Updating appointment from doctor ${data.doctorName}`}</h6>
        <h6>{`Appointment Date: ${data.apptDate}, Appointment Time: ${data.apptTime}...`}</h6>
        <br />
        <h6>New appointment</h6>
        <div className="d-block">
          <Step2 formData={formData} setFormData={setFormData} />
        </div>
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
