import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import useAxiosPrivate from "../../lib/useAxiosPrivate";

import "../../css/feedback.css";
import useAuth from "../../lib/useAuth";

function Feedback() {
  const { auth } = useAuth();
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [feedback, setFeedback] = useState<any>({});

  const role = auth.role.toLowerCase();
  let navigateBack = "";
  if (role == "clinic_owner") navigateBack = "clinic";
  else if (role == "patient") navigateBack = "patient";
  else if (role == "doctor") navigateBack = "doctor";
  else if (role == "nurse") navigateBack = "nurse";
  else if (role == "front_desk") navigateBack = "clerk";

  let isMounted = true;
  let controller = new AbortController();
  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      isMounted &&
        (await axiosPrivate.post("/all/insertSystemFeedback", feedback, {
          signal: controller.signal,
        }));
      isMounted && alert("Feedback sent successfully");
      isMounted && setFeedback({});
      isMounted = false;
      navigate(`/${navigateBack}/system-feedback`, { replace: true });
    } catch (err: any) {
      if (!err?.response) {
        alert("No Server Response");
      } else if (err.response?.status === 400) {
        alert(err.response?.data.errors);
      } else {
        alert("Unknown error occured...");
      }
      console.error(err);
    }
  };

  return (
    <>
      <h1>Feedback</h1>
      <div className="feedback-container">
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="feedback">Your feedback:</label>
            <textarea
              id="feedback"
              name="feedback"
              rows={4}
              value={feedback.feedback}
              onChange={(event) =>
                setFeedback((prev: any) => ({
                  ...prev,
                  [event.target.name]: event.target.value,
                }))
              }
            />
          </div>
          <button type="submit">Submit Feedback</button>
        </form>
      </div>
    </>
  );
}

export default Feedback;
