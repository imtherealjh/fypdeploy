import { useEffect, useState } from "react";
import useAxiosPrivate from "../../lib/useAxiosPrivate";
import useAuth from "../../lib/useAuth";
import { Link } from "react-router-dom";
import UpdateFeedBackComponent from "./UpdateFeedbackComponent";
import DeleteFeedBackComponent from "./DeleteFeedbackComponent";
import ResolvedFeedbackComponent from "./ResolvedFeedbackComponent";

export default function SystemFeedback() {
  const [page, setPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);
  const [feedback, setFeedbacks] = useState([]);

  const [data, setData] = useState<any>();
  const [btnClicked, setBtnClicked] = useState("");

  const { auth } = useAuth();
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get("/all/getSystemFeedback", {
          signal: controller.signal,
        });

        const { content, current_page, total_pages } = response.data;

        isMounted && setFeedbacks(content);
        isMounted && setPage(current_page);
        isMounted && setTotalPage(total_pages);
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, [page]);

  const pagination: any = [];
  if (totalPage > 1) {
    let startPage, endPage;
    startPage = endPage = page;

    if (page == 0) {
      endPage = startPage + 2;
    } else {
      endPage = page + 1;
      startPage = page - 1;
    }

    if (page + 1 == totalPage) {
      startPage = page - 2;
      endPage = totalPage - 1;
    }

    while (startPage <= endPage) {
      pagination.push(startPage++);
    }
  }

  return (
    <>
      <h1>System Feedback</h1>
      <div className="d-flex flex-column">
        <table className="mt-2 table table-striped">
          <thead>
            <tr>
              <th scope="col">#</th>
              {auth.role.toLowerCase() === "system_admin" && (
                <th scope="col">Name</th>
              )}
              {auth.role.toLowerCase() === "system_admin" && (
                <th scope="col">Clinic</th>
              )}
              <th scope="col">Date/Time</th>
              <th scope="col">Feedback</th>
              <th scope="col">Status</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {feedback.length < 1 ? (
              <tr>
                <td
                  colSpan={auth.role.toLowerCase() === "system_admin" ? 7 : 5}
                >
                  No data available....
                </td>
              </tr>
            ) : null}
            {feedback.map((data: any, idx: number) => (
              <tr key={idx}>
                <th className="align-middle" scope="row">
                  {idx + 1}
                </th>
                {auth.role.toLowerCase() === "system_admin" && (
                  <>
                    <td className="align-middle">{data.name}</td>
                    <td className="align-middle">{data.clinic}</td>
                  </>
                )}
                <td className="align-middle">{data.datetime}</td>
                <td className="align-middle">{data.feedback}</td>
                <td className="align-middle">{data.status}</td>
                <td>
                  {data.status !== "COMPLETED" && (
                    <>
                      {auth.role.toLowerCase() !== "system_admin" &&
                        (new Date().getTime() -
                          new Date(data.datetime).getTime()) /
                          1000 /
                          60 /
                          60 <
                          24 && (
                          <div className="d-flex gap-2">
                            <button
                              type="button"
                              className="btn btn-success"
                              onClick={() => {
                                setBtnClicked("updateFeedbackBtn");
                                setData(data);
                              }}
                              data-bs-toggle="modal"
                              data-bs-target="#modal"
                            >
                              Update
                            </button>
                            <button
                              type="button"
                              className="btn btn-danger"
                              onClick={() => {
                                setBtnClicked("deleteFeedbackBtn");
                                setData(data);
                              }}
                              data-bs-toggle="modal"
                              data-bs-target="#modal"
                            >
                              Delete
                            </button>
                          </div>
                        )}

                      {auth.role.toLowerCase() === "system_admin" && (
                        <button
                          type="button"
                          className="btn btn-primary"
                          onClick={() => {
                            setBtnClicked("resolveFeedbackBtn");
                            setData(data);
                          }}
                          data-bs-toggle="modal"
                          data-bs-target="#modal"
                        >
                          Resolve
                        </button>
                      )}
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {feedback.length < 1 && (
          <nav>
            <ul style={{ background: "transparent" }} className="pagination">
              {pagination.map((el: any) => (
                <li className="page-item">
                  <a
                    className={el === page ? `page-link active` : "page-link"}
                    href="#"
                    onClick={() => setPage(el)}
                  >
                    {el + 1}
                  </a>
                </li>
              ))}
            </ul>
          </nav>
        )}
        {auth.role.toLowerCase() !== "system_admin" && (
          <Link to="submit">
            <button type="button" className="btn btn-primary w-100">
              Submit Feedback
            </button>
          </Link>
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
              <h5 className="modal-title">
                {btnClicked === "updateFeedbackBtn" && "Update Feedback"}
                {btnClicked === "deleteFeedbackBtn" && "Delete Feedback"}
                {btnClicked === "resolveFeedbackBtn" && "Resolve Feedback"}
              </h5>
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
              {btnClicked === "updateFeedbackBtn" && (
                <UpdateFeedBackComponent
                  data={data}
                  axiosPrivate={axiosPrivate}
                />
              )}
              {btnClicked === "deleteFeedbackBtn" && (
                <DeleteFeedBackComponent
                  data={data}
                  axiosPrivate={axiosPrivate}
                />
              )}
              {btnClicked === "resolveFeedbackBtn" && (
                <ResolvedFeedbackComponent
                  data={data}
                  axiosPrivate={axiosPrivate}
                />
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
