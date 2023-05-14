import Cancel from "../assets/undraw_cancel_re_pkdm.svg";

export default function RequireVerifyPage() {
  return (
    <>
      <div className="d-flex flex-column justify-content-center align-items-center my-5">
        <img
          style={{ width: "20rem" }}
          src={Cancel}
          alt="Unable to login due to account being disabled..."
        ></img>
        <h4 className="mt-3" style={{ textAlign: "center" }}>
          Unable to login due to account being disabled... Please contact your
          adminstrator to enable your account.
        </h4>
      </div>
    </>
  );
}
