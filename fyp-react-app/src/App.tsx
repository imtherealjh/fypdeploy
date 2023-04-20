import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import DoctorHome from "./pages/doctor/Home";
import RegisterPatient from "./pages/RegisterPatient";
import RegisterClinic from "./pages/RegisterClinic";
import LandingPageLayout from "./Layout/LandingPageLayout";
import DashboardLayout from "./Layout/DashboardLayout";
import SideBar from "./components/sidenavbar";
import Appointment from "./pages/doctor/Appointment";
import PatientList from "./pages/doctor/PatientList";
import Feedback from "./pages/doctor/Feedback";

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
            <Route path="appointments" element={<Appointment />} />
            <Route path="patients" element={<PatientList />} />
            <Route path="feedbacks" element={<Feedback />} />
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
