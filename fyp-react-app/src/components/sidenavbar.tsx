import { NavLink } from "react-router-dom";
import { NavigationItems } from "../hooks/types";

import "../css/sidenavbar.css";

export type Props = {
  bottom: true | false;
  navList: Array<Partial<NavigationItems>>;
};

export default function SideBar({ navList, bottom }: Props) {
  return (
    <>
      <div id="side-navbar" className="side-navbar d-flex">
        <ul className="d-flex flex-column w-100 justify-content-between">
          <div>
            {navList.map((navItem, index) => (
              <NavLink
                key={"side-navbar" + index}
                to={navItem?.link ?? ""}
                end={navItem?.end ?? true}
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
          </div>
          {bottom && (
            <div className="bottom w-100">
              <NavLink key="side-navbar-last-item-1" to="faq" end>
                {({ isActive }) => (
                  <li className={isActive ? "active" : ""}>
                    <button style={{ display: "inline-block" }} type="button">
                      <span>FAQ</span>
                    </button>
                  </li>
                )}
              </NavLink>

              <NavLink key="side-navbar-last-item-2" to="contact-us" end>
                {({ isActive }) => (
                  <li className={isActive ? "active" : ""}>
                    <button style={{ display: "inline-block" }} type="button">
                      <span>Contact Us</span>
                    </button>
                  </li>
                )}
              </NavLink>
            </div>
          )}
        </ul>
      </div>
    </>
  );
}
