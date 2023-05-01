import { ChangeEvent, FormEvent, useEffect, useRef, useState } from "react";
import { CgLock, CgProfile } from "react-icons/cg";
import { useNavigate } from "react-router-dom";
import logo from "../assets/logo.png";
import useAuth from "../lib/useAuth";
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
      const { name, role, accessToken } = response.data;
      setAuth({ name: name, role: role, accessToken: accessToken });

      document.getElementById("closeModalBtn")?.click();

      if (role.toLowerCase() == "clinic_owner")
        navigate("clinic", { replace: true });
      else if (role.toLowerCase() == "patient")
        navigate("patient", { replace: true });
      else if (role.toLowerCase() == "doctor")
        navigate("doctor", { replace: true });
      else if (role.toLowerCase() == "nurse")
        navigate("nurse", { replace: true });
      else if (role.toLowerCase() == "front_desk")
        navigate("clerk", { replace: true });
      else if (role.toLowerCase() == "system_admin")
        navigate("admin", { replace: true });
      else navigate("/", { replace: true });
    } catch (err: any) {
      console.log(err);
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 400) {
        setErrMsg("Missing Username or Password");
      } else if (err.response?.status === 409) {
        document.getElementById("closeModalBtn")?.click();
        navigate("/verify", { replace: true });
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
      <div>
        <button
          type="button"
          style={{
            borderRadius: "0.4rem",
            padding: "0.4rem",
            backgroundColor: "black",
            color: "white",
            fontWeight: "bold",
          }}
          className="btn"
          data-bs-toggle="modal"
          data-bs-target="#modal"
        >
          Login
        </button>
        <div
          className="modal fade"
          id="modal"
          data-bs-keyboard="false"
          tabIndex={-1}
          aria-labelledby={name + "modal"}
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content py-3">
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
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
