import { useEffect } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

export default function dashboard_home() {
  const axiosPrivate = useAxiosPrivate();
  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();
    const fetchData = async () => {
      const response = await axiosPrivate.get("/doctor/secure", {
        signal: controller.signal,
      });
      console.log(response);
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  return (
    <>
      <div className="content d-flex flex-column">
        <div>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat.
        </div>
      </div>
    </>
  );
}
