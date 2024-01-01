// Debug switch
const IS_DEBUGGING = false;

/**
 * Alert only if debugging is true
 */
export const debugAlert = (message: string) => {
  if (IS_DEBUGGING) {
    alert(message);
  }
};
