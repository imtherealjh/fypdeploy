import { useState } from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import Step1 from "./AppointmentStep/Step1";
import Step2 from "./AppointmentStep/Step2";
import Step3 from "./AppointmentStep/Step3";
import { useNavigate } from "react-router-dom";

export default function BookAppointment() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState<any>({});

  let isMounted = false;
  const handleSubmit = async () => {
    if (step != 3) {
      setStep((prev) => prev + 1);
    } else {
      if (!isMounted) {
        isMounted = true;
        try {
          await axiosPrivate.post("/patient/bookAppointment", {
            apptId: formData.apptId,
          });
          alert("Appointment has been successfully created...");
          navigate(0);
        } catch (err: any) {
          if (!err?.response) {
            alert("No Server Response");
          } else if (err.response?.status === 400) {
            alert(err.response?.data.errors);
          } else {
            alert("Unknown error occured...");
          }
          console.log(err);
        }
        isMounted = false;
      }
    }
  };

  const conditionalComponent = () => {
    switch (step) {
      case 1:
        return <Step1 formData={formData} setFormData={setFormData} />;
      case 2:
        return <Step2 formData={formData} setFormData={setFormData} />;
      case 3:
        return <Step3 formData={formData} />;
      default:
        return <Step1 formData={formData} setFormData={setFormData} />;
    }
  };

  return (
    <>
      <h1>Appointment</h1>
      <div>
        <h4>Book appointment...</h4>
        <h6>Please fill up the form before continuing...</h6>
        {conditionalComponent()}
        <div className="d-grid gap-2 mt-3">
          {step > 1 && (
            <button
              onClick={() => setStep((prev) => prev - 1)}
              className="btn btn-danger"
            >
              Previous
            </button>
          )}
          <button onClick={handleSubmit} className="btn btn-success">
            {step != 3 ? "Next" : "Submit"}
          </button>
        </div>
      </div>
    </>
  );
}
