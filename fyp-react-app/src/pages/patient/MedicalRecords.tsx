import React, { useEffect, useState } from "react";
import useAxiosPrivate from "../../lib/useAxiosPrivate";

interface MedicalRecord {
  username: string;
  age: number;
  dateOfBirth: string;
  weight: number;
  height: number;
  hospitalizedBefore: boolean;
  currentMedication: string;
  foodAllergies: string;
  drugAllergies: string;
  bloodType: string;
  medicalConditions: string;
  emergencyContact: string;
  emergencyContactNumber: string;
}

export default function MedicalRecords() {
  const axiosPrivate = useAxiosPrivate();
  const [medicalRecord, setMedicalRecord] = useState<MedicalRecord | null>(
    null
  );

  useEffect(() => {
    fetchMedicalRecord();
  }, []);

  const fetchMedicalRecord = async () => {
    try {
      const userId = "some-user-id"; // have to replace this with the information used to identify the user
      const response = await axiosPrivate.get(
        `/doctor/getByMedicalRecordsId?medicalRecordsId=${userId}`
      );
      setMedicalRecord(response.data);
    } catch (error) {
      console.error("Error fetching medical record:", error);
    }
  };

  // FAKE DATA TO MIMIC API CALL
  // const fetchFakeMedicalRecord = () => {
  //   setTimeout(() => {
  //     setMedicalRecord({
  //       username: "John Doe",
  //       age: 30,
  //       dateOfBirth: "1993-02-15",
  //       weight: 75,
  //       height: 180,
  //       hospitalizedBefore: true,
  //       currentMedication: "Aspirin",
  //       foodAllergies: "Peanuts",
  //       drugAllergies: "Penicillin",
  //       bloodType: "A+",
  //       medicalConditions: "Asthma",
  //       emergencyContact: "Jane Doe",
  //       emergencyContactNumber: "123-456-7890",
  //     });
  //   }, 1000);
  // };

  return (
    <>
      <h1>Medical Records</h1>
      <div>
        <table>
          <thead>
            <tr>
              <th>
                {medicalRecord
                  ? `${medicalRecord.username}, ${medicalRecord.age}`
                  : "-"}
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th>Date of Birth</th>
              <th>Weight</th>
              <th>Height</th>
            </tr>
            <tr>
              <td>{medicalRecord?.dateOfBirth || "-"}</td>
              <td>{medicalRecord?.weight || "-"}</td>
              <td>{medicalRecord?.height || "-"}</td>
            </tr>
            <tr>
              <th>Hospitalized Before</th>
              <th>Current Medication</th>
              <th>Food Allergies</th>
            </tr>
            <tr>
              <td>
                {medicalRecord
                  ? medicalRecord.hospitalizedBefore
                    ? "Yes"
                    : "No"
                  : "-"}
              </td>
              <td>{medicalRecord?.currentMedication || "-"}</td>
              <td>{medicalRecord?.foodAllergies || "-"}</td>
            </tr>
            <tr>
              <th>Drug Allergies</th>
              <th>Blood Type</th>
              <th>Medical Conditions</th>
            </tr>
            <tr>
              <td>{medicalRecord?.drugAllergies || "-"}</td>
              <td>{medicalRecord?.bloodType || "-"}</td>
              <td>{medicalRecord?.medicalConditions || "-"}</td>
            </tr>
            <tr>
              <th>Emergency Contact</th>
              <th>Emergency Contact Number</th>
            </tr>
            <tr>
              <td>{medicalRecord?.emergencyContact || "-"}</td>
              <td>{medicalRecord?.emergencyContactNumber || "-"}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
}
