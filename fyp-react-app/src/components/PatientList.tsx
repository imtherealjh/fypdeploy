import "../css/patientlist.css";

interface PatientListProps {
  apptDetails: any;
}

function PatientList({ apptDetails }: PatientListProps) {
  return (
    <div className="patients-list">
      <h3>List of patients for selected date:</h3>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Time</th>
          </tr>
        </thead>
        <tbody>
          {apptDetails.map((apptDetail: any) => (
            <tr key={apptDetail.patient.patientId}>
              <td>{apptDetail.patient.name}</td>
              <td>{apptDetail.apptTime}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PatientList;
