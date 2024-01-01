import { PromiseHelper } from '../../../common/helper/js/PromiseHelper';
import { handleException } from '../../util/exception';
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
export const likeQuizRequest = async (
  quizId: string | undefined,
  userId: string
): Promise<void> => {
  // Stop action if the user id is not passable
  if (!isPassableId(userId)) {
    alert('ERROR: Must be logged in to perform the operation.');
    return PromiseHelper.newConservativeVoidPromise();
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
  return request(`/quizzes/${quizId}/likes`, options)
    .then(() => {
      window.location.reload();
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Request to load a like
 */
export const loadLikeRequest = async (
  quizId: string | undefined,
  userId: string | undefined,
  setLike: any
): Promise<void> => {
  // If the user id is not passable, load the Null Like
  if (!isPassableId(userId)) {
    setLike(NULL_LIKE);
    return PromiseHelper.newConservativeVoidPromise();
  }

  // Run request
  return request(`/quizzes/${quizId}/likes/i-liked-this/${userId}`)
    .then((res: any) => {
      // Load the Null Like if the user did not like the post
      if (!res || res === null) {
        setLike(NULL_LIKE);
        return PromiseHelper.newConservativeVoidPromise();
      }
      // Load the Like if the user liked the post
      setLike(res);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * DELETE Method
 * Request to unlike a quiz
 */
export const unlikeQuizRequest = async (
  quizId: string | undefined,
  like: any
): Promise<void> => {
  // Set up method parameters
  const options = {
    method: 'DELETE',
  };

  // Run request
  return request(`/quizzes/${quizId}/likes/${like.id}`, options)
    .then(() => {
      window.location.reload();
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};
