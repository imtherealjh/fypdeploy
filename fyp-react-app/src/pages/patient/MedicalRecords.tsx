import React, { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

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
  return <>MedicalRecords</>;
}
