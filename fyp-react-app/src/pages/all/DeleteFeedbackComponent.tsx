import { Axios } from "axios";
import { useNavigate } from "react-router-dom";

interface Props {
  data: any;
  axiosPrivate: Axios;
}

export default function DeleteFeedBackComponent({ data, axiosPrivate }: Props) {
  const navigate = useNavigate();

  const handleClick = async () => {
    try {
      await axiosPrivate.delete(`/all/deleteSystemFeedback?id=${data.id}`);
      alert("Feedback removed successfully");
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
      <div className="d-flex flex-column gap-2">
        <h6>Are you sure that you want to delete this feedback?</h6>
        <button type="button" className="btn btn-primary" onClick={handleClick}>
          Confirm
        </button>
      </div>
    </>
  );
}
