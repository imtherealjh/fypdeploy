interface Props {
  patient: any;
  handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  editable?: boolean;
}

function PatientDetails({ patient, handleChange, editable }: Partial<Props>) {
  if (!patient) {
    return <p>No patient selected</p>;
  }

  console.log(patient);

  return (
    <>
      <div className="patient-details">
        <h1>{patient.name}</h1>
        <div className="row g-3">
          <div className="col-md-4">
            <label htmlFor="contactNo" className="form-label fw-bold">
              Contact Number
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="contactNo"
              value={patient.contactNo}
              readOnly
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="sex" className="form-label fw-bold">
              Sex
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="sex"
              value={patient.sex}
              readOnly
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="dob" className="form-label fw-bold">
              Date of Birth
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="dob"
              value={patient.dateOfBirth}
              readOnly
            />
          </div>

          <div className="col-md-4">
            <label htmlFor="height" className="form-label fw-bold">
              Height (In Cm)
            </label>
            <input
              type="number"
              className={`form-control${!editable ? "-plaintext" : ""}`}
              id="height"
              name="height"
              readOnly={!editable}
              onChange={handleChange}
              value={`${patient.height || 0}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="weight" className="form-label fw-bold">
              Weight (In Kg)
            </label>
            <input
              type="number"
              className={`form-control${!editable ? "-plaintext" : ""}`}
              id="weight"
              name="weight"
              readOnly={!editable}
              onChange={handleChange}
              value={`${patient.weight || 0}`}
            />
          </div>

          <div className="col-md-4">
            <label htmlFor="hospitalizedHistory" className="form-label fw-bold">
              Hospitalized before
            </label>
            <input
              type="text"
              className={`form-control${!editable ? "-plaintext" : ""}`}
              id="hospitalizedHistory"
              name="hospitalizedHistory"
              readOnly={!editable}
              onChange={handleChange}
              value={`${patient.hospitalizedHistory || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="currentMedication" className="form-label fw-bold">
              Current Medication
            </label>
            <input
              type="text"
              className={`form-control${!editable ? "-plaintext" : ""}`}
              id="currentMedication"
              name="currentMedication"
              readOnly={!editable}
              onChange={handleChange}
              value={`${patient.currentMedication || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="foodAllergies" className="form-label fw-bold">
              Food Allergies
            </label>
            <input
              type="text"
              className={`form-control${!editable ? "-plaintext" : ""}`}
              id="foodAllergies"
              name="foodAllergies"
              readOnly={!editable}
              onChange={handleChange}
              value={`${patient.foodAllergies || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="drugAllergies" className="form-label fw-bold">
              Drug Allergies
            </label>
            <input
              type="text"
              className={`form-control${!editable ? "-plaintext" : ""}`}
              id="drugAllergies"
              name="drugAllergies"
              readOnly={!editable}
              onChange={handleChange}
              value={`${patient.drugAllergies || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="bloodType" className="form-label fw-bold">
              Blood Type
            </label>
            <input
              type="text"
              className={`form-control${!editable ? "-plaintext" : ""}`}
              id="bloodType"
              name="bloodType"
              readOnly={!editable}
              onChange={handleChange}
              value={`${patient.bloodType || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="medicalConditions" className="form-label fw-bold">
              Medical Conditions
            </label>
            <input
              type="text"
              className={`form-control${!editable ? "-plaintext" : ""}`}
              id="medicalConditions"
              name="medicalConditions"
              readOnly={!editable}
              onChange={handleChange}
              value={`${patient.medicalConditions || ""}`}
            />
          </div>

          <div className="col-md-4">
            <label htmlFor="emergencyContact" className="form-label fw-bold">
              Emergency Contact
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="emergencyContact"
              readOnly
              value={`${patient.emergencyContact || ""}`}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="emergencyContactNo" className="form-label fw-bold">
              Emergency Contact Number
            </label>
            <input
              type="text"
              className="form-control-plaintext"
              id="emergencyContactNo"
              readOnly
              value={`${patient.emergencyContactNo || ""}`}
            />
          </div>
        </div>
      </div>
    </>
  );
}

export default PatientDetails;
