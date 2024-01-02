import { Card, Icon } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { Link, useParams } from 'react-router-dom';
import { useColorize, useUsername } from '../../context/AppContextManager';
import { NULL_USER, loadUserRequest } from '../../../service/entity/user';
import { loadQuizzesFromUserRequest } from '../../../service/entity/quiz';
import { ConditionalContent } from '../../ConditionalContent';
import './index.css';

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

  // Use color data
  const colorize = useColorize();

  // Load user and quizzes
  useEffect(() => {
    loadUserRequest(id, setUser);
    loadQuizzesFromUserRequest(id, setQuizPosts);
  }, [id]);

  // Return component
  return (
    <SitePage>
      <div id="viewUserQuizzesContainer">
        <h1>
          {user.username === username
            ? 'My Quizzes'
            : `Quizzes by ${user.username}`}
        </h1>
        <div className="quizCardGroupContainer">
          <ConditionalContent condition={quizPosts.length === 0}>
            This user has no quizzes yet.
          </ConditionalContent>
          <ConditionalContent condition={quizPosts.length > 0}>
            <Card.Group>
              {quizPosts.map((quizPost: any) => {
                return (
                  <div className="quizCardWrappingContainer">
                    <Link to={`/quizzes/${quizPost.id}`}>
                      <Card className="quizCard">
                        <div className={colorize('quizCardContent')}>
                          <h3 className="quizPostTitle">{quizPost.title}</h3>
                          <p className="quizPostDescription">
                            {quizPost.description}
                          </p>
                        </div>
                        <div className={colorize('quizLikesText')}>
                          <span>
                            <Icon name="heart" /> {quizPost.numberOfLikes}
                          </span>
                        </div>
                      </Card>
                    </Link>
                  </div>
                );
              })}
            </Card.Group>
          </ConditionalContent>
          <MultilineBreak lines={3} />
        </div>
      </div>
    </SitePage>
  );
};
