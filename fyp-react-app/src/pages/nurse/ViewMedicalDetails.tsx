import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import PatientDetails from "../../components/PatientDetails";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import axios from "axios";

export default function ViewMedicalDetails() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [apptData, setApptData] = useState([]);
  const [verify, setVerify] = useState(false);
  const axiosPrivate = useAxiosPrivate();
  const obj = useLocation()?.state;

  const patientData = obj?.patientMedicalRecords;
  patientData.name = obj.name;
  patientData.contactNo = obj.contactNo;
  patientData.sex = obj.gender;
  patientData.dateOfBirth = obj.dob;
  patientData.patientId = obj.patientId;
  patientData.emergencyContact = obj.emergencyContact;
  patientData.emergencyContactNo = obj.emergencyContactNo;

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = () => {
      try {
        let endpoints = [
          axiosPrivate.get(
            `/staff/getAppointmentDetails?patientId=${obj.patientId}`
          ),
          axiosPrivate.get(
            `/staff/checkVerifyAppointment?patientId=${obj.patientId}`
          ),
        ];

        axios.all(endpoints).then(
          axios.spread((apptData, verify) => {
            isMounted && setApptData(apptData.data);
            isMounted && setVerify(verify.data.length > 0);
            patientData.currentAppt = verify.data;
            setIsLoaded(true);
          })
        );
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  if (!isLoaded) {
    // Render a loading indicator until the data is loaded
    return <div>Loading...</div>;
  }

  return (
    <>
      <h1>Patient - Profile Overview</h1>
      <div className="d-flex flex-column gap-2">
        <PatientDetails patient={patientData} editable={verify} />
        <div className="mt-3">
          <h5>Appointment History</h5>
          <table className="mt-2 table table-striped">
            <thead>
              <tr>
                <th scope="col">Clinic</th>
                <th scope="col">Doctor</th>
                <th scope="col">Date</th>
                <th scope="col">Time</th>
                <th scope="col">Diagnosis</th>
              </tr>
            </thead>
            <tbody>
              {apptData.length < 1 ? (
                <tr>
                  <td colSpan={5}>No data available....</td>
                </tr>
              ) : null}
              {apptData.map((data: any, idx: number) => (
                <tr key={idx}>
                  <th scope="row">{data.clinicName}</th>
                  <td>{data.doctorName}</td>
                  <td>{data.apptDate}</td>
                  <td>{data.apptTime}</td>
                  <td>{data.diagnostic}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}
