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

  let isMounted = false;
  const handleClick = async () => {
    if (!isMounted) {
      isMounted = true;

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
    }

    isMounted = false;
  };

  return (
    <>
      <div>
        <h5>{`Updating appointment from doctor ${data.doctorName}`}</h5>
        <h5>{`Appointment Date: ${data.apptDate}, Appointment Time: ${data.apptTime}...`}</h5>
        <h6>New appointment</h6>
        <Step2 formData={formData} setFormData={setFormData} />
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
