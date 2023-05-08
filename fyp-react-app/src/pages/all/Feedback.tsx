import React, { useState } from "react";
import "../../css/feedback.css";

function Feedback() {
  const [feedback, setFeedback] = useState("");

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log("Submitted feedback:", feedback);
    // You can send the feedback to the backend here
    setFeedback("");
  };

  const handleFeedbackChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setFeedback(event.target.value);
  };

  return (
    <>
    <h1>Feedback</h1>
    <div className="feedback-container">
      <h2>Feedback</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="feedback">Your feedback:</label>
          <textarea
            id="feedback"
            name="feedback"
            rows={4}
            value={feedback}
            onChange={handleFeedbackChange}
          />
        </div>
        <button type="submit">Submit Feedback</button>
      </form>
    </div>
    </>
  );
}

export default Feedback;
