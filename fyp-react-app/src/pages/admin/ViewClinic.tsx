import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useEffect, useState } from "react";
import "../../css/viewclinic.css";

export default function ViewClinic() {
  const { id } = useParams();
  const [data, setData] = useState<any>({
    clinic: {},
    totalAcc: 0,
  });
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      let response = await axiosPrivate.get(
        `/sysAdmin/getClinicById?clinicId=${Number(id)}`,
        {
          signal: controller.signal,
        }
      );

      isMounted &&
        setData({
          clinic: response.data.clinic,
          totalAcc: response.data.totalAcc,
        });
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  let isMounted = false;
  return (
    <>
      <h1>Clinic - Overview</h1>
      <div className="clinic-overview">
        <div className="d-flex flex-row">
          <div className="d-flex flex-column align-items-center">
            <img
              className="rounded-circle"
              src="https://via.placeholder.com/100"
              alt="Profile Image"
            />
            <span>{data.clinic.clinicName}</span>
          </div>
          <div className="clinic-details row">
            <div className="col d-flex flex-column">
              <span>Email:</span>
              <span>{data.clinic.email}</span>
            </div>
            <div className="col d-flex flex-column">
              <span>Total Account:</span>
              <span>{data.totalAcc}</span>
            </div>
            <div className="col d-flex flex-column">
              <span>Contact Person:</span>
              <span>{data.clinic.contactName}</span>
            </div>
          </div>
        </div>

        {data.clinic.status === "PENDING" && (
          <>
            <div className="d-grid gap-2 p-4">
              <button
                type="button"
                className="btn btn-success"
                onClick={async () => {
                  if (!isMounted) {
                    isMounted = true;
                    try {
                      await axiosPrivate.put(
                        `/sysAdmin/approveClinic?clinicId=${data.clinic.clinicId}`
                      );
                      alert("Successfully approved clinic...");
                    } catch (err) {
                      alert("There is an error approving the clinic...");
                    }
                    navigate(0);
                    isMounted = false;
                  }
                }}
              >
                Accept
              </button>
              <button
                type="button"
                className="btn btn-danger"
                onClick={async () => {
                  if (!isMounted) {
                    isMounted = true;
                    try {
                      await axiosPrivate.put(
                        `/sysAdmin/rejectClinic?clinicId=${data.clinic.clinicId}`
                      );
                      alert("Successfully rejected clinic...");
                    } catch (err) {
                      alert("There is an error rejecting the clinic...");
                    }
                    navigate(0);
                    isMounted = false;
                  }
                }}
              >
                Reject
              </button>
            </div>
          </>
        )}

        {data.clinic.status === "APPROVED" && (
          <>
            <div className="d-grid gap-2 p-4">
              <button
                type="button"
                className="btn btn-warning"
                onClick={async () => {
                  if (!isMounted) {
                    isMounted = true;
                    try {
                      await axiosPrivate.put(
                        `/sysAdmin/suspendClinic?clinicId=${data.clinic.clinicId}`
                      );
                      alert("Successfully suspend clinic...");
                    } catch (err) {
                      alert("There is an error suspend the clinic...");
                    }
                    navigate(0);
                    isMounted = false;
                  }
                }}
              >
                Suspend clinic
              </button>
            </div>
          </>
        )}

        {data.clinic.status === "SUSPENDED" && (
          <>
            <div className="d-grid gap-2 p-4">
              <button
                type="button"
                className="btn btn-success"
                onClick={async () => {
                  if (!isMounted) {
                    isMounted = true;
                    try {
                      await axiosPrivate.put(
                        `/sysAdmin/enableClinic?clinicId=${data.clinic.clinicId}`
                      );
                      alert("Successfully enabling clinic...");
                    } catch (err) {
                      alert("There is an error enabling the clinic...");
                    }
                    navigate(0);
                    isMounted = false;
                  }
                }}
              >
                Enable clinic
              </button>
            </div>
          </>
        )}
      </div>
    </>
  );
}
