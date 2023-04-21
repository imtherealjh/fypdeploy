import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../api/axios";
import "../css/register.css";

export default function RegisterClinic() {
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
      const response = await axios.post("/auth/registerClinic", inputs);
      if (JSON.parse(response.data) === true) {
        alert("Clinic successfully registered successfully");
        navigate("/", { replace: true });
      } else {
        console.log(response);
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      <div className="register d-flex flex-column align-items-center mx-4">
        <h1 className="my-0 w-100">
          <span>CREATE ACCOUNT</span>
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
              name="username"
              placeholder="username..."
              onChange={handleChange}
            />
            <label htmlFor="username">Username</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="password"
              className="form-control"
              name="password"
              placeholder="password..."
              onChange={handleChange}
            />
            <label htmlFor="password">Password</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="clinicName"
              placeholder="Clinic Name..."
              onChange={handleChange}
            />
            <label htmlFor="name">Clinic Name</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="contactName"
              placeholder="Contact Name..."
              onChange={handleChange}
            />
            <label htmlFor="name">Contact Name</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              name="email"
              placeholder="email..."
              onChange={handleChange}
            />
            <label htmlFor="email">Email</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="location"
              placeholder="location..."
              onChange={handleChange}
            />
            <label htmlFor="location">Location</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="openingHrs"
              placeholder="openingHrs..."
              onChange={handleChange}
            />
            <label htmlFor="openingHrs">Opening Hours</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="closingHrs"
              placeholder="closingHrs..."
              onChange={handleChange}
            />
            <label htmlFor="closingHrs">Closing Hours</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="apptDuration"
              placeholder="apptDuration..."
              onChange={handleChange}
            />
            <label htmlFor="apptDuration">Appointment Duration</label>
          </div>
          <div className="input-group mb-3">
            <input type="file" className="form-control" name="license" />
            <label className="input-group-text" htmlFor="license">
              Upload
            </label>
          </div>
          <div className="d-grid">
            <button
              type="submit"
              style={{
                backgroundColor: "#128983",
              }}
              className="btn btn-success btn-lg"
            >
              Register
            </button>
          </div>
          <div className="my-4">Already have an account? Login here!</div>
        </form>
      </div>
    </>
  );
}
