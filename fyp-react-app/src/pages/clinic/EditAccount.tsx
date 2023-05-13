import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, ChangeEvent, useState } from "react";

import Multiselect from "multiselect-react-dropdown";
import { CgMathPlus, CgMathMinus } from "react-icons/cg";

import axios from "axios";
import axiosIn from "../../api/axios";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import { IObjectKeys } from "../../hooks/types";

interface ScheduleInputs extends IObjectKeys {
  day: string;
  startTime: string;
  endTime: string;
}

export default function EditAccount() {
  const navigate = useNavigate();
  const obj = useLocation()?.state;
  const axiosPrivate = useAxiosPrivate();
  const [accountEnabled, setAccountEnabled] = useState(false);
  const [staffInput, setStaffInput] = useState<any>([]);
  const [speciality, setSpeciality] = useState([]);

  let staffEndPoint = "";
  if (obj.role == "DOCTOR") {
    staffEndPoint = "Doctor";
  } else if (obj.role == "FRONT_DESK") {
    staffEndPoint = "Clerk";
  } else if (obj.role == "NURSE") {
    staffEndPoint = "Nurse";
  }

  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();

    const fetchData = () => {
      let endpoints = [
        staffEndPoint != ""
          ? axiosPrivate.get(
              `/clinicOwner/get${staffEndPoint}ById?id=${obj.accountId}`,
              {
                signal: controller.signal,
              }
            )
          : null,
        obj.role == "DOCTOR"
          ? axiosIn.get("/public/getAllSpecialty", {
              signal: controller.signal,
            })
          : null,
      ];

      axios.all(endpoints.map((endpoint) => endpoint)).then(
        axios.spread((staff, speciality) => {
          const staffData = staff?.data?.staffAccount;
          staffData.specialty =
            staffData?.doctorSpecialty?.map((obj: any) => obj["type"]) ?? null;
          delete staffData?.doctorSpecialty;

          staffData.staffId = obj.accountId;

          staffData.schedule = staffData?.doctorSchedule;
          delete staffData?.doctorSchedule;

          const _specialty =
            speciality != null
              ? speciality?.data?.map((obj: any) => obj["type"])
              : [];

          isMounted && setStaffInput(staffData);
          isMounted && setAccountEnabled(staff?.data?.enabled);
          isMounted && setSpeciality(_specialty);
        })
      );
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  const handleStaffChange = (event: ChangeEvent<HTMLInputElement>) => {
    setStaffInput((prev: any) => ({
      ...prev,
      [event.target.name]: event.target.value,
    }));
  };

  const handleScheduleChange = (
    event: ChangeEvent<any>,
    scheduleIdx: number
  ) => {
    setStaffInput((prev: any) => ({
      ...prev,
      ["schedule"]: prev.schedule.map((el: any, scheduleI: number) =>
        scheduleIdx === scheduleI
          ? {
              ...el,
              [event.target.name]: event.target.value,
            }
          : el
      ),
    }));
  };

  const handleFormSubmit = async () => {
    try {
      await axiosPrivate.put(`/clinicOwner/update${staffEndPoint}`, staffInput);
      alert("Staff has been successfully updated");
      navigate("/clinic/manage-account", { replace: true });
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
      <h1>Edit account</h1>
      <div>
        <div className="mt-2">
          <div className="row g-2 align-content-center justify-content-center">
            <div className="col-6">
              <input
                type="text"
                className="form-control"
                name="name"
                placeholder="Name"
                aria-label="Name"
                onChange={handleStaffChange}
                value={staffInput.name || ""}
              />
            </div>

            <div className="col-6">
              <input
                type="text"
                className="form-control"
                name="email"
                placeholder="Email"
                aria-label="Email"
                onChange={handleStaffChange}
                value={staffInput.email || ""}
              />
            </div>

            {obj.role == "DOCTOR" && (
              <>
                <div className="col-12">
                  <input
                    type="text"
                    className="form-control"
                    name="profile"
                    placeholder="Profile"
                    aria-label="Profile"
                    onChange={handleStaffChange}
                    value={staffInput.profile || ""}
                  />
                </div>

                <div className="col-12">
                  <Multiselect
                    style={{
                      searchBox: {
                        paddingLeft: "0.55rem",
                        background: "white",
                      },
                    }}
                    placeholder="Select Doctor Specialty"
                    onSelect={(e) =>
                      setStaffInput((prev: any) => ({
                        ...prev,
                        ["specialty"]: e,
                      }))
                    }
                    onRemove={(e) =>
                      setStaffInput((prev: any) => ({
                        ...prev,
                        ["specialty"]: e,
                      }))
                    }
                    displayValue="type"
                    selectedValues={staffInput.specialty}
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
                      onClick={() => {
                        setStaffInput((prev: any) => ({
                          ...prev,
                          ["schedule"]: [
                            ...prev.schedule,
                            {} as ScheduleInputs,
                          ],
                        }));
                      }}
                    >
                      <CgMathPlus size={"1.25rem"} />
                    </button>
                  </div>
                  {staffInput.schedule != null &&
                    staffInput.schedule.map((scheduleInput: any, i: number) => (
                      <div className="col-12 d-flex gap-2 mt-1" key={i}>
                        <select
                          style={{ height: "100%" }}
                          name="day"
                          defaultValue={scheduleInput.day || "DEFAULT"}
                          className="form-select form-select-sm"
                          aria-label=".form-select-sm example"
                          onChange={(event: ChangeEvent<HTMLSelectElement>) =>
                            handleScheduleChange(event, i)
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
                            handleScheduleChange(event, i)
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
                            handleScheduleChange(event, i)
                          }
                          value={scheduleInput.endTime || ""}
                        />

                        <button
                          type="button"
                          className="btn btn-danger"
                          onClick={() =>
                            setStaffInput((prev: any) => ({
                              ...prev,
                              ["schedule"]: prev.schedule.filter(
                                (el: any, idx: number) => i !== idx
                              ),
                            }))
                          }
                        >
                          <CgMathMinus />
                        </button>
                      </div>
                    ))}
                </div>
              </>
            )}
          </div>
        </div>
        <div className="d-grid gap-2 mt-2">
          <button
            type="button"
            className="btn btn-success"
            onClick={handleFormSubmit}
          >
            Submit
          </button>
          {accountEnabled && (
            <button
              type="button"
              className="btn btn-danger"
              onClick={async () => {
                try {
                  await axiosPrivate.put(
                    `/clinicOwner/suspend${staffEndPoint}?id=${obj.accountId}`,
                    staffInput
                  );
                  alert("Staff has been successfully suspended");
                  navigate("/clinic/manage-account", { replace: true });
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
              }}
            >
              Suspend Account
            </button>
          )}
          {!accountEnabled && (
            <button
              type="button"
              className="btn btn-warning"
              onClick={async () => {
                try {
                  await axiosPrivate.put(
                    `/clinicOwner/activate${staffEndPoint}?id=${obj.accountId}`,
                    staffInput
                  );
                  alert("Staff has been successfully activated");
                  navigate("/clinic/manage-account", { replace: true });
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
              }}
            >
              Activate Account
            </button>
          )}
        </div>
      </div>
    </>
  );
}
