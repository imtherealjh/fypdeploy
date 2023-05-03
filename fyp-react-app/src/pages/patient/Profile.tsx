import { ChangeEvent, useEffect, useState } from "react";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import { useNavigate } from "react-router-dom";

export default function Profile() {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const [patientProfile, setPatientProfile] = useState<any>({});

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get("/patient/getPatientProfile", {
          signal: controller.signal,
        });

        isMounted && setPatientProfile(response.data);
      } catch (err: any) {
        console.error(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setPatientProfile((prev: any) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async () => {
    console.log(patientProfile);
    try {
      await axiosPrivate.put("/patient/updateProfile", patientProfile);
      alert("Patient profile updated!!");
      navigate(0);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      <h1> Profile Page</h1>
      <div className="d-flex flex-row flex-wrap gap-3">
        <div
          style={{ flex: "1 1 auto" }}
          className="d-flex flex-column align-self-center"
        >
          <img
            src="https://via.placeholder.com/200"
            alt="Profile Img"
            className="align-self-center rounded-circle"
          />
          <h5 className="align-self-center mt-2">{patientProfile.name}</h5>
        </div>

        <div
          className="p-3 w-100"
          style={{ flex: "1 1 65%", backgroundColor: "#DCDCDC" }}
        >
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="name"
              placeholder="name..."
              onChange={handleChange}
              value={patientProfile.name || ""}
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
              value={patientProfile.email || ""}
            />
            <label htmlFor="email">Email</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="number"
              className="form-control"
              name="contactNo"
              placeholder="contact..."
              onChange={handleChange}
              value={patientProfile.contactNo || ""}
            />
            <label htmlFor="contactNo">Contact No</label>
          </div>

          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="address"
              placeholder="address..."
              onChange={handleChange}
              value={patientProfile.address || ""}
            />
            <label htmlFor="address">Address</label>
          </div>

          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="emergencyContact"
              placeholder="emergencyContact..."
              onChange={handleChange}
              value={patientProfile.emergencyContact || ""}
            />
            <label htmlFor="emergencyContact">Emergency Contact</label>
          </div>

          <div className="form-floating mb-3">
            <input
              type="number"
              className="form-control"
              name="emergencyContactNo"
              placeholder="emergencyContactNo..."
              onChange={handleChange}
              value={patientProfile.emergencyContactNo || ""}
            />
            <label htmlFor="emergencyContactNo">Emergency Contact No</label>
          </div>

          <div className="d-grid">
            <button
              type="button"
              className="btn btn-primary"
              onClick={handleSubmit}
            >
              Update
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
