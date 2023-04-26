import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import Appointment from "../pages/patient/Appointments/Appointment";
import BookAppointment from "../pages/patient/Appointments/BookAppointment";
import Home from "../pages/patient/Home";
import SearchClinic from "../pages/patient/SearchClinic";
import Queue from "../pages/patient/Queue";
import MedicalRecords from "../pages/patient/MedicalRecords";
import Feedback from "../pages/patient/Feedback";
import Payment from "../pages/patient/Payment";
import NotFound from "../pages/NotFound";
import Profile from "../pages/patient/Profile";

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
                  { name: "Payments", link: "payment" },
                  { name: "Feedbacks", link: "feedback" },
                ]}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<Home />} />
          <Route path="profile" element={<Profile />} />
          <Route path="search-clinic" element={<SearchClinic />} />
          <Route path="appointment">
            <Route index path="" element={<Appointment />} />
            <Route path="book" element={<BookAppointment />} />
          </Route>
          <Route path="queue" element={<Queue />} />
          <Route path="medical-records" element={<MedicalRecords />} />
          <Route path="payment" element={<Payment />} />
          <Route path="feedback" element={<Feedback />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
