import React from "react";
import ViewArticles from "../../components/ViewArticles";
import "../../css/patienthome.css";

function Home() {
  return (
    <div>
      <h1>Dashboard</h1>
      <div className="component-container">
        <ViewArticles />
      </div>
    </div>
  );
}

export default Home;
