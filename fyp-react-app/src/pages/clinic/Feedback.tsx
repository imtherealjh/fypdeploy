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
          `/clinicOwner/getFeedback?page=${page}`
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
        {totalPage > 1 && (
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
