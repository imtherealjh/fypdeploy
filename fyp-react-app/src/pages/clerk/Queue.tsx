import { useEffect, useState } from "react";

import useAxiosPrivate from "../../lib/useAxiosPrivate";
import { DateObject } from "react-multi-date-picker";
import { useNavigate } from "react-router-dom";

function Queue() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [data, setData] = useState<any>({
    patientList: [],
    noOfPatients: 0,
  });

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      let response = await axiosPrivate.get(
        `/staff/getPatientsByDate?apptDate=${new DateObject().format(
          "YYYY-MM-DD"
        )}`,
        {
          signal: controller.signal,
        }
      );

      isMounted && setData(response.data);
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  const handleCheckIn = async (id: number) => {
    try {
      await axiosPrivate.post(`/clerk/checkInPatient?id=${id}`);
      alert("Successfully check in patient");
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
  };

  return (
    <>
      <h1>Queue</h1>
      <div>
        <div className="d-flex flex-column justify-content-center gap-2">
          <table className="table table-striped">
            <thead>
              <tr>
                <th>Patient Name</th>
                <th>Doctor</th>
                <th>Time</th>
                <th>Status</th>
                {new Date().toDateString() ===
                  new Date(data.patientList[0]?.date).toDateString() && (
                  <>
                    <th>Action</th>
                  </>
                )}
              </tr>
            </thead>
            <tbody>
              {data.patientList.length < 1 && (
                <tr>
                  <td colSpan={6}>No data available...</td>
                </tr>
              )}
              {data.patientList.map((appointment: any, idx: number) => (
                <tr key={idx}>
                  <td>{appointment.patientName}</td>
                  <td>{appointment.doctorName}</td>
                  <td>{appointment.apptTime}</td>
                  <td>{appointment.status}</td>
                  {new Date().toDateString() ===
                    new Date(appointment?.date).toDateString() && (
                    <>
                      <td>
                        {appointment.status === "BOOKED" && (
                          <button
                            className="btn btn-primary"
                            onClick={(e) => {
                              e.stopPropagation();
                              handleCheckIn(appointment.id);
                            }}
                          >
                            Check In
                          </button>
                        )}
                      </td>
                    </>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}

export default Queue;
