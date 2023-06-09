import AccessDenied from "../assets/accessDenied.svg";

export default function Unauthorized() {
  return (
    <>
      <div className="d-flex flex-column justify-content-center align-items-center my-5">
        <img
          style={{ width: "20rem" }}
          src={AccessDenied}
          alt="Verify Email..."
        ></img>
        <h4 className="mt-3" style={{ textAlign: "center" }}>
          You are not allowed to access this page...
        </h4>
      </div>
    </>
  );
}
