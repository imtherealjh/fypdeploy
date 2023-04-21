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

export default function PatientRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar
                navList={[
                  { name: "Dashboard", link: "", end: true },
                  {
                    name: "Search Clinic",
                    link: "search-clinic",
                    end: true,
                  },
                  { name: "Appointment", link: "appointment", end: false },
                  { name: "Queue", link: "queue", end: true },
                  {
                    name: "Medical Records",
                    link: "medical-records",
                    end: true,
                  },
                  { name: "Payments", link: "payment", end: true },
                  { name: "Feedbacks", link: "feedback", end: true },
                ]}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<Home />} />
          <Route path="search-clinic" element={<SearchClinic />} />
          <Route path="appointment">
            <Route index path="" element={<Appointment />} />
            <Route path="book" element={<BookAppointment />} />
          </Route>
          <Route path="queue" element={<Queue />} />
          <Route path="medical-records" element={<MedicalRecords />} />
          <Route path="payment" element={<Payment />} />
          <Route path="feedback" element={<Feedback />} />
        </Route>
      </Routes>
    </>
  );
}
