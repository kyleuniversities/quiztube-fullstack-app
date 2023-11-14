import { Button, Container, Form, Header } from 'semantic-ui-react';
import { useNavigate, useParams } from 'react-router';
import { SitePage } from '../SitePage';
import { MultilineBreak } from '../MultilineBreak';
import { request } from '../../common/util/request';
import { useState } from 'react';
import '../index.css';

/**
 * Page for registering as a user into the site
 */
export const RegistrationPage = (): JSX.Element => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  return (
    <SitePage>
      <Container fluid className="formContainer">
        <Header>Create an Account</Header>
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
        </Form>
        <MultilineBreak lines={1} />
        <Button
          fluid
          color="blue"
          content="Submit"
          onClick={() => addUser(navigate, username, email, password)}
        />
      </Container>
    </SitePage>
  );
};

const addUser = (
  navigate: any,
  username: string,
  email: string,
  password: string
): void => {
  const method = 'POST';
  try {
    const user = {
      username,
      email,
      password,
    };
    const options = {
      method: method,
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    };
    const requestUrl = `/users`;
    request(requestUrl, options).then((data) => {
      if (!data.username || !data.email) {
        alert(method + ' User failed!');
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
  }
};
