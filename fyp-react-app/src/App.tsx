import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import Home from "./pages/Home";
import DoctorHome from "./pages/doctor/Home";
import RegisterPatient from "./pages/RegisterPatient";
import RegisterClinic from "./pages/RegisterClinic";
import LandingPageLayout from "./layout/LandingPageLayout";
import DashboardLayout from "./layout/DashboardLayout";
import SideBar from "./components/sidenavbar";

const DoctorSideBar = [
  { name: "Dashboard", link: "" },
  { name: "Appointment", link: "appointments" },
  { name: "Patient List", link: "patients" },
  { name: "Feed Back", link: "feedbacks" },
];

function App() {
  return (
    <>
      <div className="container">
        <Routes>
          <Route element={<LandingPageLayout />}>
            <Route index path="/" element={<Home />} />
            <Route path="/registerClinic" element={<RegisterClinic />} />
            <Route path="/registerPatient" element={<RegisterPatient />} />
          </Route>

          <Route
            path="/doctor/*"
            element={
              <DashboardLayout>
                <SideBar navList={DoctorSideBar} />
              </DashboardLayout>
            }
          >
            <Route index path="" element={<DoctorHome />} />
          </Route>

          <Route
            path="/clinic/*"
            element={
              <DashboardLayout>
                <SideBar navList={DoctorSideBar} />
              </DashboardLayout>
            }
          >
            <Route index path="" element={<DoctorHome />} />
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
