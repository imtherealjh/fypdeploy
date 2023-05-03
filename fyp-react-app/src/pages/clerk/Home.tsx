import AppointmentList from "../../components/AppointmentList";

import useAxiosPrivate from "../../lib/useAxiosPrivate";

import { useState, useEffect } from "react";
import { Calendar, DateObject } from "react-multi-date-picker";

export default function ClerkHome() {
  const axiosPrivate = useAxiosPrivate();
  const [value, setValue] = useState<any>(new DateObject());
  const [data, setData] = useState<any>({
    patientList: [],
    noOfPatients: 0,
  });

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      let response = await axiosPrivate.get(
        `/staff/getPatientsByDate?apptDate=${value}`,
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
        <div className="d-flex flex-column justify-content-center gap-2">
          <Calendar
            className="align-self-center"
            format="YYYY-MM-DD"
            value={value}
            onChange={(date: DateObject) => setValue(date.format())}
          />

          <span style={{ fontSize: "1.1rem", textAlign: "center" }}>
            <AppointmentList appointmentsList={data.patientList} />
          </span>
        </div>
      </div>
    </>
  );
}
