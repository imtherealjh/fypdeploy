import { ChangeEvent, useState } from "react";
import "../css/patientdetails.css";

interface Props {
  patient: any;
  editable?: boolean;
}

function PatientDetails({ patient, editable }: Partial<Props>) {
  if (!patient) {
    return <p>No patient selected</p>;
  }

  const [formData, setFormData] = useState(patient);
  const [edit, setEdit] = useState(false);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFormData((prev: any) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  return (
    <div className="patient-details">
      <div className="d-flex justify-content-between">
        <h1>{formData.name}</h1>
        {editable && formData == patient && (
          <div className="d-block">
            <button className="btn btn-success" onClick={() => setEdit(!edit)}>
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
            Height
          </label>
          <input
            type="text"
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
            Weight
          </label>
          <input
            type="text"
            className={`form-control${!edit ? "-plaintext" : ""}`}
            id="weight"
            name="weight"
            readOnly={!edit}
            onChange={handleChange}
            value={`${formData.weight || ""}`}
          />
        </div>
        {editable && (
          <>
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
          </>
        )}

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
          <label htmlFor="emergencyContactNumber" className="form-label">
            Emergency Contact Number
          </label>
          <input
            type="text"
            className="form-control-plaintext"
            id="emergencyContactNumber"
            readOnly
            value={`${formData.emergencyContactNumber || ""}`}
          />
        </div>
      </div>
      {formData != patient && (
        <div className="d-flex justify-content-end gap-2">
          <button
            className="btn btn-danger"
            onClick={() => {
              setEdit(false);
              setFormData(patient);
            }}
          >
            Cancel
          </button>
          <button className="btn btn-primary">Save changes</button>
        </div>
      )}
    </div>
  );
}

export default PatientDetails;
