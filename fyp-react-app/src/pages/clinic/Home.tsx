import { useEffect, useState } from "react";
import PatientList from "../../components/PatientList";

import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { format } from "date-fns";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

function Dashboard() {
  const axiosPrivate = useAxiosPrivate();
  const [value, setValue] = useState<any>(new Date());
  const [data, setData] = useState<any>({
    patientList: [],
    noOfPatients: 0,
  });

  useEffect(() => {
    const formattedDate = format(value, "dd/MM/yyyy");
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      let response = await axiosPrivate.get(
        `/doctor/getPatientsByDate?apptDate=${formattedDate}`,
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
  }, [value]);

  return (
    <>
      <h1>Dashboard</h1>
      <div className="dashboard-container">
        <div className="d-flex flex-wrap gap-2">
          <div
            style={{
              background: "#737373",
              color: "white",
              margin: "1rem",
              borderRadius: "1rem",
              flex: "1 1 25%",
            }}
            className="col p-3 d-flex flex-column justify-content-center"
          >
            <span
              style={{
                fontSize: "1.2rem",
                fontWeight: "bold",
                textAlign: "center",
              }}
            >
              Visits for Today:
            </span>
            <span style={{ fontSize: "1.1rem", textAlign: "center" }}>
              {data.noOfPatients}
            </span>
          </div>
          <div className="col d-flex justify-content-center">
            <Calendar onChange={setValue} value={value} />
          </div>
        </div>
        <div className="mt-3">
          <PatientList apptDetails={data.patientList} />
        </div>
      </div>
    </>
  );
}

export default Dashboard;
