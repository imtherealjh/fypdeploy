import { NavLink } from "react-router-dom";
import "../css/sidenavbar.css";
import { NavigationItems } from "../hooks/types";
import { CgMenu } from "react-icons/cg";
import { useState } from "react";
import { useWindowDimensions } from "../hooks/hooks";

export type Props = {
  navList: Array<NavigationItems>;
};

export default function SideBar({ navList }: Props) {
  const { width } = useWindowDimensions();
  const [isOpen, setIsOpen] = useState(true);
  const toggle = () => setIsOpen(!isOpen);
  return (
    <>
      <div id="side-navbar" className="side-navbar">
        <button style={{ border: "none", background: "transparent" }}>
          <CgMenu onClick={toggle} fontSize={"1.5rem"} />
        </button>

        <ul>
          {navList.map((navItem, index) => (
            <NavLink
              key={"side-navbar" + index}
              state={navItem.name}
              to={navItem.link}
              end={navItem.end}
            >
              {({ isActive }) => (
                <li className={isActive ? "active" : ""}>
                  <button style={{ display: "block" }} type="button">
                    <span
                      style={{ display: !isOpen ? "inline-block" : "none" }}
                    >
                      {navItem.name.charAt(0)}
                    </span>
                    <span style={{ display: isOpen ? "inline-block" : "none" }}>
                      {navItem.name}
                    </span>
                  </button>
                </li>
              )}
            </NavLink>
          ))}
          <div className="bottom">
            <NavLink key="side-navbar-last-item-1" to="faq" state="FAQ" end>
              {({ isActive }) => (
                <li className={isActive ? "active" : ""}>
                  <button type="button">
                    <span
                      style={{ display: !isOpen ? "inline-block" : "none" }}
                    >
                      F
                    </span>
                    <span style={{ display: isOpen ? "inline-block" : "none" }}>
                      FAQ
                    </span>
                  </button>
                </li>
              )}
            </NavLink>

            <NavLink
              key="side-navbar-last-item-2"
              to="contact-us"
              state="Contact Us"
              end
            >
              {({ isActive }) => (
                <li className={isActive ? "active" : ""}>
                  <button type="button">
                    <span
                      style={{ display: !isOpen ? "inline-block" : "none" }}
                    >
                      C
                    </span>
                    <span style={{ display: isOpen ? "inline-block" : "none" }}>
                      Contact Us
                    </span>
                  </button>
                </li>
              )}
            </NavLink>
          </div>
        </ul>
      </div>
    </>
  );
}
