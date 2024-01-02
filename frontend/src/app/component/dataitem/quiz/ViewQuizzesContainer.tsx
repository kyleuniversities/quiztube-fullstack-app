import { Card, Icon } from 'semantic-ui-react';
import { useColorize } from '../../context/AppContextManager';
import { Link } from 'react-router-dom';
import { MultilineBreak } from '../../MultilineBreak';
import { QuizUserThumbnailText } from './QuizUserThumbnailText';

/**
 * Sub Container to View Quizzes
 */
export const ViewQuizzesContainer = (props: {
  title: string;
  quizPosts: any;
}) => {
  // Set up color data
  const colorize = useColorize();

  // Return component
  return (
    <div className="viewAllQuizzesContainer">
      <h1>
        <div className={colorize('quizGroupTitle')}>{props.title}</div>
      </h1>
      <div className="quizCardGroupContainer">
        <Card.Group>
          {props.quizPosts.map((quizPost: any) => {
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
                    <div className="quizMetadataText">
                      <span>
                        <QuizUserThumbnailText
                          id="quizCardUserThumbnailImage"
                          quiz={quizPost}
                        />
                      </span>
                      <span className="quizLikesText">
                        <Icon name="heart" /> {quizPost.numberOfLikes}
                      </span>
                    </div>
                  </Card>
                </Link>
              </div>
            );
          })}
        </Card.Group>
        <MultilineBreak lines={3} />
      </div>
    </div>
  );
};
