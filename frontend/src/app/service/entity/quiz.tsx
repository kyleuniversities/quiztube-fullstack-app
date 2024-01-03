import { ArrayHelper } from '../../../common/helper/ArrayHelper';
import { PromiseHelper } from '../../../common/helper/js/PromiseHelper';
import { handleException } from '../../util/exception';
import { isPassableId } from '../auth';
import { NULL_TEXT } from '../general';
import { request } from '../request';

// Null quiz constant
export const NULL_QUIZ = {
  id: NULL_TEXT,
  title: NULL_TEXT,
  description: NULL_TEXT,
  picture: NULL_TEXT,
  userId: NULL_TEXT,
  subjectId: NULL_TEXT,
  authorUsername: NULL_TEXT,
  subject: NULL_TEXT,
  numberOfQuestions: 0,
  numberOfLikes: 0,
};

// Quiz request limit constant
export const DEFAULT_QUIZ_LIMIT_VALUE = 5;

// Sentinel value for no limit
export const NO_LIMIT_VALUE = 1000000000;

/**
 * CREATE / UPDATE Method
 * Adding or editing a given quiz
 */
export const addModifyQuizRequest = async (
  navigate: any,
  titleText: string,
  descriptionText: string,
  subjectId: string,
  userId: string,
  id: string | undefined,
  subjectOptions: any,
  method: string,
  requestUrl: string
): Promise<void> => {
  // Stop action if the user id is not passable
  if (!isPassableId(userId)) {
    alert('Must be logged in to perform the operation.');
    return PromiseHelper.newConservativeVoidPromise();
  }

  // Stop action if subject id is empty
  if (subjectId.length < 10) {
    alert('Subject must not be empty.');
    return PromiseHelper.newConservativeVoidPromise();
  }

  // Set up request object
  const quiz = {
    id,
    title: titleText,
    description: descriptionText,
    picture: collectPictureText(subjectId, subjectOptions),
    thumbnail: collectThumbnailText(subjectId, subjectOptions),
    subjectId,
    userId,
  };

  // Set up request paramters
  const options = {
    method: method,
    headers: {
      'Content-Type': 'application/json',
    },
    data: JSON.stringify(quiz),
  };

  // Run request
  return request(requestUrl, options)
    .then((data: any) => {
      // Navigate to the newly added or modified quiz if successful
      navigate(`/quizzes/${data.id}`);
      window.location.reload();
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads quizzes
 */
export const loadQuizzesRequest = async (
  limit: number,
  setQuizzes: any,
  setIsLoaded: any
): Promise<void> => {
  // Run the request
  return request(`/quizzes/posts?limit=${limit}`)
    .then((res: any) => {
      setQuizzes(res);
      setIsLoaded(true);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads quizzes from a user
 */
export const loadQuizzesFromUserRequest = async (
  userId: string | undefined,
  setQuizzes: any,
  setIsLoaded: any
): Promise<void> => {
  return request(`/users/${userId}/quizzes`)
    .then((res: any) => {
      setQuizzes(res);
      setIsLoaded(true);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads quizzes from a subject
 */
export const loadQuizzesFromSubjectRequest = async (
  subjectId: string | undefined,
  limit: number,
  setQuizzes: any,
  setIsLoaded: any
): Promise<void> => {
  return request(`/quizzes/posts/${subjectId}?limit=${limit}`)
    .then((res: any) => {
      setQuizzes(res);
      setIsLoaded(true);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads quizzes from a title query
 */
export const loadQuizzesFromTitleQueryRequest = async (
  title: string | undefined,
  setQuizzes: any,
  setIsLoaded: any
): Promise<void> => {
  return request(`/quizzes/posts?title=${title}`)
    .then((res: any) => {
      setQuizzes(res);
      setIsLoaded(true);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads a quiz
 */
export const loadQuizAsWholeRequest = async (
  id: string | undefined,
  setQuiz: any
): Promise<void> => {
  return request(`/quizzes/${id}`)
    .then((res: any) => {
      setQuiz(res);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads a quiz
 */
export const loadQuizAsPartsRequest = async (
  id: string | undefined,
  subjects: any,
  setTitle: any,
  setDescription: any,
  setSubjectText: any,
  setSubjectId: any
): Promise<void> => {
  return request(`/quizzes/${id}`)
    .then((res: any) => {
      setTitle(res.title);
      setDescription(res.description);
      setSubjectText(res.subject);
      setSubjectId(res.subjectId);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * DELETE Method
 * Request to delete a quiz
 */
export const deleteQuizRequest = async (
  navigate: any,
  userId: string,
  id: string | undefined
): Promise<void> => {
  // Run request
  return request(`/quizzes/${id}`, { method: 'DELETE' })
    .then((res) => {
      // Navigate to user quizzes page
      navigate(`/users/${userId}/quizzes`);
      window.location.reload();
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

// Gets the default picture text for the quiz
const collectPictureText = (subjectId: string, subjectOptions: any) => {
  return collectImageText(subjectId, subjectOptions, '');
};

// Gets the default thumbnail text for the quiz
const collectThumbnailText = (subjectId: string, subjectOptions: any) => {
  return collectImageText(subjectId, subjectOptions, '_T');
};

// Gets the default image text for the quiz
const collectImageText = (
  subjectId: string,
  subjectOptions: any[],
  extenderText: string
): string => {
  const index = ArrayHelper.indexOf(
    subjectOptions,
    (subject: any) => subject.id === subjectId
  );
  const subject = subjectOptions[index];
  const subjectSnakeCaseText = subject.text.toLowerCase().replace(/ /g, '-');
  return `static/quiz/quiz-picture-${subjectSnakeCaseText}${extenderText}.png`;
};
