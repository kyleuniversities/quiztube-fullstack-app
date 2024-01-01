import { PromiseHelper } from '../../../common/helper/js/PromiseHelper';
import { NULL_TEXT } from '../general';
import { request } from '../request';

// Null user constant
export const NULL_USER = {
  id: NULL_TEXT,
  username: NULL_TEXT,
  email: NULL_TEXT,
  picture: NULL_TEXT,
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
  return request(requestUrl, options).then(() => {
    // Navigate to the homepage if successful
    alert("The user 'username' was successfully registered!");
    navigate('/');
    window.location.reload();
    return PromiseHelper.newConservativeVoidPromise();
  });
};

/**
 * READ Method
 * Loads a user
 */
export const loadUserRequest = (id: string | undefined, setUser: any): void => {
  request(`/users/${id}`).then((res: any) => {
    setUser(res);
  });
};
