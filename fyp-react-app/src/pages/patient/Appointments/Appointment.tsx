import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useAxiosPrivate from "../../../lib/useAxiosPrivate";
import UpdateAppointmentComponent from "../../AppointmentComponents/UpdateAppointment";
import DeleteAppointmentComponent from "../../AppointmentComponents/DeleteAppointment";
import FeedbackComponent from "./FeedbackAppointment,";

export default function Appointment() {
  // for updating and deleting appointment
  const [btnClicked, setBtnClicked] = useState("");
  const [data, setData] = useState({});

  const [past, setPast] = useState(true);
  const [filteredAppt, setFilteredAppt] = useState<any>([]);

  const [appointments, setAppointments] = useState<any>([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();
    const fetchData = async () => {
      const response = await axiosPrivate.get(`/patient/getAllAppointments`, {
        signal: controller.signal,
      });

      isMounted && setAppointments(response.data);
      isMounted &&
        setFilteredAppt(
          response.data.filter(
            (obj: any) => new Date(obj.apptDate) < new Date()
          )
        );
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  return (
    <>
      <h1>Appointment</h1>
      <div>
        <div className="d-flex justify-content-end">
          <Link to="book">
            <button className="btn btn-dark btn-lg" type="button">
              Book
            </button>
          </Link>
        </div>
        <div className="d-flex justify-content-end mt-2">
          <div className="d-grid gap-2 d-md-flex justify-content-md-end">
            <input
              type="radio"
              id="Past"
              className="btn-check"
              name="btnradio"
              autoComplete="off"
              onChange={() => {
                setPast(true);
                setFilteredAppt(
                  appointments.filter(
                    (obj: any) => new Date(obj.apptDate) < new Date()
                  )
                );
              }}
              checked={past}
            />
            <label className="btn btn-outline-secondary" htmlFor="Past">
              Past
            </label>

            <input
              id="Upcoming"
              type="radio"
              className="btn-check"
              name="btnradio"
              autoComplete="off"
              onChange={() => {
                setPast(false);
                setFilteredAppt(
                  appointments.filter(
                    (obj: any) => new Date(obj.apptDate) >= new Date()
                  )
                );
              }}
              checked={!past}
            />
            <label className="btn btn-outline-secondary" htmlFor="Upcoming">
              Upcoming
            </label>
          </div>
        </div>

        <table className="mt-2 table table-striped">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Clinic</th>
              <th scope="col">Doctor</th>
              <th scope="col">Date</th>
              <th scope="col">Time</th>
              <th scope="col"></th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            {filteredAppt.length < 1 ? (
              <tr>
                <td colSpan={7}>No data available....</td>
              </tr>
            ) : null}
            {filteredAppt.map((value: any, idx: number) => (
              <tr key={value.appointmentId}>
                <th className="align-middle" scope="row">
                  {value.appointmentId}
                </th>
                <td className="align-middle">{value.clinicName}</td>
                <td className="align-middle">{value.doctorName}</td>
                <td className="align-middle">{value.apptDate}</td>
                <td className="align-middle">{value.apptTime}</td>
                <td colSpan={2}>
                  <div className="d-flex justify-content-end gap-2 flex-wrap">
                    {!past && value.status !== "COMPLETED" && (
                      <>
                        <button
                          id="updateBtn"
                          type="button"
                          className="btn btn-warning"
                          onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                            setData(filteredAppt[idx]);
                            setBtnClicked(e.currentTarget.id);
                          }}
                          data-bs-toggle="modal"
                          data-bs-target="#modal"
                        >
                          Update
                        </button>
                        <button
                          id="deleteBtn"
                          type="button"
                          className="btn btn-danger"
                          onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                            setData(filteredAppt[idx]);
                            setBtnClicked(e.currentTarget.id);
                          }}
                          data-bs-toggle="modal"
                          data-bs-target="#modal"
                        >
                          Delete
                        </button>
                      </>
                    )}
                    {past && value.status === "COMPLETED" && (
                      <>
                        <button
                          id="feedbackBtn"
                          type="button"
                          className="btn btn-primary"
                          onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                            setData(filteredAppt[idx]);
                            setBtnClicked(e.currentTarget.id);
                          }}
                          data-bs-toggle="modal"
                          data-bs-target="#modal"
                        >
                          Feedback
                        </button>
                      </>
                    )}
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
                <h5 className="modal-title">
                  {btnClicked === "updateBtn" && "Update Appointment"}
                  {btnClicked === "deleteBtn" && "Delete Appointment"}
                  {btnClicked === "feedbackBtn" && "Feedback"}
                </h5>
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
                {btnClicked === "updateBtn" && (
                  <UpdateAppointmentComponent data={data} />
                )}
                {btnClicked === "deleteBtn" && (
                  <DeleteAppointmentComponent data={data} />
                )}
                {btnClicked === "feedbackBtn" && (
                  <FeedbackComponent data={data} />
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
