import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import RegisterAccount from "../pages/clinic/RegisterAccount";
import ManageAccount from "../pages/clinic/ManageAccount";
import Home from "../pages/clinic/Home";
import CreateAppointmentSlots from "../pages/clinic/CreateAppointmentSlots";
import Subscription from "../pages/clinic/Subscription";
import Feedback from "../pages/clinic/Feedback";
import NotFound from "../pages/NotFound";

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
                  },
                  {
                    name: "Manage Account",
                    link: "manage-account",
                    end: false,
                  },
                  {
                    name: "Create Appointment Slots",
                    link: "create-appointment-slots",
                  },
                  {
                    name: "Subscription Plans",
                    link: "subscription plans",
                  },
                  { name: "Feedback", link: "feedback" },
                ]}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<Home />} />
          <Route path="register-account" element={<RegisterAccount />} />
          <Route path="manage-account" element={<ManageAccount />} />
          <Route
            path="create-appointment-slots"
            element={<CreateAppointmentSlots />}
          />
          <Route path="subscription" element={<Subscription />} />
          <Route path="feedback" element={<Feedback />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
