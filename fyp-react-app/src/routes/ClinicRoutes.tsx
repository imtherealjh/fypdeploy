import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import DoctorHome from "../pages/doctor/Home";
import RegisterAccount from "../pages/clinic/RegisterAccount";

export default function ClincRoutes() {
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
      </Routes>
    </>
  );
}
