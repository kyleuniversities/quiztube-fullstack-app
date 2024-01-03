import { ReactNode } from 'react';
import { ConditionalContent } from '../ConditionalContent';

/**
 * Container for a loaded resource
 */
export const LoadedResourceContainer = (props: {
  entityName: string;
  id: string | undefined;
  isAbsent: boolean;
  children: ReactNode;
}) => {
  return (
    <div>
      <ConditionalContent condition={props.isAbsent}>
        <AbsentContent entityName={props.entityName} id={props.id} />
      </ConditionalContent>
      <ConditionalContent condition={!props.isAbsent}>
        {props.children}
      </ConditionalContent>
    </div>
  );
};

/**
 * Content for an absent resource
 */
const AbsentContent = (props: {
  entityName: string;
  id: string | undefined;
}): JSX.Element => {
  // Set up text data
  const titleText = props.entityName;
  const commonNounText = props.entityName.toLowerCase();

  // Return component
  return (
    <>
      <h1>{titleText} Does Not Exist.</h1>
      <p>
        I'm sorry, the {commonNounText} with id{' '}
        <i>
          <b>"{props.id}"</b>
        </i>{' '}
        does not exist.
      </p>
    </>
  );
};
