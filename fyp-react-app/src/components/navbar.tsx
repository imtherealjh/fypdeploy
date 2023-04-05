import logo from "../assets/logo.png";
import { Link } from "react-router-dom";
import "../css/navbar.css";
import { ReactNode } from "react";

interface Props {
  homePagePath: string;
  children: Array<ReactNode>;
}

export default function NavBar({ homePagePath, children }: Props) {
  return (
    <>
      <nav className="d-flex justify-content-between align-items-center">
        <Link to={homePagePath}>
          <img src={logo} alt="logo" />
        </Link>
        <ul>
          {children.map((child, index) => (
            <li key={"navbar" + index}>{child}</li>
          ))}
        </ul>
      </nav>
    </>
  );
}
