import { axiosPrivate } from "../api/axios";
import { useEffect, useState } from "react";
import useRefreshToken from "./useRefreshToken";
import useAuth from "./useAuth";

const useAxiosPrivate = () => {
  const [counter, setCounter] = useState(0);
  const refresh = useRefreshToken();
  const { auth } = useAuth();

  useEffect(() => {
    const inc = (mod: any) => setCounter((c) => c + mod);

    const requestInterceptor = axiosPrivate.interceptors.request.use(
      (config) => {
        inc(1);
        if (!config.headers["Authorization"]) {
          config.headers["Authorization"] = `Bearer ${auth?.accessToken}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    const responseInterceptor = axiosPrivate.interceptors.response.use(
      (response) => (inc(-1), response),
      async (error) => {
        const prevReq = error?.config;
        if (error?.response?.status === 403 && !prevReq?.sent) {
          //ensures that the request is sent once
          prevReq.sent = true;
          //call the refresh token endpoint
          const newAccessToken = await refresh();
          prevReq.headers["Authorization"] = `Bearer ${newAccessToken}`;
          return axiosPrivate(prevReq);
        }
        inc(-1);
        return Promise.reject(error);
      }
    );

    return () => {
      axiosPrivate.interceptors.request.eject(requestInterceptor);
      axiosPrivate.interceptors.response.eject(responseInterceptor);
    };
  }, [auth, refresh]);

  const loader = window.document.getElementById("loader-container")!;
  useEffect(() => {
    if (counter > 0) {
      loader.style.display = "flex";
    } else {
      loader.style.display = "none";
    }
  }, [counter]);

  return axiosPrivate;
};

export default useAxiosPrivate;
