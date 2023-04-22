import { useLocation, useParams } from "react-router-dom";

export default function ViewClinic() {
  const { id } = useParams();

  return (
    <>
      <h1>Hello {id}</h1>
    </>
  );
}
