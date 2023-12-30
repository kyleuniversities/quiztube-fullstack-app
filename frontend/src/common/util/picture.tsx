import { deriveApiHost } from './request';

// Collect picture image path
export const collectPicturePath = (user: any) => {
  const key = user.picture;
  const encodedKey = key.replace(/\//g, '__');
  return `${deriveApiHost()}/file/image/${encodedKey}`;
};

// Collect thumbnail image path
export const collectThumbnailPath = (user: any) => {
  const key = user.thumbnail;
  const encodedKey = key.replace(/\//g, '__');
  return `${deriveApiHost()}/file/image/${encodedKey}`;
};
