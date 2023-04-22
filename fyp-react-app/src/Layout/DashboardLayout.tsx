import { Outlet } from "react-router-dom";
import { CgBell, CgProfile } from "react-icons/cg";
import NavBar from "../components/navbar";
import { ReactNode } from "react";

import "../css/dashboard.css";
import Dropdown from "../components/dropdown";

interface Props {
  children: ReactNode;
}

export default function DashboardLayout({ children }: Props) {
  const profileContent = (
    <>
      <img
        src="https://via.placeholder.com/50"
        alt="Profile Img"
        className="rounded-circle"
      />
      <div className="divider-wrapper">
        <div className="horizontal-divider"></div>
      </div>
      <div style={{ wordBreak: "break-all", width: "7.5rem" }}>
        <span style={{ display: "inline-block" }}>
          AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        </span>
        <button>Logout</button>
      </div>
    </>
  );

  return (
    <>
      <NavBar homePagePath="/doctor">
        <Dropdown buttonContent={<CgBell />} menuContent={null} />
        <Dropdown buttonContent={<CgProfile />} menuContent={profileContent} />
      </NavBar>
      <section className="dashboard d-flex flex-row mt-3">
        {children}
        <main>
          <Outlet />
        </main>
      </section>
    </>
  );
}
