import { useState, useEffect } from "react";

export function getTouchEventData(
  e:
    | TouchEvent
    | MouseEvent
    | React.MouseEvent<HTMLDivElement>
    | React.TouchEvent<HTMLDivElement>
) {
  return "changedTouches" in e ? e.changedTouches[0] : e;
}

export function useWindowDimensions() {
  const hasWindow = typeof window !== "undefined";

  function getWindowDimensions() {
    const width = hasWindow ? window.innerWidth : null;
    const height = hasWindow ? window.innerHeight : null;
    return {
      width,
      height,
    };
  }

  const [windowDimensions, setWindowDimensions] = useState(
    getWindowDimensions()
  );

  useEffect(() => {
    if (hasWindow) {
      function handleResize() {
        setWindowDimensions(getWindowDimensions());
      }

      window.addEventListener("resize", handleResize);
      return () => window.removeEventListener("resize", handleResize);
    }
  }, [hasWindow]);

  return windowDimensions;
}
