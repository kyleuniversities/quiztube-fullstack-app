import { Card, Container, Icon } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { Link } from 'react-router-dom';
import { loadSubjectsRequest } from '../../../service/entity/subject';
import { loadQuizzesFromSubjectRequest } from '../../../service/entity/quiz';

/**
 * Page to View All Quizzes
 */
export const ViewAllQuizzesPage = () => {
  // Set up subject section data
  const [subjects, setSubjects] = useState([]);

  // Load subjects
  useEffect(() => {
    loadSubjectsRequest(setSubjects);
  }, []);

  // Return component
  return (
    <SitePage>
      {subjects.map((subject: any) => {
        return <ViewAllQuizzesSubContainer subject={subject} />;
      })}
    </SitePage>
  );
};

/**
 * Sub Container to View All Arts Quizzes
 */
const ViewAllQuizzesSubContainer = (props: { subject: any }) => {
  // Set up quiz data
  const [quizPosts, setQuizPosts] = useState([]);

  // Load quizzes
  useEffect(() => {
    loadQuizzesFromSubjectRequest(props.subject.id, setQuizPosts);
  }, [props.subject.id]);

  // Return component
  return (
    <Container fluid>
      <h1>{props.subject.text}</h1>
      <Card.Group itemsPerRow={5}>
        {quizPosts.map((quizPost: any) => {
          return (
            <Link to={`/quizzes/${quizPost.id}`}>
              <Card
                style={{
                  width: '280px',
                  marginLeft: '10px',
                  marginBottom: '12px',
                }}
              >
                <Card.Content style={{ height: '110px' }}>
                  <Card.Header>{quizPost.title}</Card.Header>
                  <Card.Description>{quizPost.description}</Card.Description>
                </Card.Content>
                <div
                  style={{
                    color: 'rgb(0,120,210)',
                    marginLeft: '10px',
                    marginBottom: '0px',
                  }}
                >
                  <span>
                    <Icon name="heart" /> {quizPost.numberOfLikes}
                  </span>
                </div>
              </Card>
            </Link>
          );
        })}
      </Card.Group>
      <MultilineBreak lines={3} />
    </Container>
  );
};
