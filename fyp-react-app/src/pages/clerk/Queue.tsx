import React, { useState } from "react";
import "../../css/queue.css";
import axiosPrivate from "../../api/axios";

interface QueueItem {
  id: number;
  queueNumber: number;
  status: string;
}

const initialQueue: QueueItem[] = [
  { id: 1, queueNumber: 1, status: "Consulting" },
  { id: 2, queueNumber: 2, status: "In queue" },
  { id: 3, queueNumber: 3, status: "In queue" },
];

function Queue() {
  const [queue, setQueue] = useState(initialQueue);

  const updateStatus = async (id: number, newStatus: string) => {
    // Update the status in the backend.
    // Replace the URL and data format to match API.
    try {
      await axiosPrivate.post("/queue/update", { id, status: newStatus });
      console.log(`Updated status of queue number ${id} to ${newStatus}`);
    } catch (error) {
      console.error("Error updating status:", error);
    }

    // Update the status in the local state.
    setQueue(
      queue.map((item) =>
        item.id === id ? { ...item, status: newStatus } : item
      )
    );
  };

  return (
    <div className="queue-container">
      <h1>Queue</h1>
      <table>
        <thead>
          <tr>
            <th>Queue Number</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {queue.map((item) => (
            <tr key={item.id}>
              <td>{item.queueNumber}</td>
              <td>{item.status}</td>
              <td>
                <button onClick={() => updateStatus(item.id, "Consulting")}>
                  Set Consulting
                </button>
                <button onClick={() => updateStatus(item.id, "In queue")}>
                  Set In queue
                </button>
                <button
                  onClick={() =>
                    updateStatus(item.id, "Awaiting medication/Done")
                  }
                >
                  Waiting/Done
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Queue;
