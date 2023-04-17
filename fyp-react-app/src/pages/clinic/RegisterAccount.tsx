import { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

interface DoctorInputs {
  username: string;
  password: string; 
  name: string; 
  profile: string;
  speciality: Array<any>; 
}

export default function RegisterAccount() {
  const [doctorInput, setDoctorInput] = useState([{} as DoctorInputs]);
  const [nurseInput, setNurseInput] = useState([]);
  const [clerkInput, setClerkInput] = useState([]);
  const [speciality, setSpeciality] = useState([]);

  const axiosPrivate = useAxiosPrivate();
  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();
    const fetchData = async () => {
      try {
        const response = await axiosPrivate.get("/doctor/secure", {
          signal: controller.signal,
        });
        console.log(response);
        isMounted && setSpeciality(response.data);
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();
    
    return () => {
      isMounted = false;
      controller.abort();
    }
  }, []);

  return (
    <>
      <div className="d-flex flex-row gap-2 w-100">
        <select
          className="form-select form-select-sm"
          aria-label=".form-select-sm example"
        >
          <option selected disabled>Open this to select roles...</option>
          <option value="doctor">Doctor</option>
          <option value="nurse">Nurse</option>
          <option value="clerk">Front Desk</option>
        </select>
        <button type="button" className="btn btn-success">Add</button>
      </div>
      <div>
        <div>
          <h2>Doctors</h2>
          <div>
            {doctorInput.map((customInput, idx) => (
              <>
                <div key={idx}>
                  <h4>Doctor {idx+1}</h4>
                  <div className="row">
                    <div className="col">
                      <input type="text" className="form-control" name="username" value={customInput.username} />
                      <label className="form-label" htmlFor="floatingInput">Username</label>
                    </div>
                    <div className="col">
                      <input type="password" className="form-control" name="password" value={customInput.password} />
                      <label className="form-label" htmlFor="floatingInput">Password</label>
                    </div>
                    <div className="col">
                      <input type="text" className="form-control" name="name" value={customInput.name} />
                      <label className="form-label" htmlFor="floatingInput">Name</label>
                    </div>
                  </div>
                  <div className="form-floating mb-3">
                  <input type="text" className="form-control" name="profile" value={customInput.profile} />
                    <label htmlFor="floatingInput">Profile</label>
                  </div>
                </div>
              </>
            ))}
          </div>
        </div>
        <div>
          <h2>Nurses</h2>

        </div>
        <div>
          <h2>Clerks</h2>

        </div>
        
        
      </div>
    </>
  );
}
