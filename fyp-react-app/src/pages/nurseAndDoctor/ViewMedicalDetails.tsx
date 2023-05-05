import { useLocation, useNavigate } from "react-router-dom";
import { ChangeEvent, useEffect, useState } from "react";
import PatientDetails from "../../components/PatientDetails";
import useAxiosPrivate from "../../lib/useAxiosPrivate";

export default function ViewMedicalDetails() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [apptData, setApptData] = useState([]);

  //edits
  const [edit, setEdit] = useState(false);
  const [currentAppt, setCurrentAppt] = useState<any>([]);
  const [formData, setFormData] = useState<any>({});

  const navigate = useNavigate();

  const axiosPrivate = useAxiosPrivate();
  const obj = useLocation()?.state;

  const patientData = obj?.patientMedicalRecords;
  patientData.name = obj.name;
  patientData.contactNo = obj.contactNo;
  patientData.sex = obj.gender;
  patientData.dateOfBirth = obj.dob;
  patientData.patientId = obj.patientId;
  patientData.emergencyContact = obj.emergencyContact;
  patientData.emergencyContactNo = obj.emergencyContactNo;

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get(
          `/staff/getAppointmentDetails?patientId=${obj.patientId}`
        );

        const { pastAppt, todayAppt } = response.data;

        isMounted && setApptData(pastAppt);
        isMounted && setCurrentAppt(todayAppt);
        isMounted && setFormData(patientData);
        isMounted && setIsLoaded(true);
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  useEffect(() => {
    setEdit(formData != patientData);
  }, [formData]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFormData((prev: any) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async () => {
    try {
      await axiosPrivate.put("/staff/updateMedicalRecords", formData);

      document.getElementById("closeModalBtn")?.click();

      alert("Medical Record updated successfully");
      navigate(0);
    } catch (err: any) {
      if (!err?.response) {
        alert("No Server Response");
      } else if (err.response?.status === 400) {
        alert(err.response?.data.errors);
      } else {
        alert("Unknown error occured...");
      }
      console.log(err);
    }
  };

  if (!isLoaded) {
    // Render a loading indicator until the data is loaded
    return <div>Loading...</div>;
  }

  return (
    <>
      <h1>Patient - Profile Overview</h1>
      <div className="d-flex flex-column gap-2">
        <div
          style={{
            position: "relative",
            background: "white",
            padding: "1.2rem",
            borderRadius: "1.2rem",
          }}
        >
          {currentAppt.length > 0 && !edit && (
            <button
              style={{ position: "absolute", right: "1.2rem" }}
              className="btn btn-success"
              onClick={() => setEdit(!edit)}
            >
              Edit
            </button>
          )}
          <PatientDetails
            patient={formData}
            handleChange={handleChange}
            editable={edit}
          />
          <div className="d-flex justify-content-end gap-2">
            {currentAppt.length > 0 && edit && (
              <button
                className="btn btn-danger"
                onClick={() => {
                  setEdit(false);
                  setFormData(patientData);
                }}
              >
                Cancel
              </button>
            )}
            {currentAppt.length > 0 && (
              <button
                className="btn btn-primary"
                data-bs-toggle="modal"
                data-bs-target="#modal"
              >
                Complete Appointment
              </button>
            )}
          </div>
        </div>

        <div className="mt-3">
          <h5>Appointment History</h5>
          <table className="mt-2 table table-striped">
            <thead>
              <tr>
                <th scope="col">Clinic</th>
                <th scope="col">Doctor</th>
                <th scope="col">Date</th>
                <th scope="col">Time</th>
                <th scope="col">Diagnosis</th>
              </tr>
            </thead>
            <tbody>
              {apptData.length < 1 ? (
                <tr>
                  <td colSpan={5}>No data available....</td>
                </tr>
              ) : null}
              {apptData.map((data: any, idx: number) => (
                <tr key={idx}>
                  <th scope="row">{data.clinicName}</th>
                  <td>{data.doctorName}</td>
                  <td>{data.apptDate}</td>
                  <td>{data.apptTime}</td>
                  <td>{data.diagnostic}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Modal... */}
      <div
        className="modal fade"
        id="modal"
        data-bs-keyboard="false"
        tabIndex={-1}
        aria-labelledby="modal"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content py-3">
            <div className="modal-header">
              <h5 className="modal-title">Update appointment diagnosis</h5>
              <button
                id="closeModalBtn"
                style={{ display: "none" }}
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              <div className="form-floating mb-3">
                <select
                  style={{ paddingTop: 0, paddingBottom: 0 }}
                  name="appointmentId"
                  className="form-select"
                  defaultValue={"DEFAULT"}
                  aria-label="Select appointment..."
                  onChange={(e: ChangeEvent<HTMLSelectElement>) => {
                    const selectedIndex = e.target.options.selectedIndex;
                    formData.diagnostic =
                      currentAppt[selectedIndex - 1]?.diagnostic;
                    setFormData((prev: any) => ({
                      ...prev,
                      [e.target.name]: e.target.value,
                    }));
                  }}
                >
                  <option value={"DEFAULT"} disabled>
                    Select appointments...
                  </option>
                  {currentAppt.map((data: any, idx: any) => (
                    <option
                      key={idx}
                      value={data.appointmentId}
                    >{`${data.apptDate} - ${data.apptTime}`}</option>
                  ))}
                </select>
              </div>

              <div className="form-floating mb-3">
                <input
                  type="text"
                  className={`form-control`}
                  id="diagnostic"
                  name="diagnostic"
                  onChange={handleChange}
                  value={`${formData.diagnostic || ""}`}
                />
                <label htmlFor="diagnostic">Diagnostic</label>
              </div>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
              <button
                type="button"
                className="btn btn-primary"
                onClick={handleSubmit}
              >
                Save changes
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
