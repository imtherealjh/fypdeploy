import useAxiosPrivate from "../../lib/useAxiosPrivate";
import { useState, useEffect } from "react";
import Step2 from "../AppointmentComponents/Step2";
import { useNavigate } from "react-router-dom";

interface ClinicData {
  doctor: any;
}

export default function SearchClinic() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [specialtyType, setSpecialityType] = useState([]);
  const [input, setInputType] = useState<any>({});

  const [data, setDataType] = useState<ClinicData[]>([]);
  const [clickedData, setClickedData] = useState<any>({});

  const [formData, setFormData] = useState<any>({});

  const handleChange = (event: any) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputType((values: any) => ({ ...values, [name]: value }));
  };

  const handleSubmit = async () => {
    try {
      const response = await axiosPrivate.post(
        "/patient/getClinicsBySpecLoc",
        input
      );

      console.log(response);
      setDataType(response.data);
    } catch (err: any) {
      if (!err?.response) {
        alert("No Server Response");
      } else if (err.response?.status === 400) {
        alert(err.response?.data.errors);
      } else {
        alert("Unknown error");
      }
    }
  };

  const handleBookingSubmit = async () => {
    try {
      await axiosPrivate.post("/patient/bookAppointment", {
        apptId: formData.apptId,
      });
      alert("Appointment has been successfully created...");
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
    const controller = new AbortController();
    const fetchData = async () => {
      try {
        const response = await axiosPrivate.get("/public/getAllSpecialty", {
          signal: controller.signal,
        });

        const _specialty = response.data.map((obj: any) => {
          return obj["type"];
        });

        isMounted && setSpecialityType(_specialty);
      } catch (err) {
        console.log(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  return (
    <>
      <h1>Search Clinic</h1>
      <div>
        <div className="d-flex gap-2 flex-row flex-wrap">
          <select
            className="flex-fill"
            name="specialty"
            onChange={handleChange}
            defaultValue={"DEFAULT"}
          >
            <option value={"DEFAULT"}>Choose Specialty</option>
            {specialtyType.map((val, idx) => (
              <option key={idx} value={val}>
                {val}
              </option>
            ))}
          </select>
          <input
            className="flex-fill"
            type="text"
            name="location"
            onChange={handleChange}
          />
          <button className="btn btn-primary flex-fill" onClick={handleSubmit}>
            Submit
          </button>
        </div>
        <table className="mt-2 table table-striped">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Doctor</th>
              <th scope="col">Clinic</th>
              <th scope="col">Location</th>
              <th scope="col">Clinic Number</th>
              <th scope="col">Clinic Email</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {data.length < 1 ? (
              <tr>
                <td colSpan={6}>No data available....</td>
              </tr>
            ) : null}
            {data.map((clinic: any) => {
              return (
                <>
                  {clinic.doctor.map((doctor: any, idx: number) => (
                    <tr key={idx}>
                      <th className="align-middle" scope="row">
                        {idx + 1}
                      </th>
                      <td>{doctor.name}</td>
                      <td>{clinic.clinicName}</td>
                      <td>{clinic.location}</td>
                      <td>{clinic.contactNo}</td>
                      <td>{clinic.email}</td>
                      <td>
                        <button
                          className="btn btn-primary"
                          onClick={() => {
                            setClickedData(clinic);
                            setFormData((prev: any) => ({
                              ...prev,
                              ["doctorId"]: doctor.doctorId,
                            }));
                          }}
                          data-bs-toggle="modal"
                          data-bs-target="#modal"
                        >
                          Book
                        </button>
                      </td>
                    </tr>
                  ))}
                </>
              );
            })}
          </tbody>
        </table>
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
              <h5 className="modal-title">Book Appointment With Clinic</h5>
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
              <Step2 formData={formData} setFormData={setFormData} />
              <button className="btn btn-primary" onClick={handleBookingSubmit}>
                Book
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
