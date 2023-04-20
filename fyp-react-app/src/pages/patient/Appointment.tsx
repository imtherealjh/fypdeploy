import { ChangeEvent, useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

export default function Appointment() {
  const [appointmentType, setAppointmentType] = useState("past");
  const [appointments, setAppointments] = useState<any>([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();
    const fetchData = async () => {
      const response = await axiosPrivate.get("/patient/getPastAppointment", {
        signal: controller.signal,
      });

      isMounted && setAppointments(response.data);
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  let isMounted = false;
  const handleChange = async (event: ChangeEvent<HTMLInputElement>) => {
    if (!isMounted) {
      isMounted = true;
      try {
        const response = await axiosPrivate.get(
          `/patient/get${event.target.value}Appointment`
        );
        console.log(response.data);
        isMounted && setAppointments(response.data);
      } catch (error) {
        console.log(error);
      }

      isMounted && setAppointmentType(event.target.id);
      isMounted = false;
    }
  };

  return (
    <>
      <div>
        <div className="d-flex justify-content-end">
          <Link to="book" state={useLocation().state}>
            <button className="btn btn-dark btn-lg" type="button">
              Book
            </button>
          </Link>
        </div>
        <div className="d-flex justify-content-end mt-2">
          <div className="d-grid gap-2 d-md-flex justify-content-md-end">
            <input
              type="radio"
              id="past"
              value="Past"
              className="btn-check"
              name="btnradio"
              autoComplete="off"
              onChange={handleChange}
              checked={appointmentType === "past"}
            />
            <label className="btn btn-outline-secondary" htmlFor="past">
              Past
            </label>

            <input
              type="radio"
              id="upcoming"
              value="Upcoming"
              className="btn-check"
              name="btnradio"
              autoComplete="off"
              onChange={handleChange}
              checked={appointmentType === "upcoming"}
            />
            <label className="btn btn-outline-secondary" htmlFor="upcoming">
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
              {appointmentType === "upcoming" && (
                <>
                  <th scope="col"></th>
                  <th scope="col"></th>
                </>
              )}
            </tr>
          </thead>
          <tbody>
            {appointments.length < 1 && <tr>No data available....</tr>}
            {appointments.map((value: any, idx: number) => (
              <tr key={value.appointmentId}>
                <th className="align-middle" scope="row">
                  {idx + 1}
                </th>
                <td className="align-middle">{value.clinicName}</td>
                <td className="align-middle">{value.doctorName}</td>
                <td className="align-middle">{value.apptDate}</td>
                <td className="align-middle">{value.apptTime}</td>
                {appointmentType === "upcoming" && (
                  <>
                    <td colSpan={2}>
                      <div className="d-flex justify-content-end gap-2 flex-wrap">
                        <button type="button" className="btn btn-warning">
                          Update
                        </button>
                        <button type="button" className="btn btn-danger">
                          Delete
                        </button>
                      </div>
                    </td>
                  </>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}
