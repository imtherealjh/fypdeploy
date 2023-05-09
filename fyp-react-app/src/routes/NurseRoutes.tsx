import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import NurseHome from "../pages/nurse/Home";
import Feedback from "../pages/all/Feedback";
import Profile from "../pages/nurse/Profile";
import PatientList from "../pages/nurseAndDoctor/PatientList";

import NotFound from "../pages/NotFound";
import ViewMedicalDetails from "../pages/nurseAndDoctor/ViewMedicalDetails";
import Appointments from "../pages/nurseAndClerk/Appointments";
import Faq from "../pages/patient/Faq";
import Contact from "../pages/patient/ContactUs";
import SystemFeedback from "../pages/all/SystemFeedback";

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
                  { name: "System Feedback", link: "system-feedback" },
                ]}
                bottom={true}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<NurseHome />} />
          <Route path="profile" element={<Profile />} />
          <Route path="appointments" element={<Appointments />} />
          <Route path="patients">
            <Route index path="" element={<PatientList />} />
            <Route path="details" element={<ViewMedicalDetails />} />
          </Route>
          <Route path="system-feedback">
            <Route index path="" element={<SystemFeedback />} />
            <Route path="submit" element={<Feedback />} />
          </Route>
          <Route path="faq" element={<Faq />} />
          <Route path="contact-us" element={<Contact />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
