import { ChangeEvent, useState } from "react";
import RegisterDocAccForm from "./RegisterDocAccForm";
import RegisterNurAccForm from "./RegisterNurAccForm";
import RegisterClerkAccForm from "./RegisterClerkAccForm";

export default function RegisterAccount() {
  const [formState, setFormState] = useState("");

  return (
    <>
      <div className="d-flex flex-column gap-2 w-100">
        <h5>Please create account by one role at a time...</h5>
        <select
          defaultValue="DEFAULT"
          className="form-select form-select-sm"
          aria-label=".form-select-sm example"
          onChange={(event: ChangeEvent<HTMLSelectElement>) =>
            setFormState(event.target.value)
          }
        >
          <option value="DEFAULT" disabled>
            Open this to select roles...
          </option>
          <option value="doctor">Doctor</option>
          <option value="nurse">Nurse</option>
          <option value="clerk">Front Desk</option>
        </select>
      </div>
      <div>
        <div>{formState === "doctor" && <RegisterDocAccForm />}</div>
        <div>{formState === "nurse" && <RegisterNurAccForm />}</div>
        <div>{formState === "clerk" && <RegisterClerkAccForm />}</div>
      </div>
    </>
  );
}
