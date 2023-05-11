import RequireVerify from "../assets/mail.svg";

export default function RequireVerifyPage() {
  return (
    <>
      <div className="d-flex flex-column justify-content-center align-items-center my-5">
        <img
          style={{ width: "20rem" }}
          src={RequireVerify}
          alt="Verify Email..."
        ></img>
        <h4 className="mt-3" style={{ textAlign: "center" }}>
          You are required to verify with your email..
        </h4>
      </div>
    </>
  );
}
