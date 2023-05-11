import { axiosPrivate } from "../api/axios";
import { useEffect, useState } from "react";
import useRefreshToken from "./useRefreshToken";
import useAuth from "./useAuth";

const useAxiosPrivate = () => {
  const [counter, setCounter] = useState(0);
  const refresh = useRefreshToken();
  const { auth } = useAuth();

  let firstReq = true;
  useEffect(() => {
    const inc = (mod: any) => setCounter((c) => c + mod);
    let origConfig: any = {};

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
        if (firstReq) {
          firstReq = false;
          origConfig = error?.config;

          if (!origConfig?.sent) {
            origConfig.sent = true;
            try {
              const accessToken = await refresh();
              origConfig.headers["Authorization"] = `Bearer ${accessToken}`;
              inc(-1);
              return axiosPrivate(origConfig);
            } catch (err) {
              inc(-1);
              return Promise.reject(err);
            }
          }
        }

        inc(-1);
        return Promise.reject(error);
      }
    );

    return () => {
      axiosPrivate.interceptors.request.eject(requestInterceptor);
      axiosPrivate.interceptors.response.eject(responseInterceptor);
    };
  }, [auth]);

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
