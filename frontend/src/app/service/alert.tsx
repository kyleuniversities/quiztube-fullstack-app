export const alertForInauthenticatedSession = () => {
  alert(
    `ERROR: You are not logged in or your session has expired.  Please log in again.`
  );
};

export const alertForUnauthorizedSession = () => {
  alert(`ERROR: You are not authorized to access this page.`);
};
