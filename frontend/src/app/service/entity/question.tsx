import { PromiseHelper } from '../../../common/helper/js/PromiseHelper';
import { handleException } from '../../util/exception';
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
export const addModifyQuestionRequest = async (
  navigate: any,
  questionText: string,
  answerText: string,
  quizId: string | undefined,
  id: string | undefined,
  method: string,
  requestUrl: string
): Promise<void> => {
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
  return request(requestUrl, options)
    .then(() => {
      // Navigate to the quiz questions page if successful
      navigate(`/quizzes/${quizId}/questions`);
      window.location.reload();
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads questions from a quiz
 */
export const loadQuizQuestionsRequest = async (
  quizId: string | undefined,
  setQuestions: any
): Promise<void> => {
  return request(`/quizzes/${quizId}/questions`)
    .then((data) => {
      setQuestions(data);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads quiz questions for a quiz
 */
export const loadQuizQuestionsForQuizRequest = async (
  quizId: string | undefined,
  setQuestions: (questions: any) => void,
  setQuestionIndex: (index: number) => void,
  setNumberOfCorrectAnswers: (amount: number) => void,
  setQuizIsFinished: (quizIsFinished: boolean) => void
): Promise<void> => {
  return request(`/quizzes/${quizId}/questions`)
    .then((questions: any) => {
      setQuestions(questions);
      setQuestionIndex(0);
      setNumberOfCorrectAnswers(0);
      if (questions.length === 0) {
        setQuizIsFinished(true);
      }
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
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
): Promise<void> => {
  return request(`/quizzes/${quizId}/questions/${id}`)
    .then((questionItem) => {
      setQuestion(questionItem.question);
      setAnswer(questionItem.answer);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * DELETE Method
 * Request to delete a question
 */
export const deleteQuestionRequest = async (
  navigate: any,
  quizId: string,
  id: string
): Promise<void> => {
  // Run the request
  return request(`/quizzes/${quizId}/questions/${id}`, {
    method: 'DELETE',
  })
    .then(() => {
      // Navigate to quiz questions page after deletion
      navigate(`/quizzes/${quizId}/questions`);
      window.location.reload();
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};
