import { PromiseHelper } from '../../../common/helper/js/PromiseHelper';
import { handleException } from '../../util/exception';
import { logoutAction } from '../auth';
import { collectDefaultThumbnailKeyFromUsername } from '../file';
import { NULL_TEXT } from '../general';
import { request } from '../request';

// Null user constant
export const NULL_USER = {
  id: NULL_TEXT,
  username: NULL_TEXT,
  email: NULL_TEXT,
  picture: NULL_TEXT,
  thumbnail: collectDefaultThumbnailKeyFromUsername(NULL_TEXT),
};

/**
 * CREATE Method
 * Request to register a user
 */
export const addUserRequest = async (
  navigate: any,
  username: string,
  email: string,
  password: string
): Promise<void> => {
  // Set up method
  const method = 'POST';

  // Set up post body
  const user = {
    username,
    email,
    password,
    picture: collectPictureText(username),
    thumbnail: collectThumbnailText(username),
  };

  // Set up request parameters
  const options = {
    method: method,
    headers: {
      'Content-Type': 'application/json',
    },
    data: JSON.stringify(user),
  };

  // Set up request url
  const requestUrl = `/users`;

  // Run the request
  return request(requestUrl, options)
    .then(() => {
      // Navigate to the homepage if successful
      alert(`The user '${username}' was successfully registered!`);
      navigate('/');
      window.location.reload();
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads a user
 */
export const loadUserRequest = (id: string | undefined, setUser: any): void => {
  request(`/users/${id}`)
    .then((res: any) => {
      setUser(res);
    })
    .catch(handleException);
};

/**
 * DELETE Method
 * Request to delete a user
 */
export const deleteUserRequest = async (
  navigate: any,
  userContext: any,
  id: string | undefined
): Promise<void> => {
  // Run request
  return request(`/users/${id}`, { method: 'DELETE' })
    .then(() => {
      // Navigate back
      logoutAction(navigate, userContext);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

// Gets the default picture text for the user
const collectPictureText = (username: string) => {
  return collectImageText(username, '');
};

// Gets the default thumbnail text for the quiz
const collectThumbnailText = (username: string) => {
  return collectImageText(username, '_T');
};

// Gets the default image text for the quiz
const collectImageText = (username: string, extenderText: string): string => {
  const firstLetter = username.toLowerCase().charAt(0);
  return `static/user/user-picture-${firstLetter}${extenderText}.png`;
};
