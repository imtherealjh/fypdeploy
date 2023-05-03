import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import NurseHome from "../pages/nurse/Home";
import Appointment from "../pages/nurse/Appointment";
import Feedback from "../pages/nurse/Feedback";
import Profile from "../pages/nurse/Profile";
import PatientList from "../pages/nurseAndDoctor/PatientList";

import NotFound from "../pages/NotFound";
import ViewMedicalDetails from "../pages/nurseAndDoctor/ViewMedicalDetails";

export default function NurseRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar
                navList={[
                  { name: "Dashboard", link: "" },
                  { name: "Appointments", link: "appointments" },
                  { name: "Patient List", link: "patients", end: false },
                  { name: "Feedback", link: "feedbacks" },
                ]}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<NurseHome />} />
          <Route path="profile" element={<Profile />} />
          <Route path="appointments" element={<Appointment />} />
          <Route path="patients">
            <Route index path="" element={<PatientList />} />
            <Route path="details" element={<ViewMedicalDetails />} />
          </Route>
          <Route path="feedbacks" element={<Feedback />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
