/**
 * Helper class for Array related operations
 */
export class ArrayHelper {
  private constructor() {}

  /**
   * Clears an array
   */
  public static clear<T>(array: Array<T>): void {
    array.splice(0);
  }

  /**
   * Clones an array
   */
  public static clone<T>(array: Array<T>): Array<T> {
    const clone: Array<T> = [];
    ArrayHelper.forEach(array, (item: T) => clone.push(item));
    return clone;
  }

  /**
   * Iterates through all elements in an array
   */
  public static forEach<T>(array: Array<T>, action: (item: T) => void): void {
    array.forEach((item: T) => action(item));
  }

  /**
   * Gets a nonnull value of an array or raises an error
   */
  public static getValue<T>(array: Array<T>, index: number): T {
    return array[index];
  }

  /**
   * Returns the index of the first instance of an item matching the given query
   */
  public static indexOf<T>(
    array: Array<T>,
    query: (item: T) => boolean
  ): number {
    for (let i = 0; i < array.length; i++) {
      if (query(array[i])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Maps a list
   */
  public static map<T, U>(array: Array<T>, mapping: (item: T) => U): U[] {
    return array.map((item: T) => mapping(item));
  }

  /**
   * Queries a value from an array
   */
  public static query<T>(array: Array<T>, query: (item: T) => boolean): T {
    const nullItem: any = null;
    for (let i = 0; i < array.length; i++) {
      if (query(array[i])) {
        return array[i];
      }
    }
    return nullItem;
  }
}
