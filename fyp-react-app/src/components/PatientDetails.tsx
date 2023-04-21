import React from "react";
import "../css/patientdetails.css";

//note: replace the patient variable with the actual data from backend l8r

interface Props {
  patient: any;
}

function PatientDetails({ patient }: Props) {
  return (
    <div className="patient-details">
      <h1>{patient.name}</h1>
      <p>Contact Number: {patient.contactNumber}</p>
      <p>Sex: {patient.sex}</p>
      <p>Age: {patient.age}</p>
      <p>Date of Birth: {patient.dateOfBirth}</p>
      <p>Weight: {patient.weight} kg</p>
      <p>Height: {patient.height} cm</p>
      <p>
        Hospitalized before: {patient.hospitalizedBefore ? "Yes" : "No"}{" "}
        {patient.lastHospitalizedDate && `(${patient.lastHospitalizedDate})`}
      </p>
      <p>Taking medication: {patient.takingMedication ? "Yes" : "No"}</p>
      <p>Food Allergies: {patient.foodAllergies}</p>
      <p>Drug Allergies: {patient.drugAllergies}</p>
      <p>Blood Type: {patient.bloodType}</p>
      <p>Medical Conditions: {patient.medicalConditions}</p>
      <p>Emergency Contact: {patient.emergencyContact}</p>
      <p>Emergency Contact Number: {patient.emergencyContactNumber}</p>
    </div>
  );
}

export default PatientDetails;
