import { useState, ChangeEvent } from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";

interface Props {
  formData: any;
  setFormData: React.Dispatch<React.SetStateAction<any>>;
}

export default function Step2({ formData, setFormData }: Props) {
  const [timeslots, setTimeslots] = useState<any>([]);
  const axiosPrivate = useAxiosPrivate();

  let isMounted = false;
  const handleDateChange = async (event: ChangeEvent<HTMLInputElement>) => {
    if (!isMounted) {
      isMounted = true;
      let response = await axiosPrivate.post("/patient/getDoctorAvailability", {
        doctorId: formData.doctorId,
        date: event.target.value,
      });

      isMounted && setTimeslots(response.data);

      setFormData((prev: any) => {
        return { ...prev, [event.target.name]: event.target.value };
      });

      isMounted = false;
    }
  };

  return (
    <>
      <div>
        <div className="mb-3">
          <label htmlFor="date-picker" className="form-label">
            Appointment Date:
          </label>
          <input
            name="apptDate"
            onChange={handleDateChange}
            type="date"
            className="form-control"
            id="date-picker"
            placeholder="20/4/2023"
          />
        </div>
        <div>
          <h5>Timeslots:</h5>
          <div className="d-flex gap-2">
            {timeslots.length < 1 && (
              <p>There is no available timeslots for booking...</p>
            )}
            {timeslots.map((val: any, idx: number) => (
              <div className="m-1" key={idx}>
                <input
                  className="form-check-input"
                  type="radio"
                  name="apptTime"
                  id={`apptTime${idx}`}
                  value={idx}
                  onChange={(event: ChangeEvent<HTMLInputElement>) =>
                    setFormData((prev: any) => {
                      const obj: any = timeslots[event.target.value];
                      return {
                        ...prev,
                        apptId: obj.appointmentId,
                        apptTime: obj.apptTime,
                      };
                    })
                  }
                />
                <label
                  className="form-check-label ps-2"
                  htmlFor={`apptTime${idx}`}
                >
                  {val.apptTime}
                </label>
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}
