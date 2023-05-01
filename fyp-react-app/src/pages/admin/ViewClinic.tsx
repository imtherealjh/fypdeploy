import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Buffer } from "buffer";

import useAxiosPrivate from "../../lib/useAxiosPrivate";
import axios from "axios";

import "../../css/viewclinic.css";

export default function ViewClinic() {
  const state = useLocation()?.state;

  const [imageData, setImageData] = useState<any>();
  const [data, setData] = useState<any>({
    clinic: {},
    totalAcc: 0,
  });
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    let endpoints = [
      axiosPrivate.get(
        `/sysAdmin/getClinicById?clinicId=${Number(state.clinicId)}`,
        {
          signal: controller.signal,
        }
      ),
      axiosPrivate.get(
        `/sysAdmin/getClinicLicense?clinicId=${state.clinicId}`,
        {
          responseType: "arraybuffer",
          signal: controller.signal,
        }
      ),
    ];

    const fetchData = () => {
      axios.all(endpoints.map((endpoint) => endpoint)).then(
        axios.spread((clinicData, imageData) => {
          const contentType = imageData.headers["content-type"];
          const imageBase64 = Buffer.from(imageData.data, "binary").toString(
            "base64"
          );
          const imageSrc = `data:${contentType};base64,${imageBase64}`;
          isMounted && setData(clinicData.data);
          isMounted && setImageData(imageSrc);
        })
      );
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
      <div style={{ maxWidth: "85%" }} className="clinic-overview">
        <div className="d-flex flex-row flex-wrap">
          <div
            style={{ flex: "1 1 30%" }}
            className="d-flex flex-column align-items-center"
          >
            <img
              className="rounded-circle"
              src="https://via.placeholder.com/100"
              alt="Profile Image"
            />
            <span>{data.clinic.clinicName}</span>
          </div>
          <div style={{ flex: "1 1 70%" }} className="clinic-details row">
            <div className="col">
              <span>Email:</span>
              <span>{data.clinic.email}</span>
            </div>
            <div className="col">
              <span>Total Account:</span>
              <span>{data.totalAcc}</span>
            </div>
            <div className="col">
              <span>Contact Person:</span>
              <span>{data.clinic.contactName}</span>
            </div>

            <div className="col">
              <span>Proof of License:</span>
              <span>
                <button
                  id="updateBtn"
                  type="button"
                  className="btn btn-dark"
                  data-bs-toggle="modal"
                  data-bs-target="#modal"
                >
                  View License
                </button>
              </span>
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
              <button
                id="closeModalBtn"
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body d-flex justify-content-center">
              <img
                style={{ width: "20rem" }}
                src={imageData}
                alt={`Clinic License Of Proof`}
              />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
