import { Outlet, useLocation } from "react-router-dom";
import { CgBell, CgProfile, CgMenu } from "react-icons/cg";
import NavBar from "../components/navbar";
import { ReactNode, useEffect, useState } from "react";

import "../css/dashboard.css";
import Dropdown from "../components/dropdown";
import { useWindowDimensions } from "../hooks/hooks";

interface Props {
  children: ReactNode;
}

export default function DashboardLayout({ children }: Props) {
  const { width } = useWindowDimensions();
  const [show, setShow] = useState(false);

  useEffect(() => {
    setShow(width > 500);
  }, [width]);

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
        {show && children}
        <main>
          <div className="d-flex justify-content-between align-content-center">
            <h1>{useLocation()?.state ?? "Dashboard"}</h1>
            {width <= 500 && (
              <>
                <button
                  className="menu"
                  onClick={() => setShow(!show)}
                  type="button"
                  aria-expanded={show}
                >
                  {<CgMenu fontSize={"30px"} />}
                </button>
              </>
            )}
          </div>
          <Outlet />
        </main>
      </section>
    </>
  );
}
