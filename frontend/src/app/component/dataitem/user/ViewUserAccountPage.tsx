import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { SitePage } from '../../SitePage';
import { useUsername } from '../../context/AppContextManager';
import { collectPicturePath } from '../../../service/file';
import { Button, Container, Image } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
import { NULL_USER, loadUserRequest } from '../../../service/entity/user';

export const ViewUserAccountPage = (): JSX.Element => {
  // Set up parameter data
  const { id } = useParams();

  // Set up user viewing data
  const [user, setUser] = useState(NULL_USER);

  // Set up user session data
  const username = useUsername();

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
      </Container>
    </SitePage>
  );
};
