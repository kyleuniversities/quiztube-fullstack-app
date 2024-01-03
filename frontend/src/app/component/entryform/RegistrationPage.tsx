import { Button, Container, Form, Header } from 'semantic-ui-react';
import { useNavigate } from 'react-router';
import { SitePage } from '../SitePage';
import { MultilineBreak } from '../MultilineBreak';
import { useState } from 'react';
import { addUserRequest } from '../../service/entity/user';
import '../index.css';
import { useAppContext, useColorize } from '../context/AppContextManager';

/**
 * Page for registering as a user into the site
 */
export const RegistrationPage = (): JSX.Element => {
  // Set up field data
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  // Set up user data
  const userContext = useAppContext();

  // Set up color data
  const colorize = useColorize();

  // Set up navigation
  const navigate = useNavigate();

  // Return component
  return (
    <SitePage>
      <Container fluid className="formContainer">
        <Header id={colorize('formHeader')}>Create an Account</Header>
        <Form id={colorize('formWrapper')}>
          <Form.Input
            fluid
            label="Username"
            placeholder="Enter a Username"
            value={username}
            onChange={(e: any) => setUsername(e.target.value)}
          />
          <Form.Input
            fluid
            label="Email"
            placeholder="Enter an Email"
            value={email}
            onChange={(e: any) => setEmail(e.target.value)}
          />
          <Form.Input
            fluid
            label="Password"
            placeholder="Enter a password"
            type="password"
            value={password}
            onChange={(e: any) => setPassword(e.target.value)}
          />
          <MultilineBreak lines={1} />
          <Button
            fluid
            color="blue"
            content="Submit"
            onClick={() =>
              addUserRequest(navigate, userContext, username, email, password)
            }
          />
        </Form>
      </Container>
    </SitePage>
  );
};
