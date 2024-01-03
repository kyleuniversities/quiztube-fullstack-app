/**
 * Utility component for generating multiple line breaks concisely
 */
export const MultilineBreak = (props: { lines: number }): JSX.Element => {
  const breakList = [];
  for (let i = 0; i < props.lines; i++) {
    breakList.push(<br key={`m${i}`}></br>);
  }
  return <>{breakList}</>;
};
