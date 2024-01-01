import { PromiseHelper } from '../../common/helper/js/PromiseHelper';
import {
  alertForInauthenticatedSession,
  alertForUnauthorizedSession,
} from './alert';
import { request } from './request';

// Type for Authentication Credentials
export type AuthenticationCredentials = {
  username: string;
  password: string;
};

/**
 * CREATE Method
 * Function to login to the site
 */
export const loginRequest = async (
  navigate: any,
  userContext: any,
  username: string,
  password: string
): Promise<void> => {
  // Set up credentials
  const credentials = {
    username,
    password,
  };

  // Set up request parameters
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: JSON.stringify(credentials),
  };

  // Run request
  return request('/auth/login', options).then((data: any) => {
    // Set user session data if successful
    userContext.setUserSessionData(data);

    // Navigate to home
    navigate('/');
    return PromiseHelper.newConservativeVoidPromise();
  });
};

/**
 * CREATE Method
 * Redirects an unauthorized user
 */
export const redirectFromUnauthorizedQuizActionRequest = async (
  userContext: any,
  targetQuizId: string | undefined,
  navigate: any
): Promise<void> => {
  // Redirects to the Log In Page if the user is signed out
  if (!userContext.isUserAuthenticated()) {
    alertForInauthenticatedSession();
    navigate('/login');
    return PromiseHelper.newConservativeVoidPromise();
  }

  // If no quiz is targetd, then the user is authorized
  if (!targetQuizId) {
    return PromiseHelper.newConservativeVoidPromise();
  }

  // Get Session User Id
  const sessionUserId = userContext.collectUserId();

  // Run request to check for authorized access
  return request(`/quizzes/${targetQuizId}`).then((targetQuiz: any) => {
    // Redirects to the Home Page if the user is not permitted
    if (sessionUserId !== targetQuiz.userId) {
      alertForUnauthorizedSession();
      navigate('/');
      return PromiseHelper.newConservativeVoidPromise();
    }

    // Do nothing if authorized
    return PromiseHelper.newConservativeVoidPromise();
  });
};

/**
 * Logs the user out
 */
export const logoutAction = (navigate: any, userContext: any): void => {
  // Removes user session data
  userContext.removeUserSessionData();

  // Navigates to the home page
  navigate('/');
  window.location.reload();
};

/**
 * Checks if an id is a non-sentinel value
 */
export const isPassableId = (id: string | undefined): boolean => {
  return id !== null && id !== undefined && id.length > 20;
};
