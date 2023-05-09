import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import ClerkHome from "../pages/clerk/Home";
import Appointments from "../pages/nurseAndClerk/Appointments";
import Queue from "../pages/clerk/Queue";
import Articles from "../pages/clerk/Articles";
import Payment from "../pages/clerk/Payment";
import Feedback from "../pages/all/Feedback";
import NotFound from "../pages/NotFound";
import Profile from "../pages/clerk/Profile";
import PublishArticles from "../pages/clerk/PublishArticles";
import ViewSingleArticle from "../components/ViewSingleArticle";
import PatientListPage from "../pages/clerk/PatientList";
import ViewPersonalDetails from "../pages/clerk/ViewPersonalDetails";
import Faq from "../pages/patient/Faq";
import Contact from "../pages/patient/ContactUs";
import SystemFeedback from "../pages/all/SystemFeedback";

export default function ClerkRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar
                navList={[
                  { name: "Dashboard", link: "" },
                  { name: "Appointments", link: "appointments" },
                  { name: "Queue", link: "queue" },
                  { name: "Patient List", link: "patients", end: false },
                  { name: "Articles", link: "articles", end: false },
                  { name: "Payment", link: "payment" },
                  { name: "System Feedback", link: "system-feedback" },
                ]}
                bottom={true}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<ClerkHome />} />
          <Route path="profile" element={<Profile />} />
          <Route path="appointments" element={<Appointments />} />
          <Route path="queue" element={<Queue />} />
          <Route path="articles">
            <Route index path="" element={<Articles />} />
            <Route path="publish" element={<PublishArticles />} />
            <Route path="view" element={<ViewSingleArticle />} />
          </Route>
          <Route path="patients">
            <Route index path="" element={<PatientListPage />} />
            <Route path="details" element={<ViewPersonalDetails />} />
          </Route>
          <Route path="payment" element={<Payment />} />
          <Route path="system-feedback">
            <Route index path="" element={<SystemFeedback />} />
            <Route path="submit" element={<Feedback />} />
          </Route>
          <Route path="faq" element={<Faq />} />
          <Route path="contact-us" element={<Contact />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
