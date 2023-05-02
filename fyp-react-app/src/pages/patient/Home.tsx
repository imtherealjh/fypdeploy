import React from "react";
import ViewArticles from "../../components/ViewArticles";
import UpcomingAppointments from "../../components/UpcomingAppointments";
import PastAppointments from "../../components/PastAppointments";
import "../../css/patienthome.css";

function Home() {
  return (
    <div>
      <h1>Dashboard</h1>
      <div className="component-container">
        <ViewArticles />
      </div>
      <div className="component-container">
        <UpcomingAppointments />
      </div>
      <div className="component-container">
        <PastAppointments />
      </div>
    </div>
  );
}

export default Home;
