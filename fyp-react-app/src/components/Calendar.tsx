import React from "react";
import "react-calendar/dist/Calendar.css";
import Calendar from "react-calendar";

function CustomCalendar() {
  const [value, setValue] = React.useState(new Date());

  const onChange = (value: Date | Date[]) => {
    setValue(value as Date);
  };

  return (
    <div>
      <Calendar onChange={onChange} value={value} />
    </div>
  );
}

export default CustomCalendar;
