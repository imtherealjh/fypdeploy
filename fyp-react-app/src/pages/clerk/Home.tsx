import React, { useState } from "react";
import AppointmentList from "../../components/AppointmentList";
import PatientDetails from "../../components/PatientDetails";

function ClerkHome() {
  // State to store appointments when we actually connect to backend
  // const [appointments, setAppointments] = useState<Appointment[]>([]);

  const dummyAppointments = [
    {
      id: 1,
      patientName: "Jane Doe",
      doctorName: "Dr. Smith",
      date: "25/04/2023",
      time: "10:00",
      queue: 1,
    },
    {
      id: 2,
      patientName: "John Doe",
      doctorName: "Dr. Smith",
      date: "25/04/2023",
      time: "10:30",
      queue: 2,
    },
  ];

  const [selectedPatient, setSelectedPatient] = useState<any>(null);

  const handleAppointmentSelection = (appointmentId: number) => {
    const selectedAppointment = dummyAppointments.find(
      (appointment) => appointment.id === appointmentId
    );

    // Fetch patient details based on the selected appointment
    const patientDetails = {
      lastChecked: "24/04/2023",
      observation: "High fever and cough",
      prescription: "Paracetamol & Antibiotics",
      contactNumber: "555-123-4567",
      sex: "Female",
      age: 32,
      dateOfBirth: "15/03/1991",
      weight: 62,
      height: 168,
      hospitalizedBefore: true,
      lastHospitalizedDate: "12/12/2022",
      takingMedication: true,
      foodAllergies: "Peanuts, Shellfish",
      drugAllergies: "Penicillin",
      bloodType: "O+",
      medicalConditions: "Asthma",
      emergencyContact: "Alice Doe",
      emergencyContactNumber: "555-987-6543",
    };

    setSelectedPatient({ ...selectedAppointment, ...patientDetails });

    // When connecting to the backend, replace the above patientDetails object with the code below:
    // fetchPatientDetails(appointmentId);
  };

  /*
  // Uncomment this code when connecting to the backend
  const fetchAppointments = async () => {
    try {
      const response = await axiosPrivate.get("/appointments"); // Replace "/appointments" with the correct endpoint
      setAppointments(response.data);
    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchPatientDetails = async (appointmentId: number) => {
    try {
      const response = await axiosPrivate.get(`/patient-details/${appointmentId}`); // Replace "/patient-details/${appointmentId}" with the correct endpoint
      setSelectedPatient({ ...response.data });
    } catch (error) {
      console.error("Error fetching patient details:", error);
    }
  };
  */

  return (
    <>
      <h1>Dashboard</h1>
      <div className="w-100">
        <AppointmentList
          appointmentsList={dummyAppointments}
          onAppointmentSelect={handleAppointmentSelection}
        />
        <PatientDetails patient={selectedPatient} />
      </div>
    </>
  );
}

export default ClerkHome;
