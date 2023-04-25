import { Link } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useState, useEffect } from "react";

export default function SearchClinic() {
  const axiosPrivate = useAxiosPrivate();
  const [specialtyType, setSpecialityType] = useState([]);
  const [input, setInputType] = useState<any>({});
  const [data, setDataType] = useState([]);

  const handleChange = (event: any) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputType((values: any) => ({ ...values, [name]: value }));
  };

  const handleSubmit = async (event: any) => {
    try {
      const response = await axiosPrivate.post("", input);
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
        <select
          className="custom-select"
          name="specialty"
          onChange={handleChange}
        >
          <option selected>Choose Specialty</option>
          {specialtyType.map((val, idx) => (
            <option key={idx} value={val}>
              {val}
            </option>
          ))}
        </select>
        <input type="text" name="location" onChange={handleChange} />
        <button onClick={handleSubmit}> Submit </button>
        <table className="mt-2 table table-striped">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Clinic</th>
              <th scope="col">Location</th>
              <th scope="col">Contact Number</th>
            </tr>
          </thead>
          <tbody>
            {data.length < 1 ? (
              <tr>
                <td colSpan={6}>No data available....</td>
              </tr>
            ) : null}
            {data.map((data: any, idx: number) => (
              <tr key={idx}>
                <th className="align-middle" scope="row">
                  {idx + 1}
                </th>
                <td>{data.clinicName}</td>
                <td>{data.location}</td>
                <td>{data.contactName}</td>
                <td>{data.email}</td>
                <td>
                  <Link
                    to={"book"}
                    state={{ type: input.specialty, id: data.clinicID }}
                  >
                    <button>Book</button>
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
