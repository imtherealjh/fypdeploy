import { useNavigate, useParams } from "react-router-dom";
import { useEffect } from "react";
import axios from "../api/axios";

export default function VerifyEmail() {
  const { code } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const verifyCode = async () => {
      try {
        await axios.get("/auth/verify?code=" + code);
        alert("Code has been verified!!");
      } catch (err) {
        console.log(err);
      }
      navigate("/", { replace: true });
    };

    verifyCode();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  return <></>;
}
