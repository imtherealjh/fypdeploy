import { useEffect, useState } from "react";

import { Calendar, DateObject } from "react-multi-date-picker";

import PatientList from "../../components/PatientList";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import ViewArticles from "../../components/ViewArticles";

function Dashboard() {
  const axiosPrivate = useAxiosPrivate();
  const [value, setValue] = useState<any>(new DateObject());
  const [editable, setEditable] = useState(false);
  const [data, setData] = useState<any>({
    patientList: [],
    noOfPatients: 0,
  });

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get(
          `/staff/getPatientsByDate?apptDate=${value}`,
          {
            signal: controller.signal,
          }
        );

        const selectedDate = new Date(value);
        selectedDate.setHours(0, 0, 0, 0);

        const currentDate = new Date();
        currentDate.setHours(0, 0, 0, 0);

        isMounted &&
          setEditable(currentDate.getTime() === selectedDate.getTime());
        isMounted && setData(response.data);
      } catch (err) {
        console.log(err);
      }
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
        <ViewArticles />
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
            <Calendar
              format="YYYY-MM-DD"
              value={value}
              onChange={(date: DateObject) => setValue(date.format())}
            />
          </div>
        </div>
        <div className="mt-3">
          <PatientList editable={editable} apptDetails={data.patientList} />
        </div>
      </div>
    </>
  );
}

export default Dashboard;
