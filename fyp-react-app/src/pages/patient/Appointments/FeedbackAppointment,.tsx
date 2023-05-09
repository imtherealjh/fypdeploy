import { ChangeEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import useAxiosPrivate from "../../../lib/useAxiosPrivate";

interface Props {
  data: any;
}

export default function FeedbackComponent({ data }: Props) {
  const [formData, setFormData] = useState<any>({
    appointmentId: data.appointmentId,
    clinicRatings: data.fPC?.ratings ?? "",
    clinicFeedback: data.fPC?.feedback ?? "",
    doctorRatings: data.fPD?.ratings ?? "",
    doctorFeedback: data.fPD?.feedback ?? "",
  });
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  console.log(data);

  const createdDate = new Date(data.fPC?.localDateTime);
  const hrsDiff =
    (new Date().getTime() - createdDate.getTime()) / 1000 / 60 / 60;

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setFormData((prev: any) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleClick = async () => {
    try {
      await axiosPrivate.post("/patient/insertFeedback", formData);

      alert("Feedback successfully submitted...");
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
        <h6>{`Appointment Date: ${data.apptDate}, Appointment Time: ${data.apptTime}...`}</h6>

        <h6>{`Feedback on ${data.clinicName}`}</h6>
        <div className="mb-3">
          <label htmlFor="clinicRatings" className="form-label">
            Ratings (1 - 5)
          </label>
          <input
            type="number"
            className={`form-control${hrsDiff >= 24 ? "-plaintext" : ""}`}
            id="clinicRatings"
            name="clinicRatings"
            placeholder="1-5"
            min={1}
            max={5}
            onChange={handleChange}
            readOnly={hrsDiff >= 24}
            value={formData.clinicRatings}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="clinicFeedback" className="form-label">
            Feedback
          </label>
          <textarea
            className={`form-control${hrsDiff >= 24 ? "-plaintext" : ""}`}
            id="clinicFeedback"
            name="clinicFeedback"
            rows={4}
            onChange={handleChange}
            readOnly={hrsDiff >= 24}
            value={formData.clinicFeedback}
          />
        </div>

        <h6>{`Feedback on ${data.doctorName}`}</h6>
        <div className="mb-3">
          <label htmlFor="doctorRatings" className="form-label">
            Ratings (1 - 5)
          </label>
          <input
            type="number"
            className={`form-control${hrsDiff >= 24 ? "-plaintext" : ""}`}
            id="doctorRatings"
            name="doctorRatings"
            placeholder="1-5"
            min={1}
            max={5}
            onChange={handleChange}
            readOnly={hrsDiff >= 24}
            value={formData.doctorRatings}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="doctorFeedback" className="form-label">
            Feedback
          </label>
          <textarea
            className={`form-control${hrsDiff >= 24 ? "-plaintext" : ""}`}
            id="doctorFeedback"
            name="doctorFeedback"
            rows={4}
            onChange={handleChange}
            readOnly={hrsDiff >= 24}
            value={formData.doctorFeedback}
          />
        </div>

        <div className="d-grid mt-2">
          {data.fPC == null && data.fPD == null && (
            <button
              type="button"
              onClick={handleClick}
              className="btn btn-success btn-lg"
            >
              Submit
            </button>
          )}
          {hrsDiff < 24 && (
            <button
              type="button"
              onClick={handleClick}
              className="btn btn-success btn-lg"
            >
              Update Feedback
            </button>
          )}
        </div>
      </div>
    </>
  );
}
