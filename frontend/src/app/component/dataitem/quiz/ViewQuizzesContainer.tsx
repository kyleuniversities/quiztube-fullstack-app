import { Card, Container, Icon, Loader } from "semantic-ui-react";
import { useColorize } from "../../context/AppContextManager";
import { Link } from "react-router-dom";
import { MultilineBreak } from "../../MultilineBreak";
import { QuizUserThumbnailText } from "./QuizUserThumbnailText";
import { ConditionalContent } from "../../ConditionalContent";
import { useEffect, useState } from "react";

/**
 * Type for quiz attribute updater
 */
export type Updater = (item: any) => void;

/**
 * Type for quiz loading function
 */
export type LoadQuizzesFunction = (
  setQuizPosts: (quizPosts: any) => void,
  setIsLoading: (isLoaded: any) => void,
) => void;

/**
 * Sub Container to View Quizzes
 */
export const ViewQuizzesContainer = (props: {
  title: string;
  loadQuizzesFunction: LoadQuizzesFunction;
  loadQuizzesFunctionDependencyArray: any[];
  absentText: string;
}) => {
  // Set up quiz data
  const [quizPosts, setQuizPosts] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);

  // Set up use effect data
  const loadQuizzesFunction = props.loadQuizzesFunction;
  const loadQuizzesFunctionDependencyArray =
    props.loadQuizzesFunctionDependencyArray;

  // Load quizzes
  useEffect(() => {
    loadQuizzesFunction(setQuizPosts, setIsLoaded);
  }, [loadQuizzesFunction, loadQuizzesFunctionDependencyArray]);

  // Set up color data
  const colorize = useColorize();

  // Return component
  return (
    <div className="viewAllQuizzesContainer">
      <ViewQuizzesHeader colorize={colorize} title={props.title} />
      <ConditionalContent condition={quizPosts.length > 0}>
        <QuizCardGroupContainer colorize={colorize} quizPosts={quizPosts} />
      </ConditionalContent>
      <ConditionalContent condition={quizPosts.length === 0}>
        <EmptyQuizListContainer
          colorize={colorize}
          absentText={props.absentText}
          isLoaded={isLoaded}
        />
      </ConditionalContent>
    </div>
  );
};

/**
 * Header for viewing Quizzes
 */
const ViewQuizzesHeader = (props: { colorize: any; title: string }) => {
  return (
    <h1>
      <div className={props.colorize("quizGroupTitle")}>{props.title}</div>
    </h1>
  );
};

/**
 * Container for a group Quiz Cards
 */
const QuizCardGroupContainer = (props: { colorize: any; quizPosts: any }) => {
  return (
    <div className="quizCardGroupContainer">
      <Card.Group>
        {props.quizPosts.map((quizPost: any) => {
          return (
            <QuizCardContainer colorize={props.colorize} quizPost={quizPost} />
          );
        })}
      </Card.Group>
      <MultilineBreak lines={3} />
    </div>
  );
};

/**
 * Container for empty Quiz list
 */
const EmptyQuizListContainer = (props: {
  colorize: any;
  absentText: string;
  isLoaded: boolean;
}) => {
  return (
    <>
      <ConditionalContent condition={props.isLoaded}>
        <p>{props.absentText}</p>
      </ConditionalContent>
      <ConditionalContent condition={!props.isLoaded}>
        <Container fluid className="centralLoader">
          <Loader active inline>
            Loading...
          </Loader>
        </Container>
      </ConditionalContent>
    </>
  );
};

/**
 * Container for Quiz Cards
 */
const QuizCardContainer = (props: { colorize: any; quizPost: any }) => {
  return (
    <div className="quizCardWrappingContainer">
      <Link to={`/quizzes/${props.quizPost.id}`}>
        <Card className="quizCard">
          <div className={props.colorize("quizCardContent")}>
            <h3 className="quizPostTitle">{props.quizPost.title}</h3>
            <p className="quizPostDescription">{props.quizPost.description}</p>
          </div>
          <div className="quizMetadataText">
            <span>
              <QuizUserThumbnailText
                id="quizCardUserThumbnailImage"
                quiz={props.quizPost}
              />
            </span>
            <span className={props.colorize("quizLikesText")}>
              <Icon name="heart" /> {props.quizPost.numberOfLikes}
            </span>
          </div>
        </Card>
      </Link>
    </div>
  );
};
