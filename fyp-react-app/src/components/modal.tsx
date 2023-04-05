import { ReactNode } from "react";

interface Props {
  name: string;
  style: any;
  children: ReactNode;
}

export default function Modal({ name, style, children }: Props) {
  return (
    <>
      <button
        type="button"
        style={style}
        className="btn"
        data-bs-toggle="modal"
        data-bs-target="#modal"
      >
        {name}
      </button>
      <div
        className="modal fade"
        id="modal"
        data-bs-keyboard="false"
        tabIndex={-1}
        aria-labelledby={name + "modal"}
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content py-3">{children}</div>
        </div>
      </div>
    </>
  );
}
