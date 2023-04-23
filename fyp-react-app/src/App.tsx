import { Route, Routes } from "react-router-dom";

// Layouts
import LandingPageLayout from "./layout/LandingPageLayout";

// Components
import PersistLogin from "./components/PersistLogin";
import RequireAuth from "./components/RequiredAuth";

// Pages
import Home from "./pages/Home";
import RegisterPatient from "./pages/RegisterPatient";
import RegisterClinic from "./pages/RegisterClinic";
import PatientRoutes from "./routes/PatientRoutes";
import ClinicRoutes from "./routes/ClinicRoutes";
import DoctorRoutes from "./routes/DoctorRoutes";
import VerifyEmail from "./pages/VerifyEmail";
import AdminRoutes from "./routes/AdminRoutes";
import Unauthorized from "./pages/Unauthorized";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <>
      <div className="container">
        <Routes>
          <Route element={<LandingPageLayout />}>
            <Route index path="/" element={<Home />} />
            <Route path="/registerClinic" element={<RegisterClinic />} />
            <Route path="/registerPatient" element={<RegisterPatient />} />
            <Route path="/verify" element={<VerifyEmail />} />
            <Route path="/unauthorized" element={<Unauthorized />} />
            <Route path="*" element={<NotFound />} />
          </Route>

          <Route element={<PersistLogin />}>
            <Route element={<RequireAuth role={"patient"} />}>
              <Route path="/patient/*" element={<PatientRoutes />} />
            </Route>
            <Route element={<RequireAuth role={"clinic_owner"} />}>
              <Route path="/clinic/*" element={<ClinicRoutes />} />
            </Route>
            {/* <Route element={<RequireAuth role={"doctor"} />}> */}
            <Route path="/doctor/*" element={<DoctorRoutes />} />
            {/* </Route> */}
            <Route element={<RequireAuth role={"system_admin"} />}>
              <Route path="/admin/*" element={<AdminRoutes />} />
            </Route>
            <Route element={<RequireAuth role={"clerk"} />}>
              <Route path="/clerk/*" element={<ClerkDashboard />} />
            </Route>
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
