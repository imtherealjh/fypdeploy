import { ChangeEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useAxiosPrivate from "../../lib/useAxiosPrivate";

export default function Profile() {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const [clerkProfile, setClerkProfile] = useState<any>({});

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axiosPrivate.get("/clerk/getProfile", {
          signal: controller.signal,
        });

        isMounted && setClerkProfile(response.data);
      } catch (err: any) {
        if (!err?.response) {
          alert("No Server Response");
        } else if (err.response?.status === 400) {
          alert(err.response?.data.errors);
        } else {
          alert("Unknown error");
        }
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setClerkProfile((prev: any) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async () => {
    console.log(clerkProfile);
    try {
      await axiosPrivate.put("/clerk/updateProfile", clerkProfile);
      alert("Clerk profile updated!!");
      navigate(0);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      <h1> Profile Page</h1>
      <div className="d-flex flex-row flex-wrap gap-3">
        <div
          style={{ flex: "1 1 auto" }}
          className="d-flex flex-column align-self-center"
        >
          <img
            src="https://via.placeholder.com/200"
            alt="Profile Img"
            className="align-self-center rounded-circle"
          />
          <h5 className="align-self-center mt-2">{clerkProfile.name}</h5>
        </div>

        <div
          className="p-3 w-100"
          style={{ flex: "1 1 65%", backgroundColor: "#DCDCDC" }}
        >
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              name="name"
              placeholder="name..."
              onChange={handleChange}
              value={clerkProfile.name || ""}
            />
            <label htmlFor="name">Name</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              name="email"
              placeholder="email..."
              onChange={handleChange}
              value={clerkProfile.email || ""}
            />
            <label htmlFor="email">Email</label>
          </div>

          <div className="d-grid">
            <button
              type="button"
              className="btn btn-primary"
              onClick={handleSubmit}
            >
              Update
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
