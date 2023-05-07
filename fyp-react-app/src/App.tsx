import { Route, Routes } from "react-router-dom";
import { lazy } from "react";

// Layouts
import LandingPageLayout from "./layout/LandingPageLayout";

// Components
import PersistLogin from "./lib/PersistLogin";
import RequireAuth from "./lib/RequiredAuth";

// Pages
import Home from "./pages/Home";
import RegisterPatient from "./pages/RegisterPatient";
import RegisterClinic from "./pages/RegisterClinic";
import VerifyEmail from "./pages/VerifyEmail";
import Unauthorized from "./pages/Unauthorized";
import NotFound from "./pages/NotFound";

const ClinicRoutes = lazy(() => import("./routes/ClinicRoutes"));
const DoctorRoutes = lazy(() => import("./routes/DoctorRoutes"));
const AdminRoutes = lazy(() => import("./routes/AdminRoutes"));
const ClerkRoutes = lazy(() => import("./routes/ClerkRoutes"));
const PatientRoutes = lazy(() => import("./routes/PatientRoutes"));
const NurseRoutes = lazy(() => import("./routes/NurseRoutes"));

function App() {
  return (
    <>
      <div className="container">
        <Routes>
          <Route element={<LandingPageLayout />}>
            <Route index path="/" element={<Home />} />
            <Route path="/registerClinic" element={<RegisterClinic />} />
            <Route path="/registerPatient" element={<RegisterPatient />} />
            <Route path="/verify/:code" element={<VerifyEmail />} />
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
            <Route element={<RequireAuth role={"doctor"} />}>
              <Route path="/doctor/*" element={<DoctorRoutes />} />
            </Route>
            <Route element={<RequireAuth role={"system_admin"} />}>
              <Route path="/admin/*" element={<AdminRoutes />} />
            </Route>
            <Route element={<RequireAuth role={"front_desk"} />}>
              <Route path="/clerk/*" element={<ClerkRoutes />} />
            </Route>
            <Route element={<RequireAuth role={"nurse"} />}>
              <Route path="/nurse/*" element={<NurseRoutes />} />
            </Route>
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
