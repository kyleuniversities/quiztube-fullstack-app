import React, { ReactNode, useContext, useEffect, useState } from 'react';
import { request } from '../../common/util/request';
import { jwtDecode } from 'jwt-decode';
import { UserCredentials, loginRequest } from './AuthorizationInterface';
import exp from 'constants';

/**
 * Constant for context for the user's authorization
 */
export const AuthorizationContext = React.createContext(null);

type AuthorizationUser = {
  username: string | undefined;
  roles: any;
};

/**
 * Constant for null user
 */
const NULL_USER: AuthorizationUser = { username: '#signedOut', roles: null };

/**
 * Wrapper for Managing User Authorization around components
 */
export const AuthorizationContextManager = (props: {
  children: ReactNode;
}): JSX.Element => {
  // Set up user variable
  const [user, setUser] = useState(NULL_USER);

  // Sets up user from token
  const setUserFromToken = () => {
    const token = localStorage.getItem('access_token');
    if (token) {
      const decodedToken = jwtDecode(token);
      setUser({
        username: decodedToken.sub,
        roles: null,
      });
    }
  };

  // Login action
  const logIn = async (credentials: UserCredentials): Promise<any> => {
    return new Promise((resolve, reject) => {
      loginRequest(credentials)
        .then((res) => {
          const jwtToken = res.headers['authorization'];
          localStorage.setItem('access_token', jwtToken);
          const decodedToken = jwtDecode(jwtToken);
          setUser({
            username: decodedToken.sub,
            roles: null,
          });
          resolve(res);
        })
        .catch((err) => reject(err));
    });
  };

  // Logout action
  const logOut = (): void => {
    localStorage.removeItem('access_token');
    setUser(NULL_USER);
  };

  // Checks if authenticated
  const isUserAuthenticated = (): boolean => {
    const token = localStorage.getItem('access_token');
    if (!token) {
      return false;
    }
    const expiration = jwtDecode(token).exp;
    if (expiration && Date.now() > expiration * 1000) {
      logOut();
      return false;
    }
    if (!expiration) {
      return false;
    }
    return true;
  };

  // Gets user upon render
  useEffect(() => setUserFromToken(), []);

  // Creates a user context object
  const userContext: any = {
    user,
    logIn,
    logOut,
    isUserAuthenticated,
    setUserFromToken,
  };

  // Returns the components with the session as context
  return (
    <AuthorizationContext.Provider value={userContext}>
      {props.children}
    </AuthorizationContext.Provider>
  );
};

// Export useAuthorization Hook
export const useAuthorization = () => useContext(AuthorizationContext);
