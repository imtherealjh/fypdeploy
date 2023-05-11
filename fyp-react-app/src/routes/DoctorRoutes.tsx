import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layoutComponent/DashboardLayout";
import DoctorHome from "../pages/doctor/Home";

import Feedback from "../pages/doctor/Feedback";
import NotFound from "../pages/NotFound";
import Profile from "../pages/doctor/Profile";

import PatientList from "../pages/nurseAndDoctor/PatientList";
import ViewMedicalDetails from "../pages/nurseAndDoctor/ViewMedicalDetails";
import ViewSingleArticle from "../components/ViewSingleArticle";
import Faq from "../pages/patient/Faq";
import Contact from "../pages/patient/ContactUs";
import SystemFeedback from "../pages/all/SystemFeedback";
import CreateSystemFeedback from "../pages/all/Feedback";

export default function DoctorRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar
                navList={[
                  { name: "Dashboard", link: "" },
                  { name: "Patient List", link: "patients", end: false },
                  { name: "Patient Feedback", link: "patient-feedback" },
                  { name: "System Feedback", link: "system-feedback" },
                ]}
                bottom={true}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<DoctorHome />} />
          <Route path="profile" element={<Profile />} />
          <Route path="view" element={<ViewSingleArticle />} />
          <Route path="patients">
            <Route index path="" element={<PatientList />} />
            <Route path="details" element={<ViewMedicalDetails />} />
          </Route>
          <Route path="patient-feedback" element={<Feedback />} />
          <Route path="system-feedback">
            <Route index path="" element={<SystemFeedback />} />
            <Route path="submit" element={<CreateSystemFeedback />} />
          </Route>
          <Route path="faq" element={<Faq />} />
          <Route path="contact-us" element={<Contact />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
