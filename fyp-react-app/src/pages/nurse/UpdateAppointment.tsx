import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../css/register.css";

export default function UpdateAppointment() {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      await axios.post("/auth/updateAppointment", inputs, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      alert("Appointment successfully updated");
      navigate("/", { replace: true });
    } catch (err: any) {
      if (!err?.response) {
        alert("No Server Response");
      } else if (err.response?.status === 400) {
        console.log(err);
        alert(err.response?.data.errors);
      } else {
        console.log(err);
        alert("Unknown error");
      }
    }
  };

  return (
    <>
      <div className="register d-flex flex-column align-items-center mx-4">
        <h1 className="my-0 w-100">
          <span>UPDATE APPOINTMENT</span>
        </h1>
        <form
          className="d-flex flex-column align-items-center w-100 pt-4"
          method="POST"
          onSubmit={handleSubmit}
        >
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="description"
              placeholder="description..."
              onChange={handleChange}
            />
            <label htmlFor="description">Description</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="date"
              className="form-control"
              name="apptDate"
              placeholder="date..."
              onChange={handleChange}
            />
            <label htmlFor="apptDate">Appointment Date</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="time"
              className="form-control"
              name="apptTime"
              placeholder="time..."
              onChange={handleChange}
            />
            <label htmlFor="name">Appointment Time</label>
          </div>
          <div className="input-group mb-3">
          </div>
          <div className="d-grid">
            <button
              type="submit"
              style={{
                backgroundColor: "#128983",
              }}
              className="btn btn-success btn-lg"
            >
              Update
            </button>
          </div>
          <div className="my-4">Already have an account? Login here!</div>
        </form>
      </div>
    </>
  );
}
