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
