import { NULL_TEXT } from '../general';
import { request } from '../request';

// Null question constant
export const NULL_QUESTION = {
  id: NULL_TEXT,
  question: NULL_TEXT,
  answer: NULL_TEXT,
  quizId: NULL_TEXT,
};

/**
 * CREATE / UPDATE Method
 * Adds or modifies a question for a quiz
 */
export const addModifyQuestionRequest = (
  navigate: any,
  questionText: string,
  answerText: string,
  quizId: string | undefined,
  id: string | undefined,
  method: string,
  requestUrl: string
): void => {
  // Set up question object
  const question = {
    id,
    question: questionText,
    answer: answerText,
    numberOfMilliseconds: 10000,
    quizId,
  };

  // Set up request parameters
  const options = {
    method: method,
    headers: {
      'Content-Type': 'application/json',
    },
    data: JSON.stringify(question),
  };

  // Run the request
  request(requestUrl, options)
    .then(() => {
      // Navigate to the quiz questions page if successful
      navigate(`/quizzes/${quizId}/questions`);
      window.location.reload();
    })
    .catch((exception: any) => {
      // Display the error if failed
      alert('ERROR: ' + exception.message);
    });
};

/**
 * READ Method
 * Loads questions from a quiz
 */
export const loadQuizQuestionsRequest = (
  quizId: string | undefined,
  setQuestions: any
) => {
  request(`/quizzes/${quizId}/questions`).then((data) => {
    setQuestions(data);
  });
};

/**
 * READ Method
 * Loads quiz questions for a quiz
 */
export const loadQuizQuestionsForQuizRequest = (
  quizId: string | undefined,
  setQuestions: (questions: any) => void,
  setQuestionIndex: (index: number) => void,
  setNumberOfCorrectAnswers: (amount: number) => void,
  setQuizIsFinished: (quizIsFinished: boolean) => void
): void => {
  request(`/quizzes/${quizId}/questions`).then((questions: any) => {
    setQuestions(questions);
    setQuestionIndex(0);
    setNumberOfCorrectAnswers(0);
    if (questions.length === 0) {
      setQuizIsFinished(true);
    }
  });
};

/**
 * READ Method
 * Loads question from a quiz
 */
export const loadQuizQuestionRequest = (
  quizId: string | undefined,
  id: string | undefined,
  setQuestion: any,
  setAnswer: any
) => {
  request(`/quizzes/${quizId}/questions/${id}`).then((questionItem) => {
    setQuestion(questionItem.question);
    setAnswer(questionItem.answer);
  });
};

/**
 * DELETE Method
 * Request to delete a question
 */
export const deleteQuestionRequest = (
  navigate: any,
  quizId: string,
  id: string
): void => {
  // Run the request
  request(`/quizzes/${quizId}/questions/${id}`, {
    method: 'DELETE',
  }).then(() => {
    // Navigate to quiz questions page after deletion
    navigate(`/quizzes/${quizId}/questions`);
    window.location.reload();
  });
};
