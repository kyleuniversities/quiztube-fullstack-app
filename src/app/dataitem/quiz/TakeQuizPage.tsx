import { Button, Container, Form, Header, Transition } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { Consumer, useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate, useParams } from 'react-router';
import { AddModifyQuizPage } from './AddModifyQuizPage';
import { ConditionalContent } from '../../ConditionalContent';
import { CentralContainer } from '../../Component';
import { Link } from 'react-router-dom';

/**
 * Page for taking a Quiz
 */
export const TakeQuizPage = (): JSX.Element => {
  // Url parameters for the quiz id
  const { quizId } = useParams();
  const [questions, setQuestions] = useState([]);
  const [questionIndex, setQuestionIndex] = useState(-1);
  const [numberOfCorrectAnswers, setNumberOfCorrectAnswers] = useState(0);
  const [enteredAnswer, setEnteredAnswer] = useState('');
  const [answerIsSubmitted, setAnswerIsSubmitted] = useState(false);
  const [quizIsFinished, setQuizIsFinished] = useState(false);
  const navigate = useNavigate();

  // Loads the quiz on render
  useEffect(
    () =>
      loadQuiz(
        quizId,
        setQuestions,
        setQuestionIndex,
        setNumberOfCorrectAnswers
      ),
    [quizId]
  );

  const question: any = questionIndex > -1 ? questions[questionIndex] : null;

  return (
    <SitePage>
      <CentralContainer>
        <ConditionalContent condition={quizIsFinished}>
          <h3>
            Quiz is Done! You scored {numberOfCorrectAnswers} out of{' '}
            {questions.length}.
          </h3>
          <Button
            icon="bolt"
            color="brown"
            content="Retake Quiz"
            onClick={() => {
              window.location.reload();
            }}
          />
          <Button
            icon="delete"
            color="red"
            content="Leave Quiz"
            onClick={() => {
              navigate(`/quizzes/${quizId}`);
              window.location.reload();
            }}
          />
        </ConditionalContent>
        <ConditionalContent condition={!quizIsFinished}>
          <ConditionalContent condition={question === null}>
            <p>Loading...</p>
          </ConditionalContent>
          <ConditionalContent condition={question !== null}>
            <Header as="h3">
              <b>Question: </b>
              {question ? question.question : 'Loading...'}
            </Header>
            <Transition
              visible={answerIsSubmitted}
              animation="slide left"
              duration={500}
            >
              <Header as="h3">
                <b>Answer: </b>
                {question ? question.answer : 'Loading...'}
              </Header>
            </Transition>
            <Form.Input
              value={enteredAnswer}
              disabled={answerIsSubmitted}
              onChange={(e: any) => setEnteredAnswer(e.target.value)}
            />
            <MultilineBreak lines={1} />
            <ConditionalContent condition={answerIsSubmitted}>
              <Button
                content={'Correct '}
                onClick={() =>
                  nextQuestion(
                    questions,
                    questionIndex,
                    numberOfCorrectAnswers,
                    setQuestionIndex,
                    setNumberOfCorrectAnswers,
                    setEnteredAnswer,
                    setAnswerIsSubmitted,
                    setQuizIsFinished,
                    true
                  )
                }
              />
              <Button
                content={'Incorrect '}
                onClick={() =>
                  nextQuestion(
                    questions,
                    questionIndex,
                    numberOfCorrectAnswers,
                    setQuestionIndex,
                    setNumberOfCorrectAnswers,
                    setEnteredAnswer,
                    setAnswerIsSubmitted,
                    setQuizIsFinished,
                    false
                  )
                }
              />
            </ConditionalContent>
            <ConditionalContent condition={!answerIsSubmitted}>
              <Button
                content={'Check '}
                onClick={() => setAnswerIsSubmitted(true)}
              />
            </ConditionalContent>
          </ConditionalContent>
        </ConditionalContent>
      </CentralContainer>
    </SitePage>
  );
};

// Function for advancing to the next question
const nextQuestion = (
  questions: any[],
  questionIndex: number,
  numberOfCorrectAnswers: number,
  setQuestionIndex: (index: number) => void,
  setNumberOfCorrectAnswers: (amount: number) => void,
  setEnteredAnswer: (answer: string) => void,
  setAnswerIsSubmitted: (answerIsSubmitted: boolean) => void,
  setQuizIsFinished: (quizIsFinished: boolean) => void,
  answerIsCorrect: boolean
): void => {
  if (answerIsCorrect) {
    setNumberOfCorrectAnswers(numberOfCorrectAnswers + 1);
  }
  setEnteredAnswer('');
  setAnswerIsSubmitted(false);
  if (questionIndex == questions.length - 1) {
    setQuizIsFinished(true);
    return;
  }
  setQuestionIndex(questionIndex + 1);
};

// Function for loading the quiz data
const loadQuiz = (
  quizId: string | undefined,
  setQuestions: (questions: any) => void,
  setQuestionIndex: (index: number) => void,
  setNumberOfCorrectAnswers: (amount: number) => void
): void => {
  request(`/questions/by/${quizId}`).then((questions: any) => {
    setQuestions(questions);
    setQuestionIndex(0);
    setNumberOfCorrectAnswers(0);
  });
};
