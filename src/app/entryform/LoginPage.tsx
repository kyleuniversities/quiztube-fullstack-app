import { Button, Container, Form, Header } from 'semantic-ui-react';
import { useNavigate, useParams } from 'react-router';
import { SitePage } from '../SitePage';
import { MultilineBreak } from '../MultilineBreak';
import { request } from '../../common/util/request';
import { useState } from 'react';
import { useAuthorization } from '../auth/AuthorizationContextManager';

/**
 * Page for logging in as a user into the site
 */
export const LoginPage = (): JSX.Element => {
  const navigate = useNavigate();
  const userContext: any = useAuthorization();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  return (
    <SitePage>
      <Container fluid className="formContainer">
        <Header>Log In</Header>
        <Form>
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
        </Form>
        <MultilineBreak lines={1} />
        <Button
          fluid
          color="blue"
          content="Submit"
          onClick={() => loginAction(navigate, userContext, username, password)}
        />
      </Container>
    </SitePage>
  );
};

/**
 * Function to login to the site
 */
const loginAction = (
  navigate: any,
  userContext: any,
  username: string,
  password: string
): void => {
  const logIn = userContext.logIn;
  logIn({ username, password }).then((data: any) => {
    if (!data.token) {
      alert('ERROR: Incorrect username or password');
      return;
    }
    alert('Login Response: ' + JSON.stringify(data));
    navigate('/');
    window.location.reload();
  });
  /*const method = 'POST';
  try {
    const user = {
      username,
      password,
    };
    const options = {
      method: method,
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Basic ' + btoa('user:pass'),
      },
      body: JSON.stringify(user),
    };
    const requestUrl = `/users/login`;
    request(requestUrl, options).then((data) => {
      alert('Login Response: ' + JSON.stringify(data));
      navigate('/');
      window.location.reload();
      return;
      if (!data.username || !data.email) {
        alert('Login User failed!');
        alert('Error: ' + JSON.stringify(data));
        return;
      }
      alert(method + ' Operation success!');
      alert('User: ' + JSON.stringify(data));
      navigate('/');
      window.location.reload();
    });
  } catch (error: any) {
    alert('User could not be ' + method + "'ed");
  }/**/
};
