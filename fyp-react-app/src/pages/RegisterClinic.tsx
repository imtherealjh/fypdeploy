import "../css/register.css";

export default function RegisterClinic() {
  return (
    <>
      <div className="register d-flex flex-column align-items-center mx-4">
        <h1 className="my-0 w-100">
          <span>CREATE ACCOUNT</span>
        </h1>
        <form
          className="d-flex flex-column align-items-center w-100 pt-4"
          method="POST"
        >
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="username"
              placeholder="username..."
            />
            <label htmlFor="username">Username</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="password"
              className="form-control"
              id="password"
              placeholder="password..."
            />
            <label htmlFor="password">Password</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="name"
              placeholder="name..."
            />
            <label htmlFor="name">Name</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              id="email"
              placeholder="email..."
            />
            <label htmlFor="email">Email</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="location"
              placeholder="location..."
            />
            <label htmlFor="location">Location</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="openingHrs"
              placeholder="openingHrs..."
            />
            <label htmlFor="openingHrs">Opening Hours</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="closingHrs"
              placeholder="closingHrs..."
            />
            <label htmlFor="closingHrs">Closing Hours</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="apptDuration"
              placeholder="apptDuration..."
            />
            <label htmlFor="apptDuration">Appointment Duration</label>
          </div>
          <div className="input-group mb-3">
            <input type="file" className="form-control" id="license" />
            <label className="input-group-text" htmlFor="license">
              Upload
            </label>
          </div>
          <div className="d-grid">
            <button
              style={{
                backgroundColor: "#128983",
              }}
              className="btn btn-success btn-lg"
            >
              Register
            </button>
          </div>
          <div className="my-4">Already have an account? Login here!</div>
        </form>
      </div>
    </>
  );
}
