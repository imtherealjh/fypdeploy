import React from "react";
import "react-calendar/dist/Calendar.css";
import Calendar from "react-calendar";

function CustomCalendar() {
  const [value, setValue] = React.useState<any>(new Date());

  return (
    <div>
      <Calendar onChange={setValue} value={value} />
    </div>
  );
}

export default CustomCalendar;
