import { Link } from "react-router-dom";

import { useEffect, useState } from "react";

import axios from "../api/axios";
import Carousel from "../components/carousel";
import { CarouselItems } from "../hooks/types";

import PatientDemo from "../assets/PatientDemo.mp4";
import ClinicDemo from "../assets/ClinicDemo.mp4";

import "../css/home.css";

export default function Home() {
  const [current, setCurrent] = useState<"patient" | "clinic">("patient");
  const [feedback, setFeedback] = useState<CarouselItems[]>([]);
  const classList = "btn btn-outline-dark btn-lg";

  const handleButtonClick = (e: any) => {
    setCurrent(e.target.value);
  };

  useEffect(() => {
    let isMounted = true;
    let controller = new AbortController();

    const fetchData = async () => {
      try {
        let response = await axios.get("/public/getLandingPageData", {
          signal: controller.signal,
        });

        isMounted && setFeedback(response.data);
      } catch (err) {
        console.error(err);
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
      <div className="homepage-wrapper d-flex flex-column">
        <div className="video-container w-100">
          {current === "patient" && (
            <video src={PatientDemo} autoPlay controls></video>
          )}
          {current === "clinic" && (
            <video src={ClinicDemo} autoPlay controls></video>
          )}
          <div>
            {/* <p>Your Best Value Proposition</p> */}
            <p>
              GoDoctor is a platform to transform your medical appointment
              experience. Join us today and discover a seamless, secure, and
              efficient healthcare journey!
            </p>
          </div>
        </div>
        <div className="trusted-container d-flex align-items-center flex-column my-4">
          <p>Trusted by Leading Medical Institution</p>
          <Carousel feedback={feedback} />
        </div>
        <div className="register-container my-4">
          <div className="btnSet">
            <button
              value="patient"
              className={classList + (current === "patient" ? " active" : "")}
              type="button"
              onClick={handleButtonClick}
            >
              Patients
            </button>
            <button
              value="clinic"
              className={classList + (current === "clinic" ? " active" : "")}
              type="button"
              onClick={handleButtonClick}
            >
              Clinics
            </button>
          </div>
          <div className="d-flex my-5">
            {current === "patient" && (
              <>
                <div>
                  <p>
                    Say goodbye to long waiting times and complex appointment
                    booking. With GoDoctor, easily schedule, manage, and update
                    your appointments at your preferred clinics, all in one
                    user-friendly platform. Empower yourself with health
                    educational materials and personalized health promotion
                    messages to improve your well-being.
                  </p>
                  <div>
                    <Link to="/registerPatient">
                      <button className="btn btn-dark btn-lg" type="button">
                        Sign Up
                      </button>
                    </Link>
                  </div>
                </div>
              </>
            )}
            {current === "clinic" && (
              <>
                <div>
                  <p>
                    Enhance your patients' experience by simplifying appointment
                    management, streamlining patient records, and optimizing
                    billing information. Leverage real-time queue monitoring and
                    customer feedback to continuously refine your services and
                    elevate patient satisfaction.
                  </p>
                  <div>
                    <Link to="/registerClinic">
                      <button className="btn btn-dark btn-lg" type="button">
                        Sign Up
                      </button>
                    </Link>
                    <button
                      className="btn btn-outline-dark btn-lg"
                      type="button"
                    >
                      Contact Us
                    </button>
                  </div>
                </div>
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
