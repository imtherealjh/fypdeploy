import logo from "../assets/logo.png";
import { Link } from "react-router-dom";
import "../css/footer.css";
import { CgCopyright } from "react-icons/cg";
import { useWindowDimensions } from "../hooks/hooks";

export default function Footer() {
  const { width } = useWindowDimensions();

  return (
    <>
      <footer>
        <div>
          <img src={logo} alt="logo" />
        </div>
        <div>
          <p>Latest Blog Post</p>
          <p>Ready to get started?</p>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
            eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim
            ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
            aliquip ex ea commodo consequat.
          </p>
        </div>
        {width > 445 && (
          <>
            <div className="divider-wrapper">
              <div className="vertical-divider"></div>
            </div>
          </>
        )}
        <div>
          <div className="product-details d-flex justify-content-between">
            <div>
              <p>Product</p>
              <div className="d-flex flex-column">
                <a>Product</a>
                <a>Product</a>
                <a>Product</a>
              </div>
            </div>
            <div className="ms-2">
              <p>Company</p>
              <div className="d-flex flex-column">
                <a>Company</a>
                <a>Company</a>
                <a>Company</a>
              </div>
            </div>
          </div>
          <div className="d-flex mt-3 justify-content-between gap-2">
            <div>
              <CgCopyright />
              <span className="ms-1">2010-2022</span>
            </div>
            <div>
              <span>Privacy - Terms</span>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
}
