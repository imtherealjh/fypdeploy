import { useState, ChangeEvent, FormEvent } from "react";
import { IObjectKeys } from "../../hooks/types";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useNavigate } from "react-router-dom";

interface NurseInputs extends IObjectKeys {
  username: string;
  password: string;
  name: string;
  email: string;
}

const defaultVal: NurseInputs = {
  username: "",
  password: "",
  name: "",
  email: "",
};

export default function NurseAccount() {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [nurseInput, setNurseInput] = useState([defaultVal]);

  const handleNurseChange = (
    event: ChangeEvent<HTMLInputElement>,
    idx: number
  ) => {
    setNurseInput((prev) =>
      prev.map((el, index) =>
        index === idx
          ? {
              ...el,
              [event.target.name]: event.target.value,
            }
          : el
      )
    );
  };

  const handleFormSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      await axiosPrivate.post("/clinicOwner/registerNurse", nurseInput);
      alert("Nurses has been successfully registered");
      navigate(0);
    } catch (err: any) {
      if (!err?.response) {
        alert("No Server Response");
      } else if (err.response?.status === 400) {
        alert(err.response?.data.errors);
      } else {
        alert("Unknown error occured...");
      }
      console.log(err);
    }
  };

  return (
    <>
      <form method="POST" onSubmit={handleFormSubmit}>
        {nurseInput.map((customInput, idx) => (
          <div className="mt-2" key={idx}>
            <h4>Nurse {idx + 1}</h4>
            <div className="row g-2 align-content-center justify-content-center">
              <div className="col-6">
                <input
                  type="text"
                  className="form-control"
                  name="username"
                  placeholder="Username"
                  aria-label="Username"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleNurseChange(event, idx)
                  }
                  value={customInput.username || ""}
                />
              </div>
              <div className="col-6">
                <input
                  type="password"
                  className="form-control"
                  name="password"
                  placeholder="Password"
                  aria-label="Password"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleNurseChange(event, idx)
                  }
                  value={customInput.password || ""}
                />
              </div>
              <div className="col-6">
                <input
                  type="text"
                  className="form-control"
                  name="name"
                  placeholder="Name"
                  aria-label="Name"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleNurseChange(event, idx)
                  }
                  value={customInput.name || ""}
                />
              </div>
              <div className="col-6">
                <input
                  type="text"
                  className="form-control"
                  name="email"
                  placeholder="Email"
                  aria-label="Email"
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    handleNurseChange(event, idx)
                  }
                  value={customInput.email || ""}
                />
              </div>
            </div>
          </div>
        ))}
        <button
          type="button"
          className="w-100 mt-3 btn btn-danger btn-lg"
          onClick={() => {
            setNurseInput([...nurseInput, defaultVal]);
          }}
        >
          Add additional nurse
        </button>
        <button type="submit" className="w-100 mt-2 btn btn-success btn-lg">
          Submit
        </button>
      </form>
    </>
  );
}
