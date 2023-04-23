import { Outlet, useNavigate } from "react-router-dom";
import { CgBell, CgProfile } from "react-icons/cg";
import NavBar from "../components/navbar";
import { ReactNode } from "react";

import "../css/dashboard.css";
import Dropdown from "../components/dropdown";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

interface Props {
  children: ReactNode;
}

export default function DashboardLayout({ children }: Props) {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  let isMounted = false;

  const profileContent = (
    <>
      <div className="d-flex flex-column">
        <div>
          <img
            style={{ flex: "1 1 auto" }}
            src="https://via.placeholder.com/50"
            alt="Profile Img"
            className="rounded-circle"
          />
        </div>
        <div className="divider-wrapper">
          <div className="horizontal-divider"></div>
        </div>
        <div style={{ wordBreak: "break-all", width: "7.5rem" }}>
          <span style={{ display: "inline-block" }}>
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
          </span>
        </div>
        <button
          className="btn btn-danger"
          onClick={async () => {
            if (!isMounted) {
              isMounted = true;
              try {
                await axiosPrivate.post("/auth/logout");
                alert("Logout successful!");
                navigate("/");
              } catch (error) {
                alert("There is an error while logging out...");
              }
            }
          }}
        >
          Logout
        </button>
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
