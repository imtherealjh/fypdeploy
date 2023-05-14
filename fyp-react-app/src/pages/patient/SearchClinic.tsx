import useAxiosPrivate from "../../lib/useAxiosPrivate";
import { useState, useEffect } from "react";
import Step2 from "../AppointmentComponents/Step2";
import { useNavigate } from "react-router-dom";

export default function SearchClinic() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  const [page, setPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);
  const [specialtyType, setSpecialityType] = useState([]);
  const [input, setInputType] = useState<any>({});

  const [data, setData] = useState([]);
  const [clickedData, setClickedData] = useState<any>({});

  const [formData, setFormData] = useState<any>({});

  const handleChange = (event: any) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputType((values: any) => ({ ...values, [name]: value }));
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

  const handleSubmit = async () => {
    console.log(input);
    try {
      const response = await axiosPrivate.post(
        `/patient/getClinicsBySpecLoc?page=${page}`,
        input
      );
      const { content, current_page, total_pages } = response.data;

      setData(content);
      setPage(current_page);
      setTotalPage(total_pages);
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

  const pagination: any = [];

  let startPage, endPage;
  startPage = endPage = page;

  if (totalPage > 1) {
    endPage = page + 1 != totalPage ? endPage + 1 : totalPage - 1;
    endPage = page == 0 && totalPage > 2 ? endPage + 1 : endPage;
    startPage = page != 0 ? page - 1 : 0;
    startPage = page + 1 == totalPage ? startPage - 1 : startPage;

    while (startPage <= endPage) {
      pagination.push(startPage++);
    }
  }

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
            {data.map((doctor: any, idx: any) => (
              <tr key={idx}>
                <th className="align-middle" scope="row">
                  {idx + 1}
                </th>
                <td>{doctor.name}</td>
                <td>{doctor.clinicName}</td>
                <td>{doctor.clinicLocation}</td>
                <td>{doctor.clinicContact}</td>
                <td>{doctor.clinicEmail}</td>
                <td>
                  <button
                    className="btn btn-primary"
                    onClick={() => {
                      setClickedData(doctor);
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
          </tbody>
        </table>
        {totalPage > 1 && (
          <nav>
            <ul style={{ background: "transparent" }} className="pagination">
              {pagination.map((el: any) => (
                <li className="page-item">
                  <a
                    className={el === page ? `page-link active` : "page-link"}
                    href="#"
                    onClick={() => {
                      setPage(el);
                      handleSubmit();
                    }}
                  >
                    {el + 1}
                  </a>
                </li>
              ))}
            </ul>
          </nav>
        )}
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
