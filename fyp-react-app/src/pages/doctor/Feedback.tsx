import { useEffect, useState } from "react";
import useAxiosPrivate from "../../lib/useAxiosPrivate";

function Feedback() {
  const axiosPrivate = useAxiosPrivate();
  const [feedback, setFeedback] = useState({});

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        const response = await axiosPrivate.get("/doctor/getFeedback");
        isMounted && setFeedback(response.data);
      } catch (err) {
        console.log(err);
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
      <h1>Feedback</h1>
      <div className="feedback-container"></div>
    </>
  );
}

export default Feedback;
