import { Card, Icon } from 'semantic-ui-react';
import { useColorize } from '../../context/AppContextManager';
import { Link } from 'react-router-dom';
import { MultilineBreak } from '../../MultilineBreak';
import { QuizUserThumbnailText } from './QuizUserThumbnailText';
import { ConditionalContent } from '../../ConditionalContent';

/**
 * Sub Container to View Quizzes
 */
export const ViewQuizzesContainer = (props: {
  title: string;
  isLoaded: boolean;
  quizPosts: any;
  absentText: string;
}) => {
  // Set up color data
  const colorize = useColorize();

  // Return component
  return (
    <div className="viewAllQuizzesContainer">
      <h1>
        <div className={colorize('quizGroupTitle')}>{props.title}</div>
      </h1>
      <ConditionalContent condition={props.quizPosts.length > 0}>
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
                        <span className={colorize('quizLikesText')}>
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
      </ConditionalContent>
      <ConditionalContent condition={props.quizPosts.length === 0}>
        <ConditionalContent condition={props.isLoaded}>
          <p>{props.absentText}</p>
        </ConditionalContent>
        <ConditionalContent condition={!props.isLoaded}>
          Loading...
        </ConditionalContent>
      </ConditionalContent>
    </div>
  );
};
