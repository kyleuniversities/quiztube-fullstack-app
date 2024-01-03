import { useParams } from 'react-router';
import { ReactNode, useEffect, useState } from 'react';
import { Button, Container, Image, List } from 'semantic-ui-react';
import { useNavigate } from 'react-router-dom';
import { SitePage } from '../../SitePage';
import { Link } from 'react-router-dom';
import { ConditionalContent } from '../../ConditionalContent';
import { useColorize, useUsername } from '../../context/AppContextManager';
import {
  deleteQuestionRequest,
  loadQuizQuestionsRequest,
} from '../../../service/entity/question';
import {
  NULL_QUIZ,
  loadQuizAsWholeRequest,
} from '../../../service/entity/quiz';
import './index.css';
import { handleAbsentResourceException } from '../../../util/exception';
import { LoadedResourceContainer } from '../LoadedResourceContainer';

export const ViewQuizQuestionsPage = (): JSX.Element => {
  // Set up parameter data
  const { id } = useParams();

  // Set up user data
  const username = useUsername();

  // Set up form
  const [quiz, setQuiz] = useState(NULL_QUIZ);
  const [isAbsent, setIsAbsent] = useState(false);

  // Set up data items
  const [questions, setQuestions] = useState([]);

  // Set up quiz not found handling method
  const handleException = (exception: any) => {
    handleAbsentResourceException(exception, setIsAbsent);
  };

  // Load data items on render
  useEffect(() => {
    loadQuizQuestionsRequest(id, setQuestions).catch(handleException);
    loadQuizAsWholeRequest(id, setQuiz).catch(handleException);
  }, [id]);

  // Returns the page
  return (
    <SitePage>
      <LoadedResourceContainer entityName="Quiz" id={id} isAbsent={isAbsent}>
        <h2>{quiz.title}</h2>
        <QuestionsListColumnContainer>
          <Link to={`/quizzes/${id}`}>
            <Button
              icon="angle left"
              color="purple"
              content="Back to Quiz View"
            />
          </Link>
          <ConditionalContent condition={questions.length > 0}>
            <Link to={`/quizzes/${id}/questions/take`}>
              <Button icon="bolt" color="brown" content="Take Quiz" />
            </Link>
          </ConditionalContent>
          <ConditionalContent condition={quiz.authorUsername === username}>
            <Link to={`/quizzes/${id}/questions/add`}>
              <Button icon="plus" color="blue" content="New Question" />
            </Link>
          </ConditionalContent>
          <QuestionsListColumn quiz={quiz} questions={questions} />
        </QuestionsListColumnContainer>
      </LoadedResourceContainer>
    </SitePage>
  );
};

/**
 * Grid for displaying data items along with filter menu
 */
const QuestionsListColumnContainer = (props: { children: ReactNode }) => {
  return (
    <Container className="dataItemsListColumnContainer" fluid>
      {props.children}
    </Container>
  );
};

/**
 * Container for viewing data items
 */
const QuestionsListColumn = (props: { quiz: any; questions: any[] }) => {
  // Set up question image data
  const colorize = useColorize();
  const questionImage = require('../../../resource/question-image.png');
  const questionImageLight = require('../../../resource/question-image-light.png');
  const selectedQuestionImage = colorize.isDefaultMode()
    ? questionImage
    : questionImageLight;

  // Return component
  return (
    <List size="huge" relaxed className="dataItemsListColumn">
      {props.questions.length === 0 ? (
        <p>This quiz has no questions.</p>
      ) : (
        props.questions.map((item) => {
          return (
            <QuestionItem
              quiz={props.quiz}
              id={item.id}
              image={selectedQuestionImage}
              title={item.question}
              description={item.answer}
            />
          );
        })
      )}
    </List>
  );
};

/**
 * Card for displaying a single data item
 */
const QuestionItem = (props: {
  quiz: any;
  id: string;
  image: string;
  title: string;
  description: string;
}) => {
  // Set up item answer data
  const [isShowingDescription, setIsShowingDescription] = useState(false);

  // Set up username data
  const username = useUsername();

  // Set up color data
  const colorize = useColorize();

  // Set up navigation
  const navigate = useNavigate();

  // Return component
  return (
    <List.Item className={colorize('dataItem')}>
      <div className={colorize('dataItemSegment')}>
        <List.Content>
          <Container fluid>
            <Image className="dataItemImage" src={`${props.image}`} />
            <div>
              <div className="dataItemText">
                <List.Header>
                  <span className={colorize('dataItemTextTitle')}>
                    {props.title}
                  </span>
                </List.Header>
                <List.Description>
                  <span className={colorize('dataItemTextDescription')}>
                    {isShowingDescription
                      ? props.description
                      : '(Answer is Hidden)'}
                  </span>
                </List.Description>
              </div>
              <Container fluid className="dataItemButtonContainer">
                <Button
                  inline
                  icon="eye"
                  color="orange"
                  content={(isShowingDescription ? 'Hide' : 'Show') + ' Answer'}
                  onClick={() => setIsShowingDescription(!isShowingDescription)}
                />
                <ConditionalContent
                  condition={props.quiz.authorUsername === username}
                >
                  <Link
                    to={`/quizzes/${props.quiz.id}/questions/edit/${props.id}`}
                  >
                    <Button
                      inline
                      icon="pencil alternate"
                      color="green"
                      content="Edit"
                    />
                  </Link>
                  <Button
                    inline
                    icon="trash"
                    color="red"
                    content="Delete"
                    onClick={() =>
                      deleteQuestionRequest(navigate, props.quiz.id, props.id)
                    }
                  />
                </ConditionalContent>
              </Container>
            </div>
          </Container>
        </List.Content>
      </div>
    </List.Item>
  );
};
