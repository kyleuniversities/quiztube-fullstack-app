import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { SitePage } from '../../SitePage';
import { request } from '../../../common/util/request';
import { useUsername } from '../../context/AppContextManager';
import {
  collectPicturePath,
  collectThumbnailPath,
} from '../../../common/util/picture';
import { Button, Container, Image } from 'semantic-ui-react';
import { Link } from 'react-router-dom';

const DEFAULT_USER: any = {
  username: '#null',
  picture: '#null',
};

export const ViewUserAccountPage = (): JSX.Element => {
  const { id } = useParams();
  const [user, setUser] = useState(DEFAULT_USER);
  const username = useUsername();
  useEffect(() => {
    request(`/users/${id}`).then((userRes) => {
      setUser(userRes);
    });
  }, [id]);
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
