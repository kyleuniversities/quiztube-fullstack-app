import { debugAlert } from '../service/debug';

/**
 * Default function for handling exceptions
 */
export const handleException = (exception: any): void => {
  debugAlert(`ERROR DATA: ${JSON.stringify(exception.response.data)}`);
  alert(`${exception.response.data.message}`);
};
