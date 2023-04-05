import { Outlet } from "react-router-dom";
import { CgBell, CgProfile } from "react-icons/cg";
import NavBar from "../components/navbar";
import { ReactNode } from "react";

import "../css/dashboard.css";

interface Props {
  children: ReactNode;
}

export default function DashboardLayout({ children }: Props) {
  return (
    <>
      <NavBar homePagePath="/doctor">
        <button type="button">
          <CgBell />
        </button>
        <button type="button">
          <CgProfile />
        </button>
      </NavBar>
      <section className="dashboard mt-3">
        {children}
        <main>
          <Outlet />
        </main>
      </section>
    </>
  );
}
