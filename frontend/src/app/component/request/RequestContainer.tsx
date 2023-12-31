import { Button, Container, Form, Segment, TextArea } from 'semantic-ui-react';
import { Dispatch, ReactNode, SetStateAction, useState } from 'react';
import { deriveApiHost, fullRequest } from '../../service/request';
import { MultilineBreak } from '../MultilineBreak';
import './index.css';

/**
 * Container for making a GET request
 */
export const RequestContainer = () => {
  // Set up field data
  const [fullUrl, setFullUrl] = useState(deriveApiHost());
  const [res, setRes] = useState('');

  // Return component
  return (
    <RequestContainerSegment>
      <Form>
        <RequestContainerUrlTextField
          fullUrl={fullUrl}
          setFullUrl={setFullUrl}
        />
        <RequestContainerSubmitButton fullUrl={fullUrl} setRes={setRes} />
      </Form>
      <MultilineBreak lines={3} />
      <RequestContainerResponseContainer res={res} />
    </RequestContainerSegment>
  );
};

// Wrapper for the Request Container
const RequestContainerSegment = (props: { children: ReactNode }) => {
  return (
    <Segment className="requestContainerSegment">
      <h1>Request Container</h1>
      {props.children}
    </Segment>
  );
};

// Url text field for the Request Container
const RequestContainerUrlTextField = (props: {
  fullUrl: string;
  setFullUrl: Dispatch<SetStateAction<string>>;
}) => {
  return (
    <Form.Input
      label="Enter your request URL"
      value={props.fullUrl}
      onChange={(e) => props.setFullUrl(e.target.value)}
    />
  );
};

// Button for sending the request
const RequestContainerSubmitButton = (props: {
  fullUrl: string;
  setRes: Dispatch<SetStateAction<string>>;
}) => {
  return (
    <Button
      onClick={() =>
        fullRequest(props.fullUrl).then((data) =>
          props.setRes(JSON.stringify(data))
        )
      }
    >
      Submit
    </Button>
  );
};

// Container for the response data
const RequestContainerResponseContainer = (props: { res: string }) => {
  return (
    <Container fluid>
      <h3>Response: </h3>
      <TextArea
        fluid
        disabled
        className="requestContainerResponseContainer"
        value={props.res}
      />
    </Container>
  );
};
