import { ReactNode, useRef, useState } from "react";

import "../css/dropdown.css";
import { useOutsideClick } from "../hooks/hooks";

interface Props {
  buttonContent: ReactNode;
  menuContent: ReactNode;
}

export default function Dropdown({ buttonContent, menuContent }: Props) {
  const ref = useRef<any>(null);
  const [visible, setVisiblity] = useState(false);

  useOutsideClick(ref, () => setVisiblity(false));

  return (
    <>
      <div className="dropdown">
        <button
          type="button"
          onClick={() => setVisiblity(true)}
          aria-haspopup="true"
          aria-expanded={visible}
        >
          {buttonContent}
        </button>
        {visible && (
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
