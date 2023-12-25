import axios from 'axios';

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
  alert('REQUEST: ' + fullUrl);
  alert('OPTIONS: ' + JSON.stringify(options));
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
    alert('DATA: ' + JSON.stringify(data));
    return data.data;
  });
};
