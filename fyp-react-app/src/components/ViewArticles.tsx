import { useEffect, useState } from "react";
import useAxiosPrivate from "../lib/useAxiosPrivate";
import { Link } from "react-router-dom";

export default function Articles() {
  const axiosPrivate = useAxiosPrivate();
  const [articles, setArticles] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get(
          `/article/getAllEduMaterial?page=${page}`
        );

        const { content, current_page, total_pages } = response.data;

        isMounted && setArticles(content);
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
      <div className="d-flex flex-wrap justify-content-center gap-2">
        {articles.length < 1 && <p>No articles to be displayed...</p>}
        {articles.map((article: any, idx: number) => (
          <div key={idx} className="card" style={{ width: "18rem" }}>
            <div className="card-body">
              <h5 className="card-title">{article.title}</h5>
              <h6 className="card-subtitle mb-2 text-muted">
                {article.clinicName}
              </h6>
              <p className="card-text">{article.content}</p>
              <Link to="view" state={article}>
                <button className="btn btn-primary">View</button>
              </Link>
            </div>
          </div>
        ))}
      </div>
      {articles.length > 1 && (
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
    </>
  );
}
