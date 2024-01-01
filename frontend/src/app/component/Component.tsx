import { ReactNode } from 'react';
import { Link } from 'react-router-dom';
import './index.css';

/**
 * Utility component that buttons a Link and a Button
 */
export const LinkButton = (props: {
  to: string;
  className: string;
  children: ReactNode;
}): JSX.Element => {
  return (
    <Link to={props.to}>
      <button className={props.className}>{props.children}</button>
    </Link>
  );
};

/**
 * Utility component for centralizing content
 */
export const CentralContainer = (props: {
  children: ReactNode;
}): JSX.Element => {
  return <div className="centralContainer">{props.children}</div>;
};
