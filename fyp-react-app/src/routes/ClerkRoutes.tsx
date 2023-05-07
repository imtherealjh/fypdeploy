import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../Layout/DashboardLayout";
import ClerkHome from "../pages/clerk/Home";
import Appointments from "../pages/nurseAndClerk/Appointments";
import Queue from "../pages/clerk/Queue";
import Articles from "../pages/clerk/Articles";
import Payment from "../pages/clerk/Payment";
import Feedback from "../pages/clerk/Feedback";
import NotFound from "../pages/NotFound";
import Profile from "../pages/clerk/Profile";
import PublishArticles from "../pages/clerk/PublishArticles";
import ViewSingleArticle from "../components/ViewSingleArticle";
import PatientListPage from "../pages/clerk/PatientList";
import ViewPersonalDetails from "../pages/clerk/ViewPersonalDetails";
import Faq from "../pages/patient/Faq";
import Contact from "../pages/patient/ContactUs";

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
                  { name: "Feedbacks", link: "feedbacks" },
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
          <Route path="feedbacks" element={<Feedback />} />
          <Route path="faq" element={<Faq />} />
          <Route path="contact-us" element={<Contact />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
