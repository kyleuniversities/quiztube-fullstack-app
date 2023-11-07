// Default api host for requests
export const DEFAULT_HOST: string = 'http://localhost:8080/api';

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
  return fetch(fullUrl, {
    ...options,
  }).then((data) => {
    return data.json();
  });
};
