import { Outlet, useLocation } from "react-router-dom";
import { CgBell, CgProfile, CgMenu } from "react-icons/cg";
import NavBar from "../components/navbar";
import { ReactNode, useEffect, useRef, useState } from "react";

import "../css/dashboard.css";
import Dropdown from "../components/dropdown";
import { useWindowDimensions } from "../utils/hooks";

interface Props {
  children: ReactNode;
}

const acceptedLink = [
  { name: "Dashboard", link: "" },
  { name: "Appointment", link: "appointments" },
  { name: "Patient List", link: "patients" },
  { name: "Feed Back", link: "feedbacks" },
  { name: "FAQ", link: "faq" },
  { name: "Contact Us", link: "contact-us" },
];

export default function DashboardLayout({ children }: Props) {
  const { width } = useWindowDimensions();
  const [show, setShow] = useState(width > 500);
  const [activeBtn, setActiveBtn] = useState(0);

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
        <Dropdown
          buttonContent={<CgBell />}
          menuContent={null}
          onClick={() => {
            setActiveBtn(1);
          }}
          onOutsideClick={() => {
            setActiveBtn(0);
          }}
          open={activeBtn === 1}
        />
        ,
        <Dropdown
          buttonContent={<CgProfile />}
          menuContent={profileContent}
          onClick={() => setActiveBtn(2)}
          onOutsideClick={() => {
            setActiveBtn(0);
          }}
          open={activeBtn === 2}
        />
      </NavBar>
      <section className="dashboard d-flex flex-row mt-3">
        {show && children}
        <main>
          <div className="d-flex justify-content-between align-content-center">
            <h1>
              {
                acceptedLink.find((obj) => {
                  return (
                    `/doctor${obj.link !== "" ? `/${obj.link}` : ""}` ===
                    useLocation().pathname
                  );
                })?.name
              }
            </h1>
            {width <= 500 && (
              <>
                <button
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
