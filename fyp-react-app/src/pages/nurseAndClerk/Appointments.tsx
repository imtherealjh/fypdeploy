import useAxiosPrivate from "../../lib/useAxiosPrivate";

import { useState, useEffect, ChangeEvent } from "react";
import { CgSearch } from "react-icons/cg";
import DatePicker, { DateObject } from "react-multi-date-picker";

import UpdateAppointmentComponent from "../AppointmentComponents/UpdateAppointment";
import DeleteAppointmentComponent from "../AppointmentComponents/DeleteAppointment";

export default function Appointments() {
  const axiosPrivate = useAxiosPrivate();

  const [appointments, setAppointments] = useState<any>([]);
  const [data, setData] = useState<any>([]);
  const [value, setValue] = useState<any>(new DateObject());

  const [searchInput, setSearchInput] = useState("");
  const [btnClicked, setBtnClicked] = useState("");

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      let response = await axiosPrivate.get(
        `/staff/getAppointmentByDate?date=${value}`,
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
  }, [value]);

  return (
    <>
      <h1>Appointments</h1>
      <div className="dashboard-container">
        <div className="d-flex flex-column justify-content-end gap-2">
          <DatePicker
            style={{
              height: "max-content",
              width: "max-content",
              alignSelf: "flex-end",
            }}
            format="YYYY-MM-DD"
            value={value}
            onChange={(date: DateObject) => setValue(date.format())}
          />
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              placeholder={`Search Clinic`}
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
                <th>Patient Name</th>
                <th>Doctor</th>
                <th>Time</th>
                <th>Status</th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {appointments.length < 1 && (
                <tr>
                  <td colSpan={6}>No data available...</td>
                </tr>
              )}
              {appointments
                .filter(
                  (obj: any) =>
                    obj.patientName.includes(searchInput) ||
                    obj.doctorName.includes(searchInput) ||
                    obj.apptTime.includes(searchInput) ||
                    obj.status.includes(searchInput)
                )
                .map((appointment: any, idx: number) => (
                  <tr key={idx}>
                    <td>{appointment.patientName}</td>
                    <td>{appointment.doctorName}</td>
                    <td>{appointment.apptTime}</td>
                    <td>{appointment.status}</td>
                    <td colSpan={2}>
                      <div className="d-flex justify-content-end gap-2 flex-wrap">
                        {appointment.status !== "COMPLETED" && (
                          <>
                            <button
                              id="updateBtn"
                              type="button"
                              className="btn btn-warning"
                              onClick={(
                                e: React.MouseEvent<HTMLButtonElement>
                              ) => {
                                setData(appointment);
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
                              onClick={(
                                e: React.MouseEvent<HTMLButtonElement>
                              ) => {
                                setData(appointment);
                                setBtnClicked(e.currentTarget.id);
                              }}
                              data-bs-toggle="modal"
                              data-bs-target="#modal"
                            >
                              Delete
                            </button>
                          </>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
      </div>
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
    </>
  );
}
