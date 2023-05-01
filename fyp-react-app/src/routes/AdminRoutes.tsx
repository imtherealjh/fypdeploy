import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import Home from "../pages/admin/Home";
import ViewClinic from "../pages/admin/ViewClinic";
import NotFound from "../pages/NotFound";

export default function AdminRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar navList={[{ name: "Dashboard", link: "" }]} />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<Home />} />
          <Route path="/view" element={<ViewClinic />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </>
  );
}
