import { PromiseHelper } from '../../common/helper/js/PromiseHelper';
import { MarkdownHelper } from '../helper/MarkdownHelper';
import { debugAlert } from './debug';

/**
 * Info bucket URL
 * All files in this bucket are public
 */
const INFO_BUCKET_URL =
  'https://quiztube-info-bucket.s3.us-west-1.amazonaws.com';

/**
 * READ METHOD
 * Loads the text of a markdown
 */
export const loadMarkdownRequest = async (
  key: string | undefined,
  isSmallScreen: boolean,
  setText: any
): Promise<void> => {
  // Set request url
  const fullUrl = `${INFO_BUCKET_URL}/${key}/full.md`;
  debugAlert('MD_URL: ' + fullUrl);

  // Run request
  return fetch(fullUrl)
    .then((res: any) => res.text())
    .then((text: any) => {
      // Debug response if debugging
      debugAlert('MD_TEXT: ' + JSON.stringify(text));

      // Reformat text if on a small screen
      if (isSmallScreen) {
        text = MarkdownHelper.reformat(text, 40);
      }

      // Collect the text
      setText(text);

      // Return the promise
      return PromiseHelper.newConservativeVoidPromise();
    });
};
