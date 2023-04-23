import { NavLink } from "react-router-dom";
import "../css/sidenavbar.css";
import { NavigationItems } from "../hooks/types";

export type Props = {
  navList: Array<NavigationItems>;
};

export default function SideBar({ navList }: Props) {
  return (
    <>
      <div id="side-navbar" className="side-navbar">
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
                  <button style={{ display: "inline-block" }} type="button">
                    <span>{navItem.name}</span>
                  </button>
                </li>
              )}
            </NavLink>
          ))}
          <div className="bottom">
            <NavLink key="side-navbar-last-item-1" to="faq" state="FAQ" end>
              {({ isActive }) => (
                <li className={isActive ? "active" : ""}>
                  <button style={{ display: "inline-block" }} type="button">
                    <span>FAQ</span>
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
                  <button style={{ display: "inline-block" }} type="button">
                    <span>Contact Us</span>
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
