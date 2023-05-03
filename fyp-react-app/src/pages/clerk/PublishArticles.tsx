import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useAxiosPrivate from "../../lib/useAxiosPrivate";

export default function Articles() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [formData, setFormData] = useState<any>({});

  const handleSubmit = async () => {
    try {
      await axiosPrivate.post("/clerk/createEduMaterial", formData);
      alert("Article have been successfully submitted");
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

  return (
    <>
      <h1>Publish Article</h1>
      <div className="d-flex flex-column">
        <div className="mb-3">
          <label className="form-label" htmlFor="title">
            Title:
          </label>
          <input
            className="form-control"
            type="text"
            id="title"
            name="title"
            value={formData.title || ""}
            onChange={(event) =>
              setFormData((prev: any) => ({
                ...prev,
                [event.target.name]: event.target.value,
              }))
            }
          />
        </div>
        <div className="mb-3">
          <label className="form-label" htmlFor="content">
            Content:
          </label>
          <textarea
            className="form-control"
            id="content"
            name="content"
            rows={10}
            value={formData.content || ""}
            onChange={(event) =>
              setFormData((prev: any) => ({
                ...prev,
                [event.target.name]: event.target.value,
              }))
            }
          />
        </div>
        <div className="d-grid gap-2">
          <button
            style={{ background: "white" }}
            className="btn"
            type="button"
            onClick={() => navigate("/clerk/articles")}
          >
            Cancel
          </button>
          <button
            className="btn btn-primary"
            type="button"
            onClick={handleSubmit}
          >
            Publish Article
          </button>
        </div>
      </div>
    </>
  );
}
