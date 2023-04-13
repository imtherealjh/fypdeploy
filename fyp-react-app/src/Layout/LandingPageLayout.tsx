import logo from "../assets/logo.png";
import Modal from "../components/modal";
import NavBar from "../components/navbar";
import Footer from "../components/footer";
import { Outlet, useNavigate } from "react-router-dom";
import { CgLock, CgProfile } from "react-icons/cg";
import { ChangeEvent, FormEvent, useState } from "react";
import useAuth from "../hooks/useAuth";
import { axiosPrivate } from "../api/axios";

function LoginForm() {
  const navigate = useNavigate();

  const [inputs, setInputs] = useState<{ username: string; password: string }>({
    username: "",
    password: "",
  });

  const { setAuth } = useAuth();

  const handleLogin = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const { username, password } = inputs;
    const response = await axiosPrivate.post("/auth/login", {
      username: username,
      password: password,
    });

    const { role, accessToken } = await response.data;
    setAuth({ role: role, accessToken: accessToken });

    document.getElementById("closeModalBtn")?.click();

    if (role.toLowerCase() == "clinic-owner")
      navigate("clinic", { replace: true });
    else if (role.toLowerCase() == "patient")
      navigate("patient", { replace: true });
    else if (role.toLowerCase() == "doctor")
      navigate("doctor", { replace: true });
    else if (role.toLowerCase() == "nurse")
      navigate("nurse", { replace: true });
    else if (role.toLowerCase() == "front-desk")
      navigate("clerk", { replace: true });
    else navigate("/", { replace: true });
  };

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  return (
    <>
      <button
        id="closeModalBtn"
        style={{ display: "none" }}
        type="button"
        className="btn-close"
        data-bs-dismiss="modal"
        aria-label="Close"
      ></button>
      <form
        onSubmit={handleLogin}
        id="loginForm"
        className="d-flex flex-column align-items-center"
        method="POST"
      >
        <div>
          <img src={logo} alt="Logo" />
        </div>
        <div className="mb-3">
          <label htmlFor="loginUsername" className="form-label">
            Username
          </label>
          <div className="input-group">
            <span className="input-group-text" id="basic-addon1">
              <CgProfile />
            </span>
            <input
              onChange={handleChange}
              type="text"
              name="username"
              className="form-control"
              aria-label="loginUsername"
              aria-describedby="loginUsername"
            />
          </div>
        </div>
        <div className="mb-3">
          <label htmlFor="loginPassword" className="form-label">
            Password
          </label>
          <div className="input-group">
            <span className="input-group-text" id="basic-addon1">
              <CgLock />
            </span>
            <input
              onChange={handleChange}
              type="password"
              name="password"
              className="form-control"
              aria-label="loginPassword"
              aria-describedby="loginPassword"
            />
          </div>
        </div>
        <div>
          <button className="btn btn-success btn-lg">Login</button>
        </div>
      </form>
    </>
  );
}

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
