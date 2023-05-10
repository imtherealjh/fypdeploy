import { useState } from "react";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import { useNavigate } from "react-router-dom";

export default function SpecialtyPage() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [formData, setFormData] = useState<any>({});

  console.log(formData);

  const handleSubmit = async () => {
    try {
      await axiosPrivate.post(`/sysAdmin/createNewSpecialty`, formData);
      alert("Specialty created successfully");
      navigate(0);
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
      <h1>Create New Specialty</h1>
      <div>
        <div className="mb-3">
          <label htmlFor="newSpecialty" className="form-label">
            New Specialty
          </label>
          <input
            type="text"
            id="newSpecialty"
            name="specialty"
            className="form-control"
            placeholder="New Specialty"
            onChange={(e) =>
              setFormData((prev: any) => ({
                ...prev,
                [e.target.name]: e.target.value,
              }))
            }
          />
        </div>
        <button
          type="button"
          className="btn btn-primary"
          onClick={handleSubmit}
        >
          Submit
        </button>
      </div>
    </>
  );
}
