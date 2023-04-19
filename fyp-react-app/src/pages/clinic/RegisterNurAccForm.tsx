import axios from "axios";
import { useState, ChangeEvent, FormEvent } from "react";
import { IObjectKeys } from "../../utils/types";

interface NurseInputs extends IObjectKeys {
  username: string;
  password: string;
  name: string;
}

const defaultVal: NurseInputs = {
  username: "",
  password: "",
  name: "",
};

export default function NurseAccount() {
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

  const handleFormSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
  };

  return (
    <>
      <form method="POST" onSubmit={handleFormSubmit}>
        {nurseInput.map((customInput, idx) => (
          <div className="mt-2" key={idx}>
            <h4>Nurse {idx + 1}</h4>
            <div className="row g-2 align-content-center justify-content-center">
              <div className="col">
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
              <div className="col">
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
              <div className="col">
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
            </div>
          </div>
        ))}
        <button
          className="w-100 mt-3 btn btn-danger btn-lg"
          onClick={() => {
            setNurseInput([...nurseInput, defaultVal]);
          }}
        >
          Add additional nurse
        </button>
        <button className="w-100 mt-2 btn btn-success btn-lg">Submit</button>
      </form>
    </>
  );
}
