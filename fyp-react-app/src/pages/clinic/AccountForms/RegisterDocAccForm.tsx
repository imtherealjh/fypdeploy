import Multiselect from "multiselect-react-dropdown";
import { useState, ChangeEvent, useEffect, FormEvent } from "react";

import { CgMathPlus } from "react-icons/cg";
import axios from "../../../api/axios";
import { IObjectKeys } from "../../../hooks/types";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { useNavigate } from "react-router-dom";

interface ScheduleInputs extends IObjectKeys {
  day: string;
  startTime: string;
  endTime: string;
}

interface DoctorInputs extends IObjectKeys {
  username: string;
  password: string;
  name: string;
  email: string;
  profile: string;
  specialty: Array<string>;
  schedule: Array<ScheduleInputs>;
}

const defaultVal: DoctorInputs = {
  username: "",
  password: "",
  name: "",
  email: "",
  profile: "",
  specialty: [],
  schedule: [],
};

export default function DoctorAccount() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [doctorInput, setDoctorInput] = useState([defaultVal]);
  const [speciality, setSpeciality] = useState([]);

  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();
    const fetchData = async () => {
      const response = await axios.get("/public/getAllSpecialty", {
        signal: controller.signal,
      });

      const _specialty = response.data.map((obj: any) => {
        return obj["type"];
      });

      isMounted && setSpeciality(_specialty);
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  const handleDoctorChange = (
    event: ChangeEvent<HTMLInputElement>,
    idx: number
  ) => {
    setDoctorInput((prev) =>
      prev.map((el, index) =>
        index === idx
          ? {
              ...el,
              [event.target.name]: event.target.value,
            }
          : el
      )
    );
  };

  const handleScheduleChange = (
    event: ChangeEvent<any>,
    doctorIdx: number,
    scheduleIdx: number
  ) => {
    setDoctorInput((prev) =>
      prev.map((el, doctorI) =>
        doctorIdx === doctorI
          ? {
              ...el,
              ["schedule"]: el.schedule.map((elem, schedI) =>
                scheduleIdx === schedI
                  ? {
                      ...elem,
                      [event.target.name]: event.target.value,
                    }
                  : elem
              ),
            }
          : el
      )
    );
  };

  const handleFormSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      await axiosPrivate.post("/clinicOwner/registerDoctor", doctorInput);
      alert("Doctors has been successfully registered");
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

  return (
    <>
      <form method="POST" onSubmit={handleFormSubmit}>
        {doctorInput.map((customInput, idx) => (
          <div className="mt-2" key={idx}>
            <h4>Doctor {idx + 1}</h4>
            <div className="row g-2 align-content-center justify-content-center">
              <div className="col-6">
                <input
                  type="text"
                  className="form-control"
                  name="username"
                  placeholder="Username"
                  aria-label="Username"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleDoctorChange(event, idx)
                  }
                  value={customInput.username || ""}
                />
              </div>
              <div className="col-6">
                <input
                  type="password"
                  className="form-control"
                  name="password"
                  placeholder="Password"
                  aria-label="Password"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleDoctorChange(event, idx)
                  }
                  value={customInput.password || ""}
                />
              </div>

              <div className="col-6">
                <input
                  type="text"
                  className="form-control"
                  name="name"
                  placeholder="Name"
                  aria-label="Name"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleDoctorChange(event, idx)
                  }
                  value={customInput.name || ""}
                />
              </div>

              <div className="col-6">
                <input
                  type="text"
                  className="form-control"
                  name="email"
                  placeholder="Email"
                  aria-label="Email"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleDoctorChange(event, idx)
                  }
                  value={customInput.email || ""}
                />
              </div>

              <div className="col-12">
                <input
                  type="text"
                  className="form-control"
                  name="profile"
                  placeholder="Profile"
                  aria-label="Profile"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleDoctorChange(event, idx)
                  }
                  value={customInput.profile || ""}
                />
              </div>

              <div className="col-12">
                <Multiselect
                  style={{
                    searchBox: { paddingLeft: "0.55rem", background: "white" },
                  }}
                  placeholder="Select Doctor Specialty"
                  onSelect={(e) => (doctorInput[idx]["specialty"] = e)}
                  onRemove={(e) => (doctorInput[idx]["specialty"] = e)}
                  selectedValues={customInput.specialty}
                  options={speciality}
                  isObject={false}
                  showArrow
                  showCheckbox
                />
              </div>

              <div className="col-12 row">
                <div
                  style={{ margin: "0.5rem 0" }}
                  className="col-12 d-flex align-items-center justify-content-between"
                >
                  <h5 style={{ margin: "0" }}>Schedule</h5>
                  <button
                    type="button"
                    style={{ border: "none" }}
                    onClick={() =>
                      setDoctorInput((prev) =>
                        prev.map((el, index) =>
                          idx === index
                            ? {
                                ...el,
                                ["schedule"]: [
                                  ...el.schedule,
                                  {} as ScheduleInputs,
                                ],
                              }
                            : el
                        )
                      )
                    }
                  >
                    <CgMathPlus size={"1.25rem"} />
                  </button>
                </div>

                {customInput.schedule != null &&
                  customInput.schedule.map((scheduleInput, i) => (
                    <div className="col-12 d-flex gap-2 mt-1" key={i}>
                      <select
                        style={{ height: "100%" }}
                        name="day"
                        defaultValue={scheduleInput.day || "DEFAULT"}
                        className="form-select form-select-sm"
                        aria-label=".form-select-sm example"
                        onChange={(event: ChangeEvent<HTMLSelectElement>) =>
                          handleScheduleChange(event, idx, i)
                        }
                      >
                        <option value="DEFAULT" disabled>
                          Open this to select schedule days...
                        </option>
                        <option value="MONDAY">MONDAY</option>
                        <option value="TUESDAY">TUESDAY</option>
                        <option value="WEDNESDAY">WEDNESDAY</option>
                        <option value="THURSDAY">THURSDAY</option>
                        <option value="FRIDAY">FRIDAY</option>
                        <option value="SATURDAY">SATURDAY</option>
                        <option value="SUNDAY">SUNDAY</option>
                      </select>

                      <input
                        type="text"
                        className="form-control"
                        name="startTime"
                        placeholder="Starting Hours (HH:mm i.e. 12:00) "
                        aria-label="startTime"
                        onChange={(event: ChangeEvent<HTMLInputElement>) =>
                          handleScheduleChange(event, idx, i)
                        }
                        value={scheduleInput.startTime || ""}
                      />

                      <input
                        type="text"
                        className="form-control"
                        name="endTime"
                        placeholder="Stop Hours (HH:mm i.e. 15:00)"
                        aria-label="endTime"
                        onChange={(event: ChangeEvent<HTMLInputElement>) =>
                          handleScheduleChange(event, idx, i)
                        }
                        value={scheduleInput.endTime || ""}
                      />
                    </div>
                  ))}
              </div>
            </div>
          </div>
        ))}
        <button
          type="button"
          className="w-100 mt-3 btn btn-danger btn-lg"
          onClick={() => {
            setDoctorInput([...doctorInput, defaultVal]);
          }}
        >
          Add additional doctor
        </button>
        <button type="submit" className="w-100 mt-2 btn btn-success btn-lg">
          Submit
        </button>
      </form>
    </>
  );
}
