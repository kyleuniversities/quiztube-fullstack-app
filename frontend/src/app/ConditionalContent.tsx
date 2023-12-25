import { ReactNode } from 'react';

/**
 * Utility component for providing content under a specified condition
 *
 * @param boolean condition - The condition that determines if the content is present
 * @param ReactNode children - The children content that is present underthe condition
 */
export const ConditionalContent = (props: {
  condition: boolean;
  children: ReactNode;
}): JSX.Element => {
  return <>{props.condition ? props.children : <></>}</>;
};
