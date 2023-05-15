import { ChangeEvent, useState } from "react";
import RegisterDocAccForm from "./AccountForms/RegisterDocAccForm";
import RegisterNurAccForm from "./AccountForms/RegisterNurAccForm";
import RegisterClerkAccForm from "./AccountForms/RegisterClerkAccForm";

export default function RegisterAccount() {
  const [formState, setFormState] = useState("");

  return (
    <>
      <h1>Register Account</h1>
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
        {formState === "doctor" && (
          <div>
            <RegisterDocAccForm />
          </div>
        )}
        {formState === "nurse" && (
          <div>
            <RegisterNurAccForm />
          </div>
        )}
        {formState === "clerk" && (
          <div>
            <RegisterClerkAccForm />
          </div>
        )}
      </div>
    </>
  );
}
