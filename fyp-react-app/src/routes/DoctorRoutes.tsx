import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import DoctorHome from "../pages/doctor/Home";
import Appointment from "../pages/doctor/Appointment";
import PatientList from "../pages/doctor/PatientList";
import Feedback from "../pages/doctor/Feedback";

export default function ClincRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar
                navList={[
                  { name: "Dashboard", link: "", end: true },
                  { name: "Appointment", link: "appointments", end: true },
                  { name: "Patient List", link: "patients", end: true },
                  { name: "Feed Back", link: "feedbacks", end: true },
                ]}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<DoctorHome />} />
          <Route path="appointments" element={<Appointment />} />
          <Route path="patients" element={<PatientList />} />
          <Route path="feedbacks" element={<Feedback />} />
        </Route>
      </Routes>
    </>
  );
}
