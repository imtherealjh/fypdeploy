import VerifyEmailSVG from "../assets/mail.svg";

export default function VerifyEmail() {
  return (
    <>
      <div className="d-flex flex-column justify-content-center align-items-center my-5">
        <img
          style={{ width: "20rem" }}
          src={VerifyEmailSVG}
          alt="Verify Email..."
        ></img>
        <h4 style={{ textAlign: "center" }}>
          Please check your email for verification code to verify your email...
        </h4>
        <p>
          Thank you for registering your account with us. A verification email
          has been sent to the the email sample@gmail.com.
        </p>
      </div>
    </>
  );
}
