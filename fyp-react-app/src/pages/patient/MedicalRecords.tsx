import { useEffect, useState } from "react";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import PatientDetails from "../../components/PatientDetails";

export default function MedicalRecords() {
  const axiosPrivate = useAxiosPrivate();
  const [patientData, setPatientData] = useState<any>();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get("/patient/getPatientProfile", {
          signal: controller.signal,
        });

        const patientData = response.data?.patientMedicalRecords;
        patientData.name = response.data?.name;
        patientData.contactNo = response.data?.contactNo;
        patientData.sex = response.data?.gender;
        patientData.dateOfBirth = response.data?.dob;
        patientData.patientId = response.data?.patientId;
        patientData.emergencyContact = response.data?.emergencyContact;
        patientData.emergencyContactNo = response.data?.emergencyContactNo;

        isMounted && setPatientData(patientData);
      } catch (err: any) {
        console.error(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  return (
    <>
      <h1>Medical Records</h1>
      <div
        style={{
          position: "relative",
          background: "white",
          padding: "1.2rem",
          borderRadius: "1.2rem",
        }}
      >
        <PatientDetails patient={patientData} />
      </div>
    </>
  );
}
