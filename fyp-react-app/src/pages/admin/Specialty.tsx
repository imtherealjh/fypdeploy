import { useState, useEffect } from "react";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import { Link, useNavigate } from "react-router-dom";

export default function SpecialtyPage() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [speciality, setSpeciality] = useState<any>([]);

  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();
    const fetchData = async () => {
      try {
        const response = await axiosPrivate.get("/public/getAllSpecialty", {
          signal: controller.signal,
        });

        isMounted && setSpeciality(response.data);
      } catch (err) {
        console.error(err);
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
      <h1>Specialty</h1>
      <div>
        <div className="d-flex justify-content-end">
          <Link to="create">
            <button className="btn btn-dark btn-lg" type="button">
              Create
            </button>
          </Link>
        </div>
        <table className="table table-striped">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Type</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {speciality.length < 1 && (
              <tr>
                <td colSpan={2}></td>
              </tr>
            )}
            {speciality.map((speciality: any, idx: number) => (
              <tr key={idx}>
                <td scope="row">{idx + 1}</td>
                <td scope="col">{speciality.type}</td>
                <td scope="col">
                  <button
                    type="button"
                    className="btn btn-danger"
                    onClick={async () => {
                      try {
                        await axiosPrivate.delete(
                          `/sysAdmin/deleteSpecialty?id=${speciality.specialtyId}`
                        );
                        alert("Specialty removed successfully");
                        navigate(0);
                      } catch (err: any) {
                        if (!err?.response) {
                          alert("No Server Response");
                        } else if (err.response?.status === 400) {
                          alert(err.response?.data.errors);
                        } else {
                          alert("Unknown error occured...");
                        }
                        console.error(err);
                      }
                    }}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}
