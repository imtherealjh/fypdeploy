import { Link } from "react-router-dom";
import "../css/patientlist.css";
import useAuth from "../lib/useAuth";

interface PatientListProps {
  editable: any;
  apptDetails: any;
}

function PatientList({ editable, apptDetails }: Partial<PatientListProps>) {
  const { auth } = useAuth();

  return (
    <div className="patients-list">
      <h3>List of patients for selected date:</h3>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Time</th>
            <th>Status</th>
            {editable && <th>Action</th>}
          </tr>
        </thead>
        <tbody>
          {apptDetails.map((apptDetail: any, idx: number) => (
            <tr key={idx}>
              <td>
                {auth.role === "DOCTOR"
                  ? apptDetail.patient.name
                  : apptDetail.patientName}
              </td>
              <td>{apptDetail.apptTime}</td>
              <td>{apptDetail.status}</td>
              {editable && (
                <td>
                  <Link to="patients/details" state={apptDetail.patient}>
                    <button type="button" className="btn btn-primary">
                      Edit
                    </button>
                  </Link>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PatientList;
