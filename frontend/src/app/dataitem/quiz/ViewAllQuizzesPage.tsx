import { Card, Container, Icon } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { request } from '../../../common/util/request';
import { MultilineBreak } from '../../MultilineBreak';

/**
 * Page to View All Quizzes
 */
export const ViewAllQuizzesPage = () => {
  const [subjects, setSubjects] = useState([]);
  useEffect(() => {
    request('/subjects').then((res) => {
      setSubjects(res);
    });
  }, []);
  return (
    <SitePage>
      <div
        style={{ marginLeft: '15px', marginTop: '10px', marginRight: '15px' }}
      >
        {subjects.map((subject: any) => {
          return <ViewAllQuizzesSubContainer subject={subject} />;
        })}
      </div>
    </SitePage>
  );
};

/**
 * Sub Container to View All Arts Quizzes
 */
const ViewAllQuizzesSubContainer = (props: { subject: any }) => {
  const [quizPosts, setQuizPosts] = useState([]);
  useEffect(() => {
    request(`/quizzes/posts/${props.subject.id}`).then((res) => {
      setQuizPosts(res);
    }); /**/
  }, [props.subject.id]);
  return (
    <Container fluid>
      <h1>{props.subject.text}</h1>
      <Card.Group itemsPerRow={5}>
        {quizPosts.map((quizPost: any) => {
          return (
            <Card style={{ marginLeft: '10px' }}>
              <Card.Content style={{ height: '110px' }}>
                <Card.Header>{quizPost.title}</Card.Header>
                <Card.Description>{quizPost.description}</Card.Description>
              </Card.Content>
              <span>
                <Icon name="heart" /> {quizPost.numberOfLikes}
              </span>
            </Card>
          );
        })}
      </Card.Group>
      <MultilineBreak lines={3} />
    </Container>
  );
};
