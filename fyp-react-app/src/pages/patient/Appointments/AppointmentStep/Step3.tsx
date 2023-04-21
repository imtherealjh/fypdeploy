interface Props {
  formData: any;
}

export default function Step3({ formData }: Props) {
  return (
    <>
      <h6 className="d-flex flex-column gap-2">
        <span>Are you sure you want to book this appointment?</span>
        <span>Doctor Id : {formData.doctorId} </span>
        <span>Appointment Date : {formData.apptDate} </span>
        <span>Appointment Time : {formData.apptTime} </span>
      </h6>
    </>
  );
}
