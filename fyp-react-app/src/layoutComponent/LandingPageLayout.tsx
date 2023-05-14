import NavBar from "../components/navbar";
import Footer from "../components/footer";
import { Link, Outlet } from "react-router-dom";
import LoginForm from "../components/LoginForm";

export default function LandingPageLayout() {
  return (
    <>
      <NavBar homePagePath="/">
        <Link to="/queue">
          <button type="button" className="mx-2">
            Queue
          </button>
        </Link>
        <LoginForm />
      </NavBar>
      <section className="mx-3">
        <main className="my-3">
          <Outlet />
        </main>
        <Footer />
      </section>
    </>
  );
}
