import { useRef, useState, ChangeEvent, useEffect } from "react";
import useAxiosPrivate from "../../../../hooks/useAxiosPrivate";

interface Props {
  formData: any;
  setFormData: React.Dispatch<React.SetStateAction<any>>;
}

export default function Step1({ formData, setFormData }: Props) {
  const axiosPrivate = useAxiosPrivate();
  const doctorRef = useRef<HTMLSelectElement>(null);
  const [speciality, setSpeciality] = useState([]);
  const [clinic, setClinic] = useState<any>([]);
  const [doctor, setDoctor] = useState<any>([]);

  let isMounted = false;
  const handleSpecialtyChange = async (
    event: ChangeEvent<HTMLSelectElement>
  ) => {
    if (!isMounted) {
      isMounted = true;
      try {
        const response = await axiosPrivate.get(
          `/patient/getClinicsBySpecialty?specialty=${event.target.value}`
        );
        isMounted && setClinic(response.data);
        //trigger change on doctor select box
        doctorRef.current?.dispatchEvent(
          new Event("change", { bubbles: true })
        );
      } catch (error) {
        console.log(error);
      }

      isMounted = false;
    }
  };

  const handleClinicChange = (event: ChangeEvent<HTMLSelectElement>) => {
    const id: number = +event.target.value;
    setDoctor(clinic.find((x: any) => x.clinicId == id)?.doctor ?? []);
  };

  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();
    const fetchData = async () => {
      try {
        const response = await axiosPrivate.get("/public/getAllSpecialty", {
          signal: controller.signal,
        });

        const _specialty = response.data.map((obj: any) => {
          return obj["type"];
        });

        isMounted && setSpeciality(_specialty);
      } catch (err) {
        console.log(err);
      }
    };

    fetchData();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);
  return (
    <>
      <div className="d-grid gap-2 d-md-flex justify-content-md-end">
        <select
          className="form-select"
          defaultValue={"DEFAULT"}
          aria-label="Open this to select specialty"
          onChange={handleSpecialtyChange}
        >
          <option value="DEFAULT" disabled>
            Open this to select specialty
          </option>
          {speciality.map((val, idx) => (
            <option key={idx} value={val}>
              {val}
            </option>
          ))}
        </select>
        <select
          ref={doctorRef}
          className="form-select"
          defaultValue={"DEFAULT"}
          aria-label="Open this to select clinic"
          onChange={handleClinicChange}
        >
          <option value="DEFAULT" disabled>
            Open this to select clinic
          </option>
          {clinic.map((val: any, idx: number) => (
            <option
              key={idx}
              value={val.clinicId}
            >{`${val.clinicName} - ${val.location}`}</option>
          ))}
        </select>
        <select
          className="form-select"
          defaultValue={"DEFAULT"}
          aria-label="Open this to select doctor"
          onChange={(event: ChangeEvent<HTMLSelectElement>) =>
            setFormData((prev: any) => {
              return { ...prev, doctorId: event.target.value };
            })
          }
        >
          <option value="DEFAULT" disabled>
            Open this to select doctor
          </option>
          {doctor.map((val: any, idx: number) => (
            <option key={idx} value={val.doctorId}>
              {val.name}
            </option>
          ))}
        </select>
      </div>
    </>
  );
}
