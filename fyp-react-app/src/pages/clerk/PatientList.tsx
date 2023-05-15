import { useState, useEffect, ChangeEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import { CgSearch } from "react-icons/cg";

import useAxiosPrivate from "../../lib/useAxiosPrivate";
import Step2 from "../AppointmentComponents/Step2";

function PatientListPage() {
  const axiosPrivate = useAxiosPrivate();
  const [searchInput, setSearchInput] = useState("");

  const [formData, setFormData] = useState({});
  const navigate = useNavigate();

  const [doctor, setDoctor] = useState([]);
  const [patientList, setPatientList] = useState([]);

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchPatients = async () => {
      try {
        const response = await axiosPrivate.get("/staff/getAllPatients", {
          signal: controller.signal,
        });

        const doctorResponse = await axiosPrivate.get("/clerk/getAllDoctors", {
          signal: controller.signal,
        });

        isMounted && setPatientList(response.data);
        isMounted && setDoctor(doctorResponse.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchPatients();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  console.log(formData);

  return (
    <>
      <h1>Patient - Profile Overview</h1>
      <div>
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder={`Search Patient`}
            onChange={(e: ChangeEvent<HTMLInputElement>) =>
              setSearchInput(e.target.value)
            }
            aria-label="Search"
            aria-describedby="Search"
          />
          <button
            className="btn btn-outline-secondary"
            type="button"
            id="button-addon2"
          >
            <CgSearch />
            <span className="ms-2">Search</span>
          </button>
        </div>
        <table className="mt-2 table table-striped">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Patient Name</th>
              <th scope="col">Email</th>
              <th scope="col">Contact No</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            {patientList.length < 1 ? (
              <tr>
                <td colSpan={6}>No data available....</td>
              </tr>
            ) : null}
            {patientList
              .filter((obj: any) => {
                return (
                  obj.name.includes(searchInput) ||
                  obj.email.includes(searchInput) ||
                  JSON.stringify(obj.contactNo).includes(searchInput)
                );
              })
              .map((data: any, idx: number) => (
                <tr key={idx}>
                  <th className="align-middle" scope="row">
                    {idx + 1}
                  </th>
                  <td className="align-middle">{data.name}</td>
                  <td className="align-middle">{data.email}</td>
                  <td className="align-middle">{data.contactNo}</td>
                  <td>
                    <div className="d-flex gap-2">
                      <Link to="details" state={data}>
                        <button className="btn btn-primary">
                          View Records
                        </button>
                      </Link>
                      <button
                        type="button"
                        className="btn btn-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#modal"
                        onClick={() =>
                          setFormData((prev: any) => ({
                            ...prev,
                            patientId: data.patientId,
                          }))
                        }
                      >
                        Book Appointment
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
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
                <h5 className="modal-title">Book appointment</h5>
                <button
                  id="closeModalBtn"
                  style={{ display: "none" }}
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body d-flex gap-2 flex-column">
                <select
                  className="form-select"
                  defaultValue={"DEFAULT"}
                  aria-label="Open this to select doctor"
                  onChange={(event: ChangeEvent<HTMLSelectElement>) =>
                    setFormData((prev: any) => {
                      return {
                        ...prev,
                        doctorId: event.target.value,
                        originalApptId: event.target.value,
                      };
                    })
                  }
                >
                  <option value="DEFAULT" disabled>
                    Open this to select doctor
                  </option>
                  {doctor.map((val: any, idx: number) => (
                    <option key={idx} value={val.doctorId}>
                      {val.name}
                    </option>
                  ))}
                </select>
                <Step2 formData={formData} setFormData={setFormData} />
                <button
                  className="btn btn-primary"
                  onClick={async () => {
                    try {
                      await axiosPrivate.post(
                        `/clerk/bookAppointment`,
                        formData
                      );

                      alert("Appointment successfully created...");
                      document.getElementById("closeModalBtn")?.click();
                      navigate(0);
                    } catch (err: any) {
                      if (!err?.response) {
                        alert("No Server Response");
                      } else if (err.response?.status === 400) {
                        alert(err.response?.data.errors);
                      } else {
                        alert("Unknown error occured...");
                      }
                    }
                  }}
                >
                  Submit
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default PatientListPage;
