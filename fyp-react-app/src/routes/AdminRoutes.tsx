import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layoutComponent/DashboardLayout";
import Home from "../pages/admin/Home";
import ViewClinic from "../pages/admin/ViewClinic";
import NotFound from "../pages/NotFound";
import SystemFeedback from "../pages/all/SystemFeedback";
import Specialty from "../pages/admin/Specialty";
import CreateSpecialty from "../pages/admin/CreateSpecialty";

export default function AdminRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar
                navList={[
                  { name: "Dashboard", link: "" },
                  { name: "System Feedback", link: "system-feedback" },
                  { name: "Specialty", link: "specialty", end: false },
                ]}
                bottom={false}
              />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<Home />} />
          <Route path="view" element={<ViewClinic />} />
          <Route path="system-feedback">
            <Route index path="" element={<SystemFeedback />} />
          </Route>
          <Route path="specialty">
            <Route index path="" element={<Specialty />} />
            <Route path="create" element={<CreateSpecialty />} />
          </Route>
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
