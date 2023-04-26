import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import DoctorHome from "../pages/doctor/Home";
import Appointment from "../pages/doctor/Appointment";
import PatientList from "../pages/doctor/PatientList";
import Feedback from "../pages/doctor/Feedback";
import NotFound from "../pages/NotFound";
import Profile from "../pages/doctor/Profile";

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
                  { name: "Patient List", link: "patients" },
                  { name: "Feed Back", link: "feedbacks" },
                ]}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<DoctorHome />} />
          <Route path="profile" element={<Profile />} />
          <Route path="patients" element={<PatientList />} />
          <Route path="feedbacks" element={<Feedback />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
