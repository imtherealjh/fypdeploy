import { useLocation } from "react-router-dom";

export default function EditAccount() {
  const obj = useLocation()?.state;

  console.log(obj);

  return (
    <>
      <h1>Edit account</h1>
      <div></div>
    </>
  );
}
