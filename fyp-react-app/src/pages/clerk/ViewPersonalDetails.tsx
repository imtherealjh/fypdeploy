import { useLocation } from "react-router-dom";

export default function ViewPersonalDetails() {
  const obj = useLocation()?.state;

  const patientData = obj?.patientMedicalRecords;
  patientData.name = obj.name;
  patientData.contactNo = obj.contactNo;
  patientData.sex = obj.gender;
  patientData.dateOfBirth = obj.dob;
  patientData.patientId = obj.patientId;
  patientData.emergencyContact = obj.emergencyContact;
  patientData.emergencyContactNo = obj.emergencyContactNo;

  return (
    <>
      <h1>Patient - Profile Overview</h1>
      <div className="d-flex flex-column gap-2">
        <div
          style={{
            position: "relative",
            background: "white",
            padding: "1.2rem",
            borderRadius: "1.2rem",
          }}
        >
          <div className="patient-details">
            <h1>{patientData.name}</h1>
            <div className="row g-3">
              <div className="col-md-4">
                <label htmlFor="contactNo" className="form-label fw-bold">
                  Contact Number
                </label>
                <input
                  type="text"
                  className="form-control-plaintext"
                  id="contactNo"
                  value={patientData.contactNo}
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
                  value={patientData.sex}
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
                  value={patientData.dateOfBirth}
                  readOnly
                />
              </div>

              <div className="col-md-4">
                <label htmlFor="height" className="form-label fw-bold">
                  Height (In Cm)
                </label>
                <input
                  type="number"
                  className="form-control-plaintext"
                  id="height"
                  name="height"
                  readOnly
                  value={`${patientData.height || 0}`}
                />
              </div>
              <div className="col-md-4">
                <label htmlFor="weight" className="form-label fw-bold">
                  Weight (In Kg)
                </label>
                <input
                  type="number"
                  className="form-control-plaintext"
                  id="weight"
                  name="weight"
                  readOnly
                  value={`${patientData.weight || 0}`}
                />
              </div>

              <div className="col-md-4">
                <label
                  htmlFor="emergencyContact"
                  className="form-label fw-bold"
                >
                  Emergency Contact
                </label>
                <input
                  type="text"
                  className="form-control-plaintext"
                  id="emergencyContact"
                  readOnly
                  value={`${patientData.emergencyContact || ""}`}
                />
              </div>
              <div className="col-md-4">
                <label
                  htmlFor="emergencyContactNo"
                  className="form-label fw-bold"
                >
                  Emergency Contact Number
                </label>
                <input
                  type="text"
                  className="form-control-plaintext"
                  id="emergencyContactNo"
                  readOnly
                  value={`${patientData.emergencyContactNo || ""}`}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
