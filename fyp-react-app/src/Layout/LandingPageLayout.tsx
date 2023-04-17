import Modal from "../components/modal";
import NavBar from "../components/navbar";
import Footer from "../components/footer";
import { Outlet } from "react-router-dom";
import LoginForm from "../components/LoginForm";

export default function LandingPageLayout() {
  return (
    <>
      <NavBar homePagePath="/">
        <button type="button" className="mx-1">
          Help
        </button>
        <button type="button" className="mx-1">
          Pricing
        </button>
        <Modal
          style={{
            borderRadius: "0.4rem",
            padding: "0.4rem",
            backgroundColor: "black",
            color: "white",
            fontWeight: "bold",
          }}
          name="Login"
        >
          <LoginForm />
        </Modal>
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
