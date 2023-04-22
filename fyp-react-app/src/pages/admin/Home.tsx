import { ChangeEvent, useEffect, useState } from "react";
import { CgSearch } from "react-icons/cg";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { Link } from "react-router-dom";

interface ClinicData {
  clinicId: number;
  clinicName: string;
  location: string;
  contactName: string;
  email: string;
}

class CustomObject {
  clinicId: number;
  clinicName: string;
  location: string;
  contactName: string;
  email: string;

  constructor(data: any) {
    this.clinicId = data.clinicId;
    this.clinicName = data.clinicName;
    this.location = data.location;
    this.contactName = data.contactName;
    this.email = data.email;
  }
}

export default function Dashboard() {
  const [searchInput, setSearchInput] = useState("");
  const [clinics, setClinics] = useState([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();
    const fetchData = async () => {
      let response = await axiosPrivate.get("/sysAdmin/getAllClinics", {
        signal: controller.signal,
      });

      isMounted && setClinics(response.data);
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  return (
    <>
      <div>
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder={`Search Clinic`}
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
              <th scope="col">Clinic</th>
              <th scope="col">Location</th>
              <th scope="col">Contact Person</th>
              <th scope="col">Email</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {clinics.length < 1 ? (
              <tr>
                <td colSpan={6}>No data available....</td>
              </tr>
            ) : null}
            {clinics
              .filter((obj: any) => {
                return (
                  obj.clinicName.includes(searchInput) ||
                  obj.location.includes(searchInput) ||
                  obj.contactName.includes(searchInput) ||
                  obj.email.includes(searchInput)
                );
              })
              .map((data: any, idx: number) => (
                <tr key={idx}>
                  <th className="align-middle" scope="row">
                    {idx + 1}
                  </th>
                  <td>
                    <Link to={`view/${data.clinicId}`} state={data}>
                      {data.clinicName}
                    </Link>
                  </td>
                  <td>{data.location}</td>
                  <td>{data.contactName}</td>
                  <td>{data.email}</td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </>
  );
}
