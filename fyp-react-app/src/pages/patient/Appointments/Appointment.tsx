import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useAxiosPrivate from "../../../lib/useAxiosPrivate";
import UpdateAppointmentComponent from "./UpdateAppointment";
import DeleteAppointmentComponent from "./DeleteAppointment";

export default function Appointment() {
  const [appointmentType, setAppointmentType] = useState("Past");
  const [btnClicked, setBtnClicked] = useState("");
  const [data, setData] = useState({});
  const [appointments, setAppointments] = useState<any>([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();
    const fetchData = async () => {
      console.log(`/patient/get${appointmentType}Appointment`);

      const response = await axiosPrivate.get(
        `/patient/get${appointmentType}Appointment`,
        {
          signal: controller.signal,
        }
      );

      isMounted && setAppointments(response.data);
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, [appointmentType]);

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
              onChange={() => setAppointmentType("Past")}
              checked={appointmentType === "Past"}
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
              onChange={() => setAppointmentType("Upcoming")}
              checked={appointmentType === "Upcoming"}
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
              {appointmentType === "Upcoming" ? (
                <>
                  <th scope="col"></th>
                  <th scope="col"></th>
                </>
              ) : null}
            </tr>
          </thead>
          <tbody>
            {appointments.length < 1 ? (
              <tr>
                <td colSpan={appointmentType === "Upcoming" ? 7 : 5}>
                  No data available....
                </td>
              </tr>
            ) : null}
            {appointments.map((value: any, idx: number) => (
              <tr key={value.appointmentId}>
                <th className="align-middle" scope="row">
                  {idx + 1}
                </th>
                <td className="align-middle">{value.clinicName}</td>
                <td className="align-middle">{value.doctorName}</td>
                <td className="align-middle">{value.apptDate}</td>
                <td className="align-middle">{value.apptTime}</td>
                {appointmentType === "Upcoming" ? (
                  <td colSpan={2}>
                    <div className="d-flex justify-content-end gap-2 flex-wrap">
                      <button
                        id="updateBtn"
                        type="button"
                        className="btn btn-warning"
                        onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                          setData(appointments[idx]);
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
                          setData(appointments[idx]);
                          setBtnClicked(e.currentTarget.id);
                        }}
                        data-bs-toggle="modal"
                        data-bs-target="#modal"
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                ) : null}
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
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
