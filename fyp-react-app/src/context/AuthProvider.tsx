import { ReactNode, createContext, useState } from "react";

interface UserContext {
  name: string;
  role: string;
  accessToken: string;
}

interface IStateUserDef {
  auth: UserContext;
  setAuth: React.Dispatch<React.SetStateAction<UserContext>>;
}

interface Props {
  children: ReactNode;
}

const AuthContext = createContext<IStateUserDef>({
  auth: { name: "", role: "", accessToken: "" },
  setAuth: () => {},
});

export const AuthProvider = ({ children }: Props) => {
  const [auth, setAuth] = useState({ name: "", role: "", accessToken: "" });

  return (
    <>
      <AuthContext.Provider value={{ auth: auth, setAuth: setAuth }}>
        {children}
      </AuthContext.Provider>
    </>
  );
};

export default AuthContext;
