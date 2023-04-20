import { NavLink } from "react-router-dom";
import "../css/sidenavbar.css";
import { NavigationItems } from "../utils/types";

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
                  <button type="button">{navItem.name}</button>
                </li>
              )}
            </NavLink>
          ))}
          <div className="bottom">
            <NavLink key="side-navbar-last-item-1" to="faq" state="FAQ" end>
              {({ isActive }) => (
                <li className={isActive ? "active" : ""}>
                  <button type="button">FAQ</button>
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
                  <button type="button">Contact Us</button>
                </li>
              )}
            </NavLink>
          </div>
        </ul>
      </div>
    </>
  );
}
