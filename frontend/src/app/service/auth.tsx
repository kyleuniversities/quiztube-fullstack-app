import { PromiseHelper } from '../../common/helper/js/PromiseHelper';
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

    // Navigate back
    navigate(-1);
    return PromiseHelper.newConservativeVoidPromise();
  });
};

// Logout Action
export const logoutRequest = (navigate: any, userContext: any): void => {
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
