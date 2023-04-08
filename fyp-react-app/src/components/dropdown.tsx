import { ReactNode, useEffect, useRef, useState } from "react";

import "../css/dropdown.css";
import { useOutsideClick } from "../utils/hooks";

interface Props {
  buttonContent: ReactNode;
  menuContent: ReactNode;
  open: true | false;
  onClick: () => void;
  onOutsideClick: () => void;
}

export default function Dropdown({
  buttonContent,
  menuContent,
  open,
  onClick,
  onOutsideClick,
}: Props) {
  const ref = useRef<any>(null);

  useOutsideClick(ref, onOutsideClick);

  return (
    <>
      <div className="dropdown">
        <button
          onClick={onClick}
          type="button"
          aria-haspopup="true"
          aria-expanded={open}
        >
          {buttonContent}
        </button>
        {open && (
          <>
            <div ref={ref} className="dropdown-menu-content">
              <div className="content">{menuContent}</div>
            </div>
          </>
        )}
      </div>
    </>
  );
}
