import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import ClerkHome from "../pages/clerk/Home";
import Appointments from "../pages/clerk/Appointments";
import Queue from "../pages/clerk/Queue";
import MedicalRecords from "../pages/clerk/MedicalRecords";
import Payment from "../pages/clerk/Payment";
import Feedback from "../pages/clerk/Feedback";
import NotFound from "../pages/NotFound";

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
                  { name: "Medical Records", link: "medical-records" },
                  { name: "Payment", link: "payment" },
                  { name: "Feedbacks", link: "feedbacks" },
                ]}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<ClerkHome />} />
          <Route path="appointments" element={<Appointments />} />
          <Route path="queue" element={<Queue />} />
          <Route path="medical-records" element={<MedicalRecords />} />
          <Route path="payment" element={<Payment />} />
          <Route path="feedbacks" element={<Feedback />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
