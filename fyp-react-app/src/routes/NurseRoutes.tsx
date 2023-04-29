import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import NurseHome from "../pages/nurse/Home";
import Appointment from "../pages/nurse/Appointment";
import Feedback from "../pages/nurse/Feedback";
import MedicalRecords from "../pages/nurse/MedicalRecords";
import Profile from "../pages/nurse/Profile";
import Schedule from "../pages/nurse/Schedule";
import UpdateAppointment from "../pages/nurse/UpdateAppointment";
import NotFound from "../pages/NotFound";

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
                  { name: "Update Appointment", link: "update-appointment" },
                  { name: "Schedule", link: "schedule" },
                  { name: "Medical Records", link: "medical-records" },
                  { name: "Feedback", link: "feedbacks" },
                ]}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<NurseHome />} />
          <Route path="profile" element={<Profile />} />
          <Route path="appointments" element={<Appointment />} />
          <Route path="feedbacks" element={<Feedback />} />
          <Route path="medical-records" element={<MedicalRecords />} />
          <Route path="schedule" element={<Schedule />} />
          <Route path="update-appointment" element={<UpdateAppointment />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
