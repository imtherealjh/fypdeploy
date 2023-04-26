import { useEffect, useState } from "react";
import { Calendar, DateObject } from "react-multi-date-picker";
import DatePanel from "react-multi-date-picker/plugins/date_panel";

import "/node_modules/react-multi-date-picker/styles/layouts/mobile.css";
import Multiselect from "multiselect-react-dropdown";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useNavigate } from "react-router-dom";

export default function CreateAppointmentSlots() {
  const [value, setValue] = useState<any>([]);
  const [doctor, setDoctor] = useState<any>([]);
  const [selectedDoctor, setSelectedDoctor] = useState([]);

  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  const handleGenerateAll = async () => {
    console.log(value);
    try {
      await axiosPrivate.post(
        `/clinicOwner/generateClinicAppointmentSlots`,
        value
      );
      alert(
        "All of the doctors' appointment slots for the selected dates have been generated"
      );
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

  const handleGeneratePartial = async () => {
    console.log(selectedDoctor.map((item: any) => item.id));
    try {
      await axiosPrivate.post(`/clinicOwner/generateAppointmentSlots`, {
        doctorIds: selectedDoctor.map((item: any) => item.id),
        apptDates: value,
      });
      alert(
        "All of the selected doctors' appointment slots for the selected dates have been generated"
      );
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

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      let response = await axiosPrivate.get(`/clinicOwner/getAllDoctors`, {
        signal: controller.signal,
      });

      isMounted && setDoctor(response.data);
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  return (
    <>
      <h1>Appointment Slot Creation</h1>
      <div className="d-flex flex-column gap-3">
        <div className="d-flex justify-content-center">
          <Calendar
            format="YYYY-MM-DD"
            value={value}
            onChange={(dates: DateObject[]) =>
              setValue(dates.map((date) => date.format()))
            }
            plugins={[<DatePanel sort="date" />]}
          />
        </div>
        <div className="d-grid">
          <button
            type="button"
            className="btn btn-primary"
            onClick={handleGenerateAll}
          >
            Generate for all doctors
          </button>
        </div>
        <div
          style={{ background: "white" }}
          className="d-flex gap-3 flex-column w-100 p-3 shadow p-3 mb-5 bg-white rounded"
        >
          <h4 style={{ margin: 0 }}>Schedule for particular doctor</h4>
          <Multiselect
            style={{
              searchBox: { paddingLeft: "0.55rem", background: "white" },
            }}
            placeholder="Select Doctors"
            displayValue="name"
            onSelect={(e) => setSelectedDoctor(e)}
            onRemove={(e) => setSelectedDoctor(e)}
            selectedValues={selectedDoctor}
            options={doctor.map((val: any) => {
              return { id: val.doctorId, name: val.name };
            })}
            showArrow
            showCheckbox
          />
          <div className="d-grid">
            <button
              type="button"
              className="btn btn-primary"
              onClick={handleGeneratePartial}
            >
              Generate for selected doctors
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
