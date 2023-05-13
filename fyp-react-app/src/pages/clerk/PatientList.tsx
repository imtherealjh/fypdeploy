import { useState, useEffect, ChangeEvent } from "react";
import { Link } from "react-router-dom";
import { CgSearch } from "react-icons/cg";

import useAxiosPrivate from "../../lib/useAxiosPrivate";

function PatientListPage() {
  const axiosPrivate = useAxiosPrivate();
  const [searchInput, setSearchInput] = useState("");
  const [patientList, setPatientList] = useState([]);

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchPatients = async () => {
      try {
        const response = await axiosPrivate.get("/staff/getAllPatients", {
          signal: controller.signal,
        });

        isMounted && setPatientList(response.data);
      } catch (error) {
        console.log(error);
        console.error("Error fetching patient list:", error);
      }
    };

    fetchPatients();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  return (
    <>
      <h1>Patient - Profile Overview</h1>
      <div>
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder={`Search Patient`}
            onChange={(e: ChangeEvent<HTMLInputElement>) =>
              setSearchInput(e.target.value)
            }
            aria-label="Search"
            aria-describedby="Search"
          />
          <button
            className="btn btn-outline-secondary"
            type="button"
            id="button-addon2"
          >
            <CgSearch />
            <span className="ms-2">Search</span>
          </button>
        </div>
        <table className="mt-2 table table-striped">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Patient Name</th>
              <th scope="col">Email</th>
              <th scope="col">Contact No</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            {patientList.length < 1 ? (
              <tr>
                <td colSpan={6}>No data available....</td>
              </tr>
            ) : null}
            {patientList
              .filter((obj: any) => {
                return (
                  obj.name.includes(searchInput) ||
                  obj.email.includes(searchInput) ||
                  JSON.stringify(obj.contactNo).includes(searchInput)
                );
              })
              .map((data: any, idx: number) => (
                <tr key={idx}>
                  <th className="align-middle" scope="row">
                    {idx + 1}
                  </th>
                  <td className="align-middle">{data.name}</td>
                  <td className="align-middle">{data.email}</td>
                  <td className="align-middle">{data.contactNo}</td>
                  <td>
                    <Link to="details" state={data}>
                      <button className="btn btn-primary">View</button>
                    </Link>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default PatientListPage;
