import { Button, Form, Header, Transition } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { useNavigate, useParams } from 'react-router';
import { ConditionalContent } from '../../ConditionalContent';
import { CentralContainer } from '../../Component';
import { loadQuizQuestionsForQuizRequest } from '../../../service/entity/question';

/**
 * Page for taking a Quiz
 */
export const TakeQuizPage = (): JSX.Element => {
  // Url parameters for the quiz id
  const { quizId } = useParams();

  // Set up quiz data
  const [questions, setQuestions] = useState([]);
  const [questionIndex, setQuestionIndex] = useState(-1);
  const [numberOfCorrectAnswers, setNumberOfCorrectAnswers] = useState(0);
  const [enteredAnswer, setEnteredAnswer] = useState('');
  const [answerIsSubmitted, setAnswerIsSubmitted] = useState(false);
  const [quizIsFinished, setQuizIsFinished] = useState(false);
  const isAcceptingBlankAnswers = true;

  // Loads the quiz on render
  useEffect(() => {
    loadQuizQuestionsForQuizRequest(
      quizId,
      setQuestions,
      setQuestionIndex,
      setNumberOfCorrectAnswers,
      setQuizIsFinished
    );
  }, [quizId]);

  // Load current question
  const question: any = questionIndex > -1 ? questions[questionIndex] : null;

  // Function for submitting an answer
  const submitAnswer = () => {
    // If the answer is blank, prevent the user from submitting
    if (!isAcceptingBlankAnswers && enteredAnswer === '') {
      alert('Must enter an answer for the quiz question.');
      return;
    }

    // If an answer is entered, toggle the submitted flag
    setAnswerIsSubmitted(true);
  };

  // Function for advancing to the next question
  const next = (answerIsCorrect: boolean): void => {
    nextQuestion(
      questions,
      questionIndex,
      numberOfCorrectAnswers,
      setQuestionIndex,
      setNumberOfCorrectAnswers,
      setEnteredAnswer,
      setAnswerIsSubmitted,
      setQuizIsFinished,
      answerIsCorrect
    );
  };

  // Return Take Quiz Page Content
  return (
    <SitePage>
      <CentralContainer>
        <ConditionalContent condition={quizIsFinished}>
          <FinishedQuizContent
            quizId={quizId}
            numberOfCorrectAnswers={numberOfCorrectAnswers}
            questions={questions}
          />
        </ConditionalContent>
        <ConditionalContent condition={!quizIsFinished}>
          <UnfinishedQuizContent
            question={question}
            answerIsSubmitted={answerIsSubmitted}
            enteredAnswer={enteredAnswer}
            setEnteredAnswer={setEnteredAnswer}
            submitAnswer={submitAnswer}
            next={next}
          />
        </ConditionalContent>
      </CentralContainer>
    </SitePage>
  );
};

/**
 * Content for user taking a quiz
 */
const UnfinishedQuizContent = (props: {
  question: any;
  answerIsSubmitted: boolean;
  enteredAnswer: string;
  setEnteredAnswer: (enteredAnswer: string) => void;
  submitAnswer: () => void;
  next: (answerIsCorrect: boolean) => void;
}): JSX.Element => {
  return (
    <>
      <ConditionalContent condition={props.question === null}>
        <p>Loading...</p>
      </ConditionalContent>
      <ConditionalContent condition={props.question !== null}>
        <Header as="h3">
          <b>Question: </b>
          {props.question ? props.question.question : 'Loading...'}
        </Header>
        <Transition
          visible={props.answerIsSubmitted}
          animation="slide left"
          duration={500}
        >
          <Header as="h3">
            <b>Answer: </b>
            {props.question ? props.question.answer : 'Loading...'}
          </Header>
        </Transition>
        <Form.Input
          value={props.enteredAnswer}
          disabled={props.answerIsSubmitted}
          onChange={(e: any) => props.setEnteredAnswer(e.target.value)}
        />
        <MultilineBreak lines={1} />
        <ConditionalContent condition={props.answerIsSubmitted}>
          <Button content={'Correct '} onClick={() => props.next(true)} />
          <Button content={'Incorrect '} onClick={() => props.next(false)} />
        </ConditionalContent>
        <ConditionalContent condition={!props.answerIsSubmitted}>
          <Button content={'Check '} onClick={() => props.submitAnswer()} />
        </ConditionalContent>
      </ConditionalContent>
    </>
  );
};

/**
 * Content for after the user finished a quiz
 */
const FinishedQuizContent = (props: {
  quizId: string | undefined;
  numberOfCorrectAnswers: number;
  questions: any[];
}): JSX.Element => {
  // Set up navigation
  const navigate = useNavigate();

  // Return component
  return (
    <>
      <h3>
        Quiz is Done! You scored {props.numberOfCorrectAnswers} out of{' '}
        {props.questions.length}.
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
          navigate(`/quizzes/${props.quizId}`);
          window.location.reload();
        }}
      />
    </>
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
  // Increment the number of correct answer if the answer is correct
  if (answerIsCorrect) {
    setNumberOfCorrectAnswers(numberOfCorrectAnswers + 1);
  }

  // Reset the entered answer
  setEnteredAnswer('');
  setAnswerIsSubmitted(false);

  // Finish the quiz if the last question is reached
  if (questionIndex === questions.length - 1) {
    setQuizIsFinished(true);
    return;
  }

  // Otherwise, increment the question index
  setQuestionIndex(questionIndex + 1);
};
