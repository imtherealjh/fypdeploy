import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuth from "../hooks/useAuth";

interface Props {
  role: string;
}

const RequireAuth = ({ role }: Props) => {
  const { auth } = useAuth();
  const location = useLocation();

  return (
    <>
      {auth?.role.toLowerCase() === role ? (
        <Outlet />
      ) : auth?.accessToken ? (
        <Navigate to="/unauthorized" state={{ from: location }} replace />
      ) : (
        <Navigate to="/" state={{ from: location }} replace />
      )}
    </>
  );
};

export default RequireAuth;
