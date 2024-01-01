import axios from 'axios';
import { debugAlert } from './debug';
import { alertForUnauthorizedSession } from './alert';

// Default api host for requests
export const DEFAULT_HOST: string = 'http://localhost:8080';

/**
 * Utility function for finding the api host
 */
export const deriveApiHost = (): string => {
  return process.env['REACT_APP_API_FULL_HOST'] || DEFAULT_HOST;
};

/**
 * Utility function used for api requests to the backend
 */
export const request = async (url: string, options: any = {}): Promise<any> => {
  const apiHost = deriveApiHost();
  const fullUrl = `${apiHost}${url}`;
  return fullRequest(fullUrl, options);
};

/**
 * Utility function used for api requests to the backend including the api host
 */
export const fullRequest = async (
  fullUrl: string,
  options: any = {}
): Promise<any> => {
  // Debug check request and parmaters
  debugAlert('REQUEST: ' + fullUrl);
  debugAlert('OPTIONS: ' + JSON.stringify(options));

  // Set up headers
  const headers = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('access_token')}`,
  };

  // Set up API request parameters
  const fullOptions = {
    ...options,
    headers,
    mode: 'cors',
    url: fullUrl,
  };

  // Run API request
  return axios(fullOptions)
    .then((data) => {
      // Debug check response data
      debugAlert('DATA: ' + JSON.stringify(data));

      // If the data is unauthorized, display the error
      if (!data.data || data.data === '') {
        alertForUnauthorizedSession();
        debugAlert(
          `ERROR: User is not authorized to perform the operation >> "${fullUrl}"`
        );
        localStorage.removeItem('access_token');
        return;
      }

      // Return the data from the response if successful
      return data.data;
    })
    .catch((exception: any) => {
      // Display error if failed
      alert(`ERROR: ${exception.message} >> ${fullUrl}`);
    });
};
