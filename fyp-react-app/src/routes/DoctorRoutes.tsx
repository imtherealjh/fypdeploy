import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import DoctorHome from "../pages/doctor/Home";

import Feedback from "../pages/doctor/Feedback";
import NotFound from "../pages/NotFound";
import Profile from "../pages/doctor/Profile";

import PatientList from "../pages/nurseAndDoctor/PatientList";
import ViewMedicalDetails from "../pages/nurseAndDoctor/ViewMedicalDetails";
import ViewSingleArticle from "../components/ViewSingleArticle";

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
                  { name: "Feed Back", link: "feedbacks" },
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
          <Route path="feedbacks" element={<Feedback />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
