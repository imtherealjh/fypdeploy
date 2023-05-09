import { Axios } from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

interface Props {
  data: any;
  axiosPrivate: Axios;
}

export default function UpdateFeedBackComponent({ data, axiosPrivate }: Props) {
  const navigate = useNavigate();
  const [formData, setFormData] = useState(data);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      await axiosPrivate.put(
        `/all/updateSystemFeedback?id=${data.id}`,
        formData
      );
      alert("Feedback updated successfully");
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
  };

  return (
    <>
      <form className="d-flex flex-column gap-2" onSubmit={handleSubmit}>
        <div className="d-flex flex-column">
          <textarea
            id="feedback"
            name="feedback"
            rows={4}
            value={formData.feedback}
            onChange={(event) =>
              setFormData((prev: any) => ({
                ...prev,
                [event.target.name]: event.target.value,
              }))
            }
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Update Feedback
        </button>
      </form>
    </>
  );
}
