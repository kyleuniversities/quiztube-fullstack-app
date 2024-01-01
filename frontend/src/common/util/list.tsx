/**
 * Returns the index of the first instance of an item matching the given query
 */
export const indexOf = (list: any, query: (item: any) => boolean): number => {
  for (let i = 0; i < list.length; i++) {
    if (query(list[i])) {
      return i;
    }
  }
  return -1;
};
