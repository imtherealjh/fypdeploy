import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../api/axios";
import "../css/register.css";

export default function RegisterPatient() {
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
      await axios.post("/auth/registerPatient", inputs);
      alert("Patient successfully registered successfully");
      navigate("/", { replace: true });
    } catch (err: any) {
      if (!err?.response) {
        alert("No Server Response");
      } else if (err.response?.status === 400) {
        alert(err.response?.data.errors);
      } else {
        alert("Unknown error");
      }
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
              name="name"
              placeholder="name..."
              onChange={handleChange}
            />
            <label htmlFor="name">Name</label>
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
              name="address"
              placeholder="address..."
              onChange={handleChange}
            />
            <label htmlFor="address">Address</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="number"
              className="form-control"
              name="contact"
              placeholder="contact..."
              onChange={handleChange}
            />
            <label htmlFor="contact">Contact</label>
          </div>
          <select
            name="gender"
            className="form-select mb-3"
            defaultValue={"DEFAULT"}
            aria-label="Select gender"
            onChange={(e: ChangeEvent<HTMLSelectElement>) => {
              setInputs((prev) => {
                return { ...prev, [e.target.name]: e.target.value };
              });
            }}
          >
            <option value={"DEFAULT"} disabled>
              Select gender
            </option>
            <option value="M">Male</option>
            <option value="F">Female</option>
          </select>

          <div className="form-floating mb-3">
            <input
              type="date"
              className="form-control"
              name="dob"
              placeholder="date..."
              onChange={handleChange}
            />
            <label htmlFor="date">Date Of Birth</label>
          </div>

          <div className="d-grid">
            <button
              type="submit"
              style={{ backgroundColor: "#128983" }}
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
