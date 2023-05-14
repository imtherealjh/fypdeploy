import { useEffect, useRef } from "react";

import { getRefValue } from "../hooks/hooks";
import { getTouchEventData } from "../hooks/dom";

import "../css/carousel.css";
import { CarouselItems } from "../hooks/types";

export type Props = {
  items: Array<CarouselItems>;
};

export default function Carousel({ items }: Props) {
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
          className="postition-relative carousel"
        >
          <div style={{ fontSize: "1.1rem" }} className="d-block w-100">
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
              reprehenderit in voluptate velit esse cillum dolore eu fugiat
              nulla pariatur. Excepteur sint occaecat cupidatat non proident,
              sunt in culpa qui officia deserunt mollit anim id est laborum.
            </p>
            <div className="d-flex justify-content-end">
              <span>John - 5 stars</span>
            </div>
          </div>
          <div style={{ fontSize: "1.1rem" }} className="d-block w-100">
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
              reprehenderit in voluptate velit esse cillum dolore eu fugiat
              nulla pariatur. Excepteur sint occaecat cupidatat non proident,
              sunt in culpa qui officia deserunt mollit anim id est laborum.
            </p>
            <div className="d-flex justify-content-end">
              <span>John - 5 stars</span>
            </div>
          </div>
          {items.map((item, idx) => (
            <img className="rounded-circle" key={"carousel" + idx} />
          ))}
        </div>
      </div>
    </>
  );
}
