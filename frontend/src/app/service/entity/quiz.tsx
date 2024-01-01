import { indexOf } from '../../../common/util/list';
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
  numberOfLikes: 0,
};

/**
 * CREATE / UPDATE Method
 * Adding or editing a given quiz
 */
export const addModifyQuizRequest = (
  navigate: any,
  titleText: string,
  descriptionText: string,
  subjectId: string,
  userId: string,
  id: string | undefined,
  subjectOptions: any,
  method: string,
  requestUrl: string
): void => {
  // Stop action if the user id is not passable
  if (!isPassableId(userId)) {
    alert('ERROR: Must be logged in to perform the operation.');
    return;
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
  request(requestUrl, options).then((data: any) => {
    // Navigate to the newly added or modified quiz if successful
    navigate(`/quizzes/${data.id}`);
    window.location.reload();
  });
};

/**
 * READ Method
 * Loads quizzes from a user
 */
export const loadQuizzesFromUserRequest = (
  userId: string | undefined,
  setQuizzes: any
): void => {
  request(`/users/${userId}/quizzes`).then((res: any) => {
    setQuizzes(res);
  });
};

/**
 * READ Method
 * Loads quizzes from a subject
 */
export const loadQuizzesFromSubjectRequest = (
  subjectId: string | undefined,
  setQuizzes: any
): void => {
  request(`/quizzes/posts/${subjectId}`).then((res: any) => {
    setQuizzes(res);
  });
};

/**
 * READ Method
 * Loads a quiz
 */
export const loadQuizAsWholeRequest = (
  id: string | undefined,
  setQuiz: any
): void => {
  request(`/quizzes/${id}`).then((res: any) => {
    setQuiz(res);
  });
};

/**
 * READ Method
 * Loads a quiz
 */
export const loadQuizAsPartsRequest = (
  id: string | undefined,
  setTitle: any,
  setDescription: any,
  setSubject: any
): void => {
  request(`/quizzes/${id}`).then((res: any) => {
    setTitle(res.title);
    setDescription(res.description);
    setSubject(res.subject);
  });
};

/**
 * DELETE Method
 * Request to delete a quiz
 */
export const deleteQuizRequest = (
  navigate: any,
  userId: string,
  id: string | undefined
): void => {
  // Run request
  request(`/quizzes/${id}`, { method: 'DELETE' }).then((res) => {
    // Navigate to user quizzes page
    navigate(`/users/${userId}/quizzes`);
    window.location.reload();
  });
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
  subjectOptions: any,
  extenderText: string
): string => {
  const index = indexOf(
    subjectOptions,
    (subject: any) => subject.id === subjectId
  );
  const subject = subjectOptions[index];
  const subjectSnakeCaseText = subject.text.toLowerCase().replace(/ /g, '-');
  return `static/quiz/quiz-picture-${subjectSnakeCaseText}${extenderText}.png`;
};
