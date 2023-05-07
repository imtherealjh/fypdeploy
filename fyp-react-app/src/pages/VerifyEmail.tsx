import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../lib/useAxiosPrivate";
import { useEffect } from "react";

export default function VerifyEmail() {
  const { code } = useParams();
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const verifyCode = async () => {
      try {
        await axiosPrivate("/auth/verify?code=" + code);
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
