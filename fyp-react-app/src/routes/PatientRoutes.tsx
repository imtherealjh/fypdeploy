import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layoutComponent/DashboardLayout";
import Appointment from "../pages/patient/Appointments/Appointment";
import BookAppointment from "../pages/patient/Appointments/BookAppointment";
import Home from "../pages/patient/Home";
import SearchClinic from "../pages/patient/SearchClinic";
import Queue from "../pages/patient/Queue";
import MedicalRecords from "../pages/patient/MedicalRecords";
import Feedback from "../pages/all/Feedback";
import NotFound from "../pages/NotFound";
import Profile from "../pages/patient/Profile";
import Faq from "../pages/patient/Faq";
import Contact from "../pages/patient/ContactUs";
import SystemFeedback from "../pages/all/SystemFeedback";
import ViewSingleArticle from "../components/ViewSingleArticle";

export default function PatientRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar
                navList={[
                  { name: "Dashboard", link: "" },
                  {
                    name: "Search Clinic",
                    link: "search-clinic",
                  },
                  { name: "Appointment", link: "appointment", end: false },
                  { name: "Queue", link: "queue" },
                  {
                    name: "Medical Records",
                    link: "medical-records",
                  },
                  { name: "System Feedback", link: "system-feedback" },
                ]}
                bottom={true}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<Home />} />
          <Route path="profile" element={<Profile />} />
          <Route path="search-clinic" element={<SearchClinic />} />
          <Route path="view" element={<ViewSingleArticle />} />
          <Route path="appointment">
            <Route index path="" element={<Appointment />} />
            <Route path="book" element={<BookAppointment />} />
          </Route>
          <Route path="queue" element={<Queue />} />
          <Route path="medical-records" element={<MedicalRecords />} />
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
