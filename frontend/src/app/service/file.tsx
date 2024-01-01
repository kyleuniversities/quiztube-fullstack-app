import { deriveApiHost } from './request';

// Collect picture image path
export const collectPicturePath = (item: any) => {
  const key = item.picture;
  const encodedKey = key.replace(/\//g, '__');
  return `${deriveApiHost()}/file/image/${encodedKey}`;
};

// Collect thumbnail image path
export const collectThumbnailPath = (item: any) => {
  const key = item.thumbnail;
  const encodedKey = key.replace(/\//g, '__');
  return `${deriveApiHost()}/file/image/${encodedKey}`;
};

// Collect thumbnail image path
export const collectDefaultThumbnailPathFromUsername = (username: string) => {
  const firstLetter = username.toLowerCase().charAt(0);
  return `${deriveApiHost()}/file/image/static__user__user-picture-${firstLetter}_T.png`;
};
