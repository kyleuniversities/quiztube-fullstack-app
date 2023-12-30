import { Card, Container, Icon } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { request } from '../../../common/util/request';
import { MultilineBreak } from '../../MultilineBreak';
import { Link, useParams } from 'react-router-dom';
import { useUsername } from '../../context/AppContextManager';

const DEFAULT_USER: any = {
  username: '#null',
};

/**
 * Page to View All Quizzes
 */
export const ViewUserQuizzesPage = () => {
  const { id } = useParams();
  const [user, setUser] = useState(DEFAULT_USER);
  const [quizPosts, setQuizPosts] = useState([]);
  const username = useUsername();
  useEffect(() => {
    request(`/users/${id}`).then((userRes) => {
      request(`/users/${id}/quizzes`).then((quizzesRes) => {
        setQuizPosts(quizzesRes);
        setUser(userRes);
      });
    });
  }, [id]);
  return (
    <SitePage>
      <Container fluid>
        <h1>
          {user.username === username
            ? 'My Quizzes'
            : `Quizzes by ${user.username}`}
        </h1>
        <Card.Group itemsPerRow={5}>
          {quizPosts.map((quizPost: any) => {
            return (
              <Link to={`/quizzes/${quizPost.id}`}>
                <Card style={{ marginLeft: '10px' }}>
                  <Card.Content style={{ height: '110px' }}>
                    <Card.Header>{quizPost.title}</Card.Header>
                    <Card.Description>{quizPost.description}</Card.Description>
                  </Card.Content>
                  <span>
                    <Icon name="heart" /> {quizPost.numberOfLikes}
                  </span>
                </Card>
              </Link>
            );
          })}
        </Card.Group>
        <MultilineBreak lines={3} />
      </Container>
    </SitePage>
  );
};
