import React, { useState, useEffect } from "react";
import axios from "axios";
import PatientDetails from "../../components/PatientDetails";

function PatientListPage() {
  const patient = {
    name: "John Doe",
    contactNumber: "+1 (234) 567-8910",
    sex: "Male",
    age: 35,
    dateOfBirth: "1988-01-01",
    weight: 70,
    height: 175,
    hospitalizedBefore: true,
    lastHospitalizedDate: "2021-05-01",
    takingMedication: false,
    foodAllergies: "Nuts",
    drugAllergies: "Penicillin",
    bloodType: "A+",
    medicalConditions: "Diabetes",
    emergencyContact: "Jane Doe",
    emergencyContactNumber: "+1 (987) 654-3210",
  };

  const [patientList, setPatientList] = useState([]);
  const [dummyPatientList, setDummyPatientList] = useState([patient]);

  const fetchPatients = async () => {
    try {
      const response = await axios.get("<your-backend-api-url>/patients");
      setPatientList(response.data);
    } catch (error) {
      console.error("Error fetching patient list:", error);
    }
  };

  useEffect(() => {
    fetchPatients();
  }, []);

  return (
    <>
      <h1>Patient List</h1>
      <div>
        {patientList.map((patient, index) => (
          <PatientDetails key={index} patient={patient} />
        ))}
        {dummyPatientList.map((patient, index) => (
          <PatientDetails key={`dummy-${index}`} patient={patient} />
        ))}
      </div>
    </>
  );
}

export default PatientListPage;
