import { useEffect, useRef } from "react";

import { getRefValue } from "../hooks/hooks";
import { getTouchEventData } from "../hooks/dom";

import "../css/carousel.css";
import { CarouselItems } from "../hooks/types";

export type Props = {
  feedback: Array<CarouselItems>;
};

export default function Carousel({ feedback }: Props) {
  const carouselRef = useRef<HTMLDivElement>(null);
  let scrollWidthRef = useRef(0),
    prevClientXRef = useRef(0),
    prevScrollLeftRef = useRef(0);

  const onTouchStart = (
    e: React.TouchEvent<HTMLDivElement> | React.MouseEvent<HTMLDivElement>
  ) => {
    const carousel = getRefValue(carouselRef),
      event = getTouchEventData(e);

    prevClientXRef.current = event.clientX;
    prevScrollLeftRef.current = carousel.scrollLeft;

    window.addEventListener("touchmove", onTouchMove);
    window.addEventListener("mousemove", onTouchMove);

    window.addEventListener("touchend", onTouchEnd);
    window.addEventListener("mouseup", onTouchEnd);
  };

  const onTouchMove = (e: MouseEvent | TouchEvent) => {
    e.preventDefault();
    const carousel = getRefValue(carouselRef),
      prevClientX = getRefValue(prevClientXRef),
      prevScrollLeft = getRefValue(prevScrollLeftRef),
      event = getTouchEventData(e);

    carousel.classList.add("dragging");
    let positionDiff = event.clientX - prevClientX;
    carousel.scrollLeft = prevScrollLeft - positionDiff;
  };

  const onTouchEnd = () => {
    const carousel = getRefValue(carouselRef);
    carousel.classList.remove("dragging");

    window.removeEventListener("touchmove", onTouchMove);
    window.removeEventListener("mousemove", onTouchMove);

    window.removeEventListener("touchend", onTouchEnd);
    window.removeEventListener("mouseup", onTouchEnd);
  };

  useEffect(() => {
    const carousel = getRefValue(carouselRef);
    scrollWidthRef.current = carousel.scrollWidth - carousel.clientWidth;
  }, []);

  return (
    <>
      <div className="d-flex w-100 wrapper">
        <div
          ref={carouselRef}
          onTouchStart={onTouchStart}
          onMouseDown={onTouchStart}
          className="d-flex carousel gap-3"
        >
          {feedback.map((feedback, idx) => (
            <figure key={idx} className="text-end">
              <blockquote className="blockquote">
                <h5>{feedback.clinic}</h5>
                <p>{feedback.content}</p>
              </blockquote>
              <figcaption className="blockquote-footer">
                {feedback.ratings}
              </figcaption>
            </figure>
          ))}
        </div>
      </div>
    </>
  );
}
