import { debugAlert } from './debug';
import { deriveApiHost } from './request';

// Collect picture image path
export const collectPicturePath = (item: any) => {
  return `${deriveApiHost()}/file/image/${encode(item.picture)}`;
};

// Collect thumbnail image path
export const collectThumbnailPath = (item: any) => {
  return `${deriveApiHost()}/file/image/${encode(item.thumbnail)}`;
};

// Collect thumbnail image path
export const collectDefaultThumbnailPathFromUsername = (username: string) => {
  const firstLetter = username.toLowerCase().charAt(0);
  const key = `static/user/user-picture-${firstLetter}_T.png`;
  return `${deriveApiHost()}/file/image/${key}`;
};

// Collect thumbnail image key
export const collectDefaultThumbnailKeyFromUsername = (username: string) => {
  const firstLetter = username.toLowerCase().charAt(0);
  return `static/user/user-picture-${firstLetter}_T.png`;
};

// Encode file key to be passable in the url
export const encode = (key: string): string => {
  return key.replace(/\//g, '__').replace(/\#/g, '*');
};
