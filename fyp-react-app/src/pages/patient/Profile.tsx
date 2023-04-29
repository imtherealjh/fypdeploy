export default function Profile() {
  return (
    <>
      <h1> Profile Page</h1>
      <div className="d-flex flex-row flex-wrap gap-3">
        <div
          style={{ flex: "1 1 auto" }}
          className="d-flex flex-column align-self-center"
        >
          <img
            src="https://via.placeholder.com/200"
            alt="Profile Img"
            className="align-self-center rounded-circle"
          />
          <h5 className="align-self-center mt-2">Placeholder Name</h5>
        </div>

        <div
          className="p-3 w-100"
          style={{ flex: "1 1 65%", backgroundColor: "#DCDCDC" }}
        >
          <div className="mb-3 row">
            <label
              htmlFor="inputName"
              className="col-sm-2 fw-bold col-form-label"
            >
              Name
            </label>
            <div className="col-sm-10">
              <input type="text" className="form-control" id="inputName" />
            </div>
          </div>
          <div className="mb-3 row">
            <label
              htmlFor="inputEmail"
              className="col-sm-2 fw-bold col-form-label"
            >
              Email
            </label>
            <div className="col-sm-10">
              <input type="email" className="form-control" id="inputEmail" />
            </div>
          </div>
          <div className="mb-3 row">
            <label
              htmlFor="inputPhone"
              className="col-sm-2 fw-bold col-form-label"
            >
              Phone
            </label>
            <div className="col-sm-10">
              <input type="number" className="form-control" id="inputPhone" />
            </div>
          </div>
          <div className="mb-3 row">
            <label
              htmlFor="inputAddress"
              className="col-sm-2 fw-bold col-form-label"
            >
              Address
            </label>
            <div className="col-sm-10">
              <input type="text" className="form-control" id="inputAddress" />
            </div>
          </div>

          <div className="d-grid">
            <button type="button" className="btn btn-primary">
              Update
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
