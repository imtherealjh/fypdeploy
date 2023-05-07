import { useState } from "react";
import "../../css/faq.css";

interface FaqItemProps {
  question: string;
  answer: string;
}

function FaqItem({ question, answer }: FaqItemProps) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="faq-item">
      <div className="faq-question" onClick={() => setIsOpen(!isOpen)}>
        {question}
      </div>
      {isOpen && <div className="faq-answer">{answer}</div>}
    </div>
  );
}

export default function Faq() {
  return (
    <>
      <h1>Frequently Asked Questions</h1>
      <div className="faq-container">
        <FaqItem
          question="When can I apply for a queue number?"
          answer="On the day of your scheduled appointment, you will be able to register for a queue number"
        />
        <FaqItem
          question="How do I book an appointment?"
          answer='You can book an appointment by clicking "Appointment" on the sidebar and following the instructions'
        />
        {/* Add more FaqItem components for more FAQs */}
      </div>
    </>
  );
}
