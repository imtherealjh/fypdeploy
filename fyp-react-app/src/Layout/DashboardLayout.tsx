import { Outlet, useNavigate } from "react-router-dom";
import { CgBell, CgMenu, CgProfile } from "react-icons/cg";
import { ReactNode, useEffect, useState } from "react";

import NavBar from "../components/navbar";
import Dropdown from "../components/dropdown";

import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useWindowDimensions } from "../hooks/hooks";

import "../css/dashboard.css";
import useAuth from "../hooks/useAuth";

interface Props {
  children: ReactNode;
}

export default function DashboardLayout({ children }: Props) {
  const [isOpen, setIsOpen] = useState(true);
  const toggle = () => setIsOpen(!isOpen);

  const axiosPrivate = useAxiosPrivate();
  const { auth } = useAuth();

  const { width } = useWindowDimensions();
  const navigate = useNavigate();

  useEffect(() => {
    setIsOpen(width > 500);
  }, [width]);

  let isMounted = false;
  const profileContent = (
    <>
      <div className="d-flex flex-column gap-2">
        <img
          style={{ flex: "1 1 auto" }}
          src="https://via.placeholder.com/50"
          alt="Profile Img"
          className="rounded-circle"
        />
        <div
          style={{
            wordBreak: "break-all",
            width: "7.5rem",
            textAlign: "center",
          }}
        >
          <span style={{ display: "inline-block" }}>{auth.name}</span>
        </div>
        <div className="divider-wrapper">
          <div className="horizontal-divider"></div>
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
        {isOpen && (
          <div
            style={{
              position: width < 500 ? "absolute" : "relative",
              height: "100%",
              zIndex: 10,
              flexBasis: "20%",
            }}
          >
            {children}
          </div>
        )}
        <main className="w-100" style={{ position: "relative" }}>
          <button
            style={{
              position: "absolute",
              right: 0,
              zIndex: "10",
              border: "none",
              background: "transparent",
            }}
          >
            <CgMenu onClick={toggle} fontSize={"1.5rem"} />
          </button>
          <Outlet />
        </main>
      </section>
    </>
  );
}
