import { useLocation } from "react-router-dom";
import PatientDetails from "../../components/PatientDetails";

export default function ViewMedicalDetails() {
  const obj = useLocation()?.state;

  console.log(obj);

  const patientData = obj?.patientMedicalRecords;
  patientData.name = obj.name;
  patientData.contactNo = obj.contactNo;
  patientData.sex = obj.gender;
  patientData.dateOfBirth = obj.dob;
  patientData.patientId = obj.patientId;

  return (
    <>
      <h1>Patient - Profile Overview</h1>
      <div className="d-flex flex-column gap-2">
        <PatientDetails patient={patientData} editable={true} />
        <div>
          <h3>Appointment History</h3>
          {/* <table className="mt-2 table table-striped">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Patient Name</th>
                <th scope="col">Email</th>
                <th scope="col">Contact No</th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              {obj.patientAppt.length < 1 ? (
                <tr>
                  <td colSpan={6}>No data available....</td>
                </tr>
              ) : null}
              {obj.patientAppt.map((data: any, idx: number) => (
                <tr key={idx}>
                  <th className="align-middle" scope="row">
                    {idx + 1}
                  </th>
                  <td>{data.name}</td>
                  <td>{data.email}</td>
                  <td>{data.contactNo}</td>
                  <td></td>
                </tr>
              ))}
            </tbody>
          </table> */}
        </div>
      </div>
    </>
  );
}
