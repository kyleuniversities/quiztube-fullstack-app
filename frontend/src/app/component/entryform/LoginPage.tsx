import { Button, Container, Form, Header } from 'semantic-ui-react';
import { useNavigate } from 'react-router';
import { SitePage } from '../SitePage';
import { MultilineBreak } from '../MultilineBreak';
import { useState } from 'react';
import { loginRequest } from '../../service/auth';
import { useAppContext, useColorize } from '../context/AppContextManager';
import './index.css';

/**
 * Page for logging in as a user into the site
 */
export const LoginPage = (): JSX.Element => {
  // Set up field data
  const [username, setUsername] = useState('');
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
        <Header id={colorize('formHeader')}>Log In</Header>
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
              loginRequest(navigate, userContext, username, password)
            }
          />
        </Form>
      </Container>
    </SitePage>
  );
};
