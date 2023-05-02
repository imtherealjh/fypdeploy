import { ChangeEvent, useEffect, useState } from "react";
import useAxiosPrivate from "../lib/useAxiosPrivate";

import "../css/patientdetails.css";
import { useNavigate } from "react-router-dom";

interface Props {
  patient: any;
  editable?: boolean;
}

function PatientDetails({ patient, editable }: Partial<Props>) {
  if (!patient) {
    return <p>No patient selected</p>;
  }

  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const [formData, setFormData] = useState<any>(patient);
  const [edit, setEdit] = useState(false);

  useEffect(() => {
    setEdit(formData != patient);
  }, [formData]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFormData((prev: any) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async () => {
    try {
      await axiosPrivate.put("/staff/updateMedicalRecords", formData);

      document.getElementById("closeModalBtn")?.click();

      alert("Medical Record updated successfully");
      navigate(-1);
    } catch (err: any) {
      if (!err?.response) {
        alert("No Server Response");
      } else if (err.response?.status === 400) {
        alert(err.response?.data.errors);
      } else {
        alert("Unknown error occured...");
      }
      console.log(err);
    }
  };

  return (
    <>
      <div className="patient-details">
        <div className="d-flex justify-content-between">
          <h1>{formData.name}</h1>
          {editable && !edit && (
            <div className="d-block">
              <button
                className="btn btn-success"
                onClick={() => setEdit(!edit)}
              >
                Edit
              </button>
            </div>
          )}
        </div>
        <div className="row g-3">
          <div className="col-md-4">
            <label htmlFor="contactNo" className="form-label">
              Contact Number
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="contactNo"
              value={formData.contactNo}
              readOnly
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="sex" className="form-label">
              Sex
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="sex"
              value={formData.sex}
              readOnly
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="dob" className="form-label">
              Date of Birth
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="dob"
              value={formData.dateOfBirth}
              readOnly
            />
          </div>

          <div className="col-md-4">
            <label htmlFor="height" className="form-label">
              Height (In Cm)
            </label>
            <input
              type="number"
              className={`form-control${!edit ? "-plaintext" : ""}`}
              id="height"
              name="height"
              readOnly={!edit}
              onChange={handleChange}
              value={`${formData.height || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="weight" className="form-label">
              Weight (In Kg)
            </label>
            <input
              type="number"
              className={`form-control${!edit ? "-plaintext" : ""}`}
              id="weight"
              name="weight"
              readOnly={!edit}
              onChange={handleChange}
              value={`${formData.weight || ""}`}
            />
          </div>

          <div className="col-md-4">
            <label htmlFor="hospitalizedHistory" className="form-label">
              Hospitalized before
            </label>
            <input
              type="text"
              className={`form-control${!edit ? "-plaintext" : ""}`}
              id="hospitalizedHistory"
              name="hospitalizedHistory"
              readOnly={!edit}
              onChange={handleChange}
              value={`${formData.hospitalizedHistory || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="currentMedication" className="form-label">
              Current Medication
            </label>
            <input
              type="text"
              className={`form-control${!edit ? "-plaintext" : ""}`}
              id="currentMedication"
              name="currentMedication"
              readOnly={!edit}
              onChange={handleChange}
              value={`${formData.currentMedication || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="foodAllergies" className="form-label">
              Food Allergies
            </label>
            <input
              type="text"
              className={`form-control${!edit ? "-plaintext" : ""}`}
              id="foodAllergies"
              name="foodAllergies"
              readOnly={!edit}
              onChange={handleChange}
              value={`${formData.foodAllergies || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="drugAllergies" className="form-label">
              Drug Allergies
            </label>
            <input
              type="text"
              className={`form-control${!edit ? "-plaintext" : ""}`}
              id="drugAllergies"
              name="drugAllergies"
              readOnly={!edit}
              onChange={handleChange}
              value={`${formData.drugAllergies || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="bloodType" className="form-label">
              Blood Type
            </label>
            <input
              type="text"
              className={`form-control${!edit ? "-plaintext" : ""}`}
              id="bloodType"
              name="bloodType"
              readOnly={!edit}
              onChange={handleChange}
              value={`${formData.bloodType || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="medicalConditions" className="form-label">
              Medical Conditions
            </label>
            <input
              type="text"
              className={`form-control${!edit ? "-plaintext" : ""}`}
              id="medicalConditions"
              name="medicalConditions"
              readOnly={!edit}
              onChange={handleChange}
              value={`${formData.medicalConditions || ""}`}
            />
          </div>

          <div className="col-md-4">
            <label htmlFor="emergencyContact" className="form-label">
              Emergency Contact
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="emergencyContact"
              readOnly
              value={`${formData.emergencyContact || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="emergencyContactNo" className="form-label">
              Emergency Contact Number
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="emergencyContactNo"
              readOnly
              value={`${formData.emergencyContactNo || ""}`}
            />
          </div>
        </div>
        <div className="d-flex justify-content-end gap-2">
          {editable && edit && (
            <button
              className="btn btn-danger"
              onClick={() => {
                setEdit(false);
                setFormData(patient);
              }}
            >
              Cancel
            </button>
          )}
          {editable && (
            <button
              className="btn btn-primary"
              data-bs-toggle="modal"
              data-bs-target="#modal"
            >
              Complete Appointment
            </button>
          )}
        </div>
      </div>
      <div
        className="modal fade"
        id="modal"
        data-bs-keyboard="false"
        tabIndex={-1}
        aria-labelledby="modal"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content py-3">
            <div className="modal-header">
              <h5 className="modal-title">Update appointment diagnosis</h5>
              <button
                id="closeModalBtn"
                style={{ display: "none" }}
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              <div className="form-floating mb-3">
                <select
                  style={{ paddingTop: 0, paddingBottom: 0 }}
                  name="appointmentId"
                  className="form-select"
                  defaultValue={"DEFAULT"}
                  aria-label="Select appointment..."
                  onChange={(e: ChangeEvent<HTMLSelectElement>) => {
                    const selectedIndex = e.target.options.selectedIndex;
                    formData.diagnostic =
                      formData?.currentAppt[selectedIndex - 1]?.diagnostic;
                    setFormData((prev: any) => ({
                      ...prev,
                      [e.target.name]: e.target.value,
                    }));
                  }}
                >
                  <option value={"DEFAULT"} disabled>
                    Select appointments...
                  </option>
                  {formData.currentAppt.map((data: any, idx: any) => (
                    <option
                      key={idx}
                      value={data.appointmentId}
                    >{`${data.apptDate} - ${data.apptTime}`}</option>
                  ))}
                </select>
              </div>

              <div className="form-floating mb-3">
                <input
                  type="text"
                  className={`form-control`}
                  id="diagnostic"
                  name="diagnostic"
                  onChange={handleChange}
                  value={`${formData.diagnostic || ""}`}
                />
                <label htmlFor="diagnostic">Diagnostic</label>
              </div>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
              <button
                type="button"
                className="btn btn-primary"
                onClick={handleSubmit}
              >
                Save changes
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default PatientDetails;
