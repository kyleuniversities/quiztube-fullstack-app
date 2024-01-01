import { Card, Container, Icon } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { request } from '../../../service/request';
import { MultilineBreak } from '../../MultilineBreak';
import { Link, useParams } from 'react-router-dom';
import { useUsername } from '../../context/AppContextManager';
import { NULL_USER, loadUserRequest } from '../../../service/entity/user';
import { loadQuizzesFromUserRequest } from '../../../service/entity/quiz';
import { ConditionalContent } from '../../ConditionalContent';

/**
 * Page to View All Quizzes
 */
export const ViewUserQuizzesPage = () => {
  // Set up parameter data
  const { id } = useParams();

  // Set up user and quiz data
  const [user, setUser] = useState(NULL_USER);
  const [quizPosts, setQuizPosts] = useState([]);
  const username = useUsername();

  // Load user and quizzes
  useEffect(() => {
    loadUserRequest(id, setUser);
    loadQuizzesFromUserRequest(id, setQuizPosts);
  }, [id]);

  // Return component
  return (
    <SitePage>
      <Container fluid>
        <h1>
          {user.username === username
            ? 'My Quizzes'
            : `Quizzes by ${user.username}`}
        </h1>
        <ConditionalContent condition={quizPosts.length === 0}>
          This user has no quizzes yet.
        </ConditionalContent>
        <ConditionalContent condition={quizPosts.length > 0}>
          <Card.Group itemsPerRow={5}>
            {quizPosts.map((quizPost: any) => {
              return (
                <Link to={`/quizzes/${quizPost.id}`}>
                  <Card style={{ marginLeft: '10px' }}>
                    <Card.Content style={{ height: '110px' }}>
                      <Card.Header>{quizPost.title}</Card.Header>
                      <Card.Description>
                        {quizPost.description}
                      </Card.Description>
                    </Card.Content>
                    <span>
                      <Icon name="heart" /> {quizPost.numberOfLikes}
                    </span>
                  </Card>
                </Link>
              );
            })}
          </Card.Group>
        </ConditionalContent>
        <MultilineBreak lines={3} />
      </Container>
    </SitePage>
  );
};
