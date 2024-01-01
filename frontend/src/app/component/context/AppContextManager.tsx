import React, { ReactNode, useContext, useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode';

// Constant for context for the user's authorization
export const AppContext = React.createContext(null);

// Null username constant
const NULL_USERNAME = '#null';

// Null user id constant
const NULL_ID = '#null';

// Function for extracting user id from token
const getId = (decodedToken: any): string => {
  try {
    return decodedToken.id;
  } catch (e: any) {
    return NULL_ID;
  }
};

// Type for session user
type SessionUser = {
  username: string | undefined;
  userId: string | undefined;
  roles: any;
};

// Constant for null user
const NULL_SESSION_USER: SessionUser = {
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
  const [sessionUser, setSessionUser] = useState(NULL_SESSION_USER);

  // Set up color mode variable
  const [colorMode, setColorMode] = useState('Light');

  // Sets up user from token
  const setUserFromSession = () => {
    const token = localStorage.getItem('access_token');
    if (token && token !== 'undefined') {
      const decodedToken: any = jwtDecode(token);
      setSessionUser({
        username: decodedToken.sub,
        userId: getId(decodedToken),
        roles: null,
      });
    }
  };

  // Set user session data
  const setUserSessionData = (loginResponse: any): void => {
    const jwtToken = loginResponse.token;
    if (!jwtToken || jwtToken === 'undefined') {
      return;
    }
    localStorage.setItem('access_token', jwtToken);
    const decodedToken: any = jwtDecode(jwtToken);
    setSessionUser({
      username: decodedToken.sub,
      userId: getId(decodedToken),
      roles: null,
    });
  };

  // Remove user session data
  const removeUserSessionData = (): void => {
    localStorage.removeItem('access_token');
    setSessionUser(NULL_SESSION_USER);
  };

  // Checks if authenticated
  const isUserAuthenticated = (): boolean => {
    const token = localStorage.getItem('access_token');
    if (!token) {
      return false;
    }
    const expiration = jwtDecode(token).exp;
    if (expiration && Date.now() > expiration * 1000) {
      removeUserSessionData();
      return false;
    }
    if (!expiration) {
      return false;
    }
    return true;
  };

  // Set color mode from session
  const setColorModeFromSession = () => {
    const mode = localStorage.getItem('color_mode');
    if (mode && mode !== 'undefined') {
      setColorMode(mode);
    }
  };

  // Set color session data
  const setColorModeSessionData = (mode: string): void => {
    localStorage.setItem('color_mode', mode);
    setColorMode(mode);
  };

  // Gets user upon render
  useEffect(() => setUserFromSession(), []);

  // Gets color mode upon render
  useEffect(() => setColorModeFromSession(), []);

  // Creates a app context object
  const appContext: any = {
    user: sessionUser,
    colorMode,
    setUserSessionData,
    setColorModeSessionData,
    removeUserSessionData,
    isUserAuthenticated,
  };

  // Returns the components with the session as context
  return (
    <AppContext.Provider value={appContext}>
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
      colorContext.setColorModeSessionData('');
      return;
    }
    colorContext.setColorModeSessionData('Light');
  };
  return colorize;
};
