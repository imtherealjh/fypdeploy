import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import useAxiosPrivate from "../lib/useAxiosPrivate";

export default function Articles() {
  const { materialId } = useLocation()?.state;
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [article, setArticle] = useState<any>({});
  const [isEditable, setIsEditable] = useState(false);

  const handleSubmit = async () => {
    try {
      await axiosPrivate.put(
        `/clerk/updateEduMaterial?id=${materialId}`,
        article
      );
      alert("Article saved successfully");
      navigate("/clerk/articles");
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
  };

  const handleDelete = async () => {
    try {
      await axiosPrivate.delete(`/clerk/deleteEduMaterial?id=${materialId}`);
      alert("Article deleted successfully");
      navigate("/clerk/articles");
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
  };

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get(
          `/article/getEduMaterialById?id=${materialId}`
        );

        const { content, editable } = response.data;

        isMounted && setArticle(content);
        isMounted && setIsEditable(editable);
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
      <h1>View Article</h1>
      <div className="d-flex flex-column">
        <div className="mb-3">
          <label className="form-label fw-bold" htmlFor="title">
            Title:
          </label>
          <input
            name="title"
            type="text"
            readOnly={!isEditable}
            className={`form-control${!isEditable ? "-plaintext" : ""}`}
            value={article.title || ""}
            onChange={(event) =>
              setArticle((prev: any) => ({
                ...prev,
                [event.target.name]: event.target.value,
              }))
            }
          />
        </div>
        <div className="mb-3">
          <label className="form-label fw-bold" htmlFor="content">
            Content:
          </label>
          <textarea
            name="content"
            rows={15}
            readOnly={!isEditable}
            className={`form-control${!isEditable ? "-plaintext" : ""}`}
            value={article.content || ""}
            onChange={(event) =>
              setArticle((prev: any) => ({
                ...prev,
                [event.target.name]: event.target.value,
              }))
            }
          />
        </div>
        {isEditable && (
          <>
            <div className="d-grid gap-2">
              <button
                className="btn btn-warning"
                type="button"
                onClick={handleSubmit}
              >
                Update
              </button>
              <button
                className="btn btn-danger"
                type="button"
                onClick={handleDelete}
              >
                Delete
              </button>
            </div>
          </>
        )}
      </div>
    </>
  );
}
