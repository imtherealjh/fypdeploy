import "../css/patientlist.css";

interface PatientListProps {
  apptDetails: any;
}

function PatientList({ apptDetails }: PatientListProps) {
  console.log(apptDetails);
  return (
    <div className="patients-list">
      <h3>List of patients for selected date:</h3>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Time</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {apptDetails.map((apptDetail: any, idx: number) => (
            <tr key={idx}>
              <td>{apptDetail.patientName}</td>
              <td>{apptDetail.apptTime}</td>
              <td>{apptDetail.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PatientList;
