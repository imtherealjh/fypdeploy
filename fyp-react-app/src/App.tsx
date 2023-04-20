import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import DoctorHome from "./pages/doctor/Home";
import RegisterAccount from "./pages/clinic/RegisterAccount";
import RegisterPatient from "./pages/RegisterPatient";
import RegisterClinic from "./pages/RegisterClinic";
import Appointment from "./pages/patient/Appointment";
import BookAppointment from "./pages/patient/BookAppointment";
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
                      { name: "Dashboard", link: "", end: true },
                      { name: "Appointment", link: "appointments", end: true },
                      { name: "Patient List", link: "patients", end: true },
                      { name: "Feed Back", link: "feedbacks", end: true },
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
                      { name: "Dashboard", link: "", end: true },
                      {
                        name: "Create Account",
                        link: "register-account",
                        end: true,
                      },
                      {
                        name: "Manage Account",
                        link: "manage-account",
                        end: true,
                      },
                      {
                        name: "Create Appointment",
                        link: "create-appointment",
                        end: true,
                      },
                      {
                        name: "Subscription",
                        link: "subscription",
                        end: true,
                      },
                      { name: "Feedback", link: "feedback", end: true },
                    ]}
                  />
                </DashboardLayout>
              }
            >
              <Route index path="" element={<DoctorHome />} />
              <Route path="register-account" element={<RegisterAccount />} />
            </Route>

            <Route
              path="/patient/*"
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
              <Route index path="" element={<DoctorHome />} />
              <Route path="appointment" element={<Appointment />} />
              <Route path="appointment/book" element={<BookAppointment />} />
            </Route>
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
