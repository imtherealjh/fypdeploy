import { useEffect, useState } from "react";
import useAxiosPrivate from "../../lib/useAxiosPrivate";

function Feedback() {
  const axiosPrivate = useAxiosPrivate();
  const [page, setPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);
  const [feedback, setFeedback] = useState<any>([]);

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        const response = await axiosPrivate.get(
          `/doctor/getFeedback?page=${page}`
        );

        const { content, current_page, total_pages } = response.data;

        isMounted && setFeedback(content);
        isMounted && setPage(current_page);
        isMounted && setTotalPage(total_pages);
      } catch (err) {
        console.log(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, [page]);

  const pagination: any = [];

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
    startPage = startPage > 0 ? startPage : 0;
    endPage = totalPage - 1;
  }

  while (startPage <= endPage) {
    pagination.push(startPage++);
  }

  return (
    <>
      <h1>Feedback</h1>
      <div>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Patient</th>
              <th>Date</th>
              <th>Feedback</th>
              <th>Ratings</th>
            </tr>
          </thead>
          <tbody>
            {feedback.length < 1 && (
              <tr>
                <td colSpan={4}>No data available to read...</td>
              </tr>
            )}
            {feedback.map((feedback: any, idx: number) => (
              <tr key={idx}>
                <td>{feedback.patient}</td>
                <td>{feedback.date}</td>
                <td>{feedback.feedback}</td>
                <td>{feedback.rating}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {feedback.length > 1 && (
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
      </div>
    </>
  );
}

export default Feedback;
