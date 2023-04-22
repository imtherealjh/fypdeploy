import { Route, Routes } from "react-router-dom";
import SideBar from "../components/sidenavbar";
import DashboardLayout from "../layout/DashboardLayout";
import Home from "../pages/admin/Home";
import ViewClinic from "../pages/admin/ViewClinic";

export default function AdminRoutes() {
  return (
    <>
      <Routes>
        <Route
          element={
            <DashboardLayout>
              <SideBar navList={[{ name: "Dashboard", link: "", end: true }]} />
            </DashboardLayout>
          }
        >
          <Route index path="" element={<Home />} />
          <Route path="/view/:id" element={<ViewClinic />} />
        </Route>
      </Routes>
    </>
  );
}
