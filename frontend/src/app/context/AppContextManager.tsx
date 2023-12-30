import React, { ReactNode, useContext, useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import { UserCredentials, loginRequest } from '../auth/AuthorizationInterface';

/**
 * Constant for context for the user's authorization
 */
export const AppContext = React.createContext(null);

// Export null username constant
export const NULL_USERNAME = '#signedOut';

// Export null username id
export const NULL_ID = 'null';

// Function for extracting id
const getId = (decodedToken: any): string => {
  try {
    return decodedToken.id;
  } catch (e: any) {
    return NULL_ID;
  }
};

type AppUser = {
  username: string | undefined;
  userId: string | undefined;
  roles: any;
};

/**
 * Constant for null user
 */
const NULL_USER: AppUser = {
  username: NULL_USERNAME,
  userId: NULL_ID,
  roles: null,
};

/**
 * Wrapper for Managing User App around components
 */
export const AppContextManager = (props: {
  children: ReactNode;
}): JSX.Element => {
  // Set up user variable
  const [user, setUser] = useState(NULL_USER);

  // Set up color mode variable
  const [colorMode, setColorMode] = useState('Light');

  // Sets up user from token
  const setUserFromToken = () => {
    const token = localStorage.getItem('access_token');
    if (token && token !== 'undefined') {
      const decodedToken: any = jwtDecode(token);
      setUser({
        username: decodedToken.sub,
        userId: getId(decodedToken),
        roles: null,
      });
    }
  };

  // Login action
  const logIn = async (credentials: UserCredentials): Promise<any> => {
    return new Promise((resolve, reject) => {
      loginRequest(credentials)
        .then((res) => {
          alert('HEADERS: ' + JSON.stringify(res));
          const jwtToken = res.token;
          if (!jwtToken || jwtToken === 'undefined') {
            resolve(res);
            return;
          }
          localStorage.setItem('access_token', jwtToken);
          const decodedToken: any = jwtDecode(jwtToken);
          alert('DECODED: ' + JSON.stringify(decodedToken));
          setUser({
            username: decodedToken.sub,
            userId: getId(decodedToken),
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
    colorMode,
    logIn,
    logOut,
    isUserAuthenticated,
    setUserFromToken,
    setColorMode,
  };

  // Returns the components with the session as context
  return (
    <AppContext.Provider value={userContext}>
      {props.children}
    </AppContext.Provider>
  );
};

// Export useAppContext Hook
export const useAppContext = () => useContext(AppContext);

// Export useUsername Hook
export const useUsername = () => {
  const userContext: any = useAppContext();
  return userContext && userContext.user && userContext.user.username
    ? userContext.user.username
    : NULL_USERNAME;
};

// Export useUserId Hook
export const useUserId = () => {
  const userContext: any = useAppContext();
  return userContext && userContext.user && userContext.user.userId
    ? userContext.user.userId
    : NULL_ID;
};

// Export useColorize Hook
export const useColorize = () => {
  const colorContext: any = useAppContext();
  const colorize = (className: string) => className + colorContext.colorMode;

  // Get color mode
  colorize.colorMode = () => colorContext.colorMode;

  // Set up toggle function
  colorize.toggle = () => {
    if (colorContext.colorMode === 'Light') {
      colorContext.setColorMode('');
      return;
    }
    colorContext.setColorMode('Light');
  };
  return colorize;
};
