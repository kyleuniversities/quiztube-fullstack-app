import { isPassableId } from '../auth';
import { NULL_TEXT } from '../general';
import { request } from '../request';

// Null like constant
export const NULL_LIKE = {
  id: NULL_TEXT,
  userId: NULL_TEXT,
  quizId: NULL_TEXT,
};

/**
 * CREATE Method
 * Request to like a quiz
 */
export const likeQuizRequest = (
  quizId: string | undefined,
  userId: string
): void => {
  // Stop action if the user id is not passable
  if (!isPassableId(userId)) {
    alert('ERROR: Must be logged in to perform the operation.');
    return;
  }

  // Set up like object
  const like = {
    id: '#null',
    userId,
    quizId,
  };

  // Set up request parameters
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: JSON.stringify(like),
  };

  // Run request
  request(`/quizzes/${quizId}/likes`, options).then(() => {
    window.location.reload();
  });
};

/**
 * READ Method
 * Request to load a like
 */
export const loadLikeRequest = (
  quizId: string | undefined,
  userId: string | undefined,
  setLike: any
): void => {
  // If the user id is not passable, load the Null Like
  if (!isPassableId(userId)) {
    setLike(NULL_LIKE);
    return;
  }

  // Run request
  request(`/quizzes/${quizId}/likes/i-liked-this/${userId}`).then(
    (res: any) => {
      // Load the Null Like if the user did not like the post
      if (!res || res === null) {
        setLike(NULL_LIKE);
        return;
      }
      // Load the Like if the user liked the post
      setLike(res);
    }
  );
};

/**
 * DELETE Method
 * Request to unlike a quiz
 */
export const unlikeQuizRequest = (
  quizId: string | undefined,
  like: any
): void => {
  // Set up method parameters
  const options = {
    method: 'DELETE',
  };

  // Run request
  request(`/quizzes/${quizId}/likes/${like.id}`, options).then(() => {
    window.location.reload();
  });
};
