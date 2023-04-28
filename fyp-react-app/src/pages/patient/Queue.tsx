import React, { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

interface Appointment {
  clinic: string;
  doctor: string;
  date: string;
  time: string;
  queueNumber: string | null;
}

interface QueueStatus {
  asAt: string;
  currentQueueNumber: string;
  userQueueNumber: string | null;
}

export default function Queue() {
  return <>Queue</>;
}
