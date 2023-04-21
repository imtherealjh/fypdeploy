export function getTouchEventData(
  e:
    | TouchEvent
    | MouseEvent
    | React.MouseEvent<HTMLDivElement>
    | React.TouchEvent<HTMLDivElement>
) {
  return "changedTouches" in e ? e.changedTouches[0] : e;
}
