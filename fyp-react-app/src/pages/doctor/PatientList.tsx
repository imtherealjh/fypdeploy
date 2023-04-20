import React from "react";
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

  return (
    <div>
      <PatientDetails patient={patient} />
    </div>
  );
}

export default PatientListPage;
