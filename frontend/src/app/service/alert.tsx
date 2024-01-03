/**
 * Provides an error message for insufficient credentials
 */
export const alertForInauthenticatedSession = () => {
  alert(
    `You are not logged in or your session has expired.  Please log in again.`
  );
};

/**
 * Provides an error message for bad credentials
 */
export const alertForUnauthorizedSession = () => {
  alert(`You are not authorized to access this page.`);
};
