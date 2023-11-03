import { ReactNode } from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'semantic-ui-react';

/**
 * Utility component that buttons a Link and a Button
 *
 * @param string to - The url the link points to
 * @param ReactNode children - The children content of the button
 */
export const LinkButton = (props: {
  to: string;
  children: ReactNode;
}): JSX.Element => {
  return (
    <Link to={props.to}>
      <button
        style={{
          backgroundColor: 'rgb(255, 230, 210)',
          color: 'black',
          paddingLeft: '8px',
          paddingRight: '8px',
          paddingTop: '5px',
          paddingBottom: '5px',
          marginLeft: '0.5em',
          borderColor: 'black',
          borderWidth: '3px',
          borderRadius: '5px',
          fontFamily: 'Helvetica Neue',
          fontSize: '15px',
          fontWeight: 'bold',
        }}
      >
        {props.children}
      </button>
    </Link>
  );
};
