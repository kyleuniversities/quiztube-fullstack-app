import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import { SitePage } from '../../SitePage';
import {
  useAppContext,
  useUserId,
  useUsername,
} from '../../context/AppContextManager';
import { collectPicturePath } from '../../../service/file';
import { Button, Container, Image } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
import {
  NULL_USER,
  deleteUserRequest,
  loadUserRequest,
} from '../../../service/entity/user';
import { ConditionalContent } from '../../ConditionalContent';

export const ViewUserAccountPage = (): JSX.Element => {
  // Set up parameter data
  const { id } = useParams();

  // Set up user viewing data
  const [user, setUser] = useState(NULL_USER);

  // Set up user session data
  const userContext = useAppContext();
  const userId = useUserId();
  const username = useUsername();

  // Set up navigation
  const navigate = useNavigate();

  // Load user
  useEffect(() => {
    loadUserRequest(id, setUser);
  }, [id]);

  // Return component
  return (
    <SitePage>
      <Container fluid>
        <h1>
          {user.username === username ? 'My Account' : `${user.username}`}
        </h1>
        <Image src={collectPicturePath(user)} />
        <p>
          <b>Username: </b>
          {user.username}
        </p>
        <p>
          <b>Email: </b>
          {user.email}
        </p>
        <Link to={`/users/${id}/quizzes`}>
          <Button icon="file alternate" color="orange" content="View Quizzes" />
        </Link>
        <ConditionalContent condition={userId === id}>
          <Button
            icon="trash"
            color="red"
            content="Delete Account"
            onClick={() => deleteUserRequest(navigate, userContext, id)}
          />
        </ConditionalContent>
      </Container>
    </SitePage>
  );
};
