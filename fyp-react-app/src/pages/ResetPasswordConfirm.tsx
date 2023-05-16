import { useState } from "react";
import axios from "../api/axios";
import { useNavigate, useParams } from "react-router-dom";

export default function ResetPasswordConfirm() {
  const navigate = useNavigate();
  const { code } = useParams();
  const [formData, setFormData] = useState({ code: code });

  const handleSubmit = async () => {
    try {
      await axios.post("/auth/resetConfirm", formData);
      alert("Password have been resetted successfully");
      navigate("/");
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
      <div className="d-flex flex-column">
        <div className="row align-items-center justify-content-center g-0">
          <div className="col-12 col-md-8 col-lg-4">
            <div className="card shadow-sm">
              <div className="card-body">
                <div className="mb-3">
                  <label htmlFor="password" className="form-label">
                    Password
                  </label>
                  <input
                    type="password"
                    id="password"
                    className="form-control"
                    name="password"
                    placeholder="Enter Your Password"
                    onChange={(e) =>
                      setFormData((prev) => ({
                        ...prev,
                        [e.target.name]: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="cfmPassword" className="form-label">
                    Confirm Password
                  </label>
                  <input
                    type="password"
                    id="cfmPassword"
                    className="form-control"
                    name="cfmPassword"
                    placeholder="Enter Your Confirmed Password"
                    onChange={(e) =>
                      setFormData((prev) => ({
                        ...prev,
                        [e.target.name]: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="mb-3 d-grid">
                  <button
                    type="submit"
                    className="btn btn-primary"
                    onClick={handleSubmit}
                  >
                    Reset Password
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
