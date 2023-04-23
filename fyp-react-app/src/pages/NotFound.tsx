import NotFound from "../assets/notFound.svg";

export default function Unauthorized() {
  return (
    <>
      <div className="d-flex flex-column justify-content-center align-items-center my-5">
        <img
          style={{ width: "20rem" }}
          src={NotFound}
          alt="Verify Email..."
        ></img>
        <h4 className="mt-3" style={{ textAlign: "center" }}>
          The page that you are accessing is not found...
        </h4>
      </div>
    </>
  );
}
