import { debugAlert } from '../service/debug';

/**
 * Default function for handling exceptions
 */
export const handleException = (exception: any): void => {
  debugAlert(`ERROR: ${JSON.stringify(exception)}`);
  debugAlert(`ERROR DATA: ${JSON.stringify(exception.response.data)}`);
  alert(`${exception.response.data.message}`);
};

/**
 * Default function for handling absent resource exceptions
 */
export const handleAbsentResourceException = (
  exception: any,
  setIsAbsent: any
): void => {
  if (exception.response.data.status === 404) {
    setIsAbsent(true);
    return;
  }
  alert(`${exception.response.data.message}`);
};
