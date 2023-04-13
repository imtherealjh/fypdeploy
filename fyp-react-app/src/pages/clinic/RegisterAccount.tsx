import { useState } from "react";

export default function RegisterAccount() {
  const [doctorInput, setDoctorInput] = useState([]);
  const [nurseInput, setNurseInput] = useState([]);
  const [clerkInput, setClerkInput] = useState([]);

  return (
    <>
      <div className="d-flex flex-row">
        <select
          className="form-select form-select-sm"
          aria-label=".form-select-sm example"
        >
          <option selected>Open this select menu</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
        <button>Add</button>
      </div>
    </>
  );
}
