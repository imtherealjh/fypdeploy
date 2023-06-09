import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layoutComponent/DashboardLayout";
import RegisterAccount from "../pages/clinic/RegisterAccount";
import ManageAccount from "../pages/clinic/ManageAccount";
import Home from "../pages/clinic/Home";
import CreateAppointmentSlots from "../pages/clinic/CreateAppointmentSlots";
import Feedback from "../pages/clinic/Feedback";
import NotFound from "../pages/NotFound";
import Profile from "../pages/clinic/Profile";
import EditAccount from "../pages/clinic/EditAccount";
import Faq from "../pages/patient/Faq";
import Contact from "../pages/patient/ContactUs";
import SystemFeedback from "../pages/all/SystemFeedback";
import CreateSystemFeedback from "../pages/all/Feedback";

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
                  { name: "Patient Feedback", link: "patient-feedback" },
                  { name: "System Feedback", link: "system-feedback" },
                ]}
                bottom={true}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<Home />} />
          <Route path="profile" element={<Profile />} />
          <Route path="register-account" element={<RegisterAccount />} />
          <Route path="manage-account">
            <Route index path="" element={<ManageAccount />} />
            <Route path="edit" element={<EditAccount />} />
          </Route>
          <Route
            path="create-appointment-slots"
            element={<CreateAppointmentSlots />}
          />
          <Route path="patient-feedback" element={<Feedback />} />
          <Route path="system-feedback">
            <Route index path="" element={<SystemFeedback />} />
            <Route path="submit" element={<CreateSystemFeedback />} />
          </Route>
          <Route path="faq" element={<Faq />} />
          <Route path="contact-us" element={<Contact />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
