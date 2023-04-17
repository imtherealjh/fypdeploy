import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import DoctorHome from "./pages/doctor/Home";
import RegisterAccount from "./pages/clinic/RegisterAccount";
import RegisterPatient from "./pages/RegisterPatient";
import RegisterClinic from "./pages/RegisterClinic";
import LandingPageLayout from "./layout/LandingPageLayout";
import DashboardLayout from "./layout/DashboardLayout";
import SideBar from "./components/sidenavbar";
import PersistLogin from "./components/PersistLogin";

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

          <Route element={<PersistLogin />}>
            <Route
              path="/doctor/*"
              element={
                <DashboardLayout>
                  <SideBar
                    navList={[
                      { name: "Dashboard", link: "" },
                      { name: "Appointment", link: "appointments" },
                      { name: "Patient List", link: "patients" },
                      { name: "Feed Back", link: "feedbacks" },
                    ]}
                  />
                </DashboardLayout>
              }
            >
              <Route index path="" element={<DoctorHome />} />
            </Route>

            <Route
              path="/clinic/*"
              element={
                <DashboardLayout>
                  <SideBar
                    navList={[
                      { name: "Dashboard", link: "" },
                      { name: "Create Account", link: "register-account" },
                      { name: "Manage Account", link: "manage-account" },
                      {
                        name: "Create Appointment",
                        link: "create-appointment",
                      },
                      { name: "Subscription", link: "subscription" },
                      { name: "Feedback", link: "feedback" },
                    ]}
                  />
                </DashboardLayout>
              }
            >
              <Route index path="" element={<DoctorHome />} />
              <Route path="register-account" element={<RegisterAccount />} />
            </Route>
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
