import { axiosPrivate } from "../api/axios";
import useAuth from "./useAuth";

const useRefreshToken = () => {
  const { setAuth } = useAuth();

  const refresh = async () => {
    const response = await axiosPrivate.get("/auth/refresh");

    setAuth((prev) => {
      return {
        ...prev,
        name: response.data.name,
        role: response.data.role,
        accessToken: response.data.accessToken,
      };
    });

    console.log(response.data.accessToken);
    return response.data.accessToken;
  };

  return refresh;
};

export default useRefreshToken;
