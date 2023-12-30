import axios from 'axios';

// Default api host for requests
export const DEFAULT_HOST: string = 'http://localhost:8080';

// Debug switch
const IS_DEBUGGING = true;

// Debug alert
const debugAlert = (message: string) => {
  if (IS_DEBUGGING) {
    alert(message);
  }
};

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
  debugAlert('REQUEST: ' + fullUrl);
  debugAlert('OPTIONS: ' + JSON.stringify(options));
  const fullOptions = {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Basic ' + btoa('user:pass'),
    },
    mode: 'cors',
    url: fullUrl,
  };
  return axios(fullOptions).then((data) => {
    debugAlert('DATA: ' + JSON.stringify(data));
    return data.data;
  });
};
