import { ChangeEvent, FormEvent, useEffect, useRef, useState } from "react";
import { CgLock, CgProfile } from "react-icons/cg";
import { useNavigate } from "react-router-dom";
import logo from "../assets/logo.png";
import useAuth from "../hooks/useAuth";
import { axiosPrivate } from "../api/axios";

export default function LoginForm() {
  const errRef = useRef<HTMLParagraphElement>(null);
  const navigate = useNavigate();

  const [errMsg, setErrMsg] = useState("");
  const [inputs, setInputs] = useState<{ username: string; password: string }>({
    username: "",
    password: "",
  });

  const { setAuth } = useAuth();

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  const handleLogin = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await axiosPrivate.post("/auth/login", inputs);
      const { role, accessToken } = response.data;
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
    } catch (err: any) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 400) {
        setErrMsg("Missing Username or Password");
      } else {
        setErrMsg("Login failed");
      }
      errRef.current?.focus();
    }
  };

  useEffect(() => {
    setErrMsg("");
  }, [inputs]);

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
        <p
          ref={errRef}
          className={errMsg ? "error" : "hidden"}
          aria-live="assertive"
        >
          {errMsg}
        </p>
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
