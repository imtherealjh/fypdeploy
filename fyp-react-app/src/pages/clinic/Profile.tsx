import { ChangeEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useAxiosPrivate from "../../lib/useAxiosPrivate";

export default function Profile() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [clinicProfile, setClinicProfile] = useState<any>({});

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get("/clinicOwner/getProfile", {
          signal: controller.signal,
        });

        console.log(response.data);

        isMounted && setClinicProfile(response.data);
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
    setClinicProfile((prev: any) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async () => {
    try {
      await axiosPrivate.put("/clinicOwner/updateProfile", clinicProfile);
      alert("Clinic profile updated!!");
      navigate(0);
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
          <h5 className="align-self-center mt-2">{clinicProfile.clinicName}</h5>
        </div>

        <div
          className="p-3 w-100"
          style={{ flex: "1 1 65%", backgroundColor: "#DCDCDC" }}
        >
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="contactName"
              placeholder="Contact Name..."
              onChange={handleChange}
              value={clinicProfile.contactName || ""}
            />
            <label htmlFor="name">Contact Name</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="number"
              className="form-control"
              name="contactNo"
              placeholder="contact..."
              onChange={handleChange}
              value={clinicProfile.contactNo || ""}
            />
            <label htmlFor="contactNo">Contact No</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              name="email"
              placeholder="email..."
              onChange={handleChange}
              value={clinicProfile.email || ""}
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
              value={clinicProfile.location || ""}
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
              value={clinicProfile.openingHrs || ""}
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
              value={clinicProfile.closingHrs || ""}
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
              value={clinicProfile.apptDuration || ""}
            />
            <label htmlFor="apptDuration">Appointment Duration</label>
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
