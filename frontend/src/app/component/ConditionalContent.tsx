import { ReactNode } from 'react';

/**
 * Utility component for providing content under a specified condition
 */
export const ConditionalContent = (props: {
  condition: boolean;
  children: ReactNode;
}): JSX.Element => {
  return <>{props.condition ? props.children : <></>}</>;
};
