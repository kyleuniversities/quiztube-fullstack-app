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
   * Filters an array: Returns a new array of elements that satisfy a
   * condition.  It does not modify the original array
   */
  public static filter<T>(
    array: Array<T>,
    condition: (condition: T) => boolean
  ): Array<T> {
    return array.filter(condition);
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
   * Maps an array
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

  /**
   * Reduces an array: Synthesizes each element in a array.
   * The initial value is supplied, and a function is applied
   * to it and the next elements over and over until the end is reached.
   * The final value is returned.
   */
  public static reduce<T, U>(
    array: Array<T>,
    accumulator: (sum: U, nextValue: T) => U,
    initialValue: U
  ): U {
    return array.reduce(accumulator, initialValue);
  }

  /**
   * Slices an array: Iterates through the array from a start index to
   * an up to index and returns the iterated sub array. Same thing as
   * subList().  slice() == subList()
   */
  public static slice<T>(
    array: Array<T>,
    start: number,
    upTo: number
  ): Array<T> {
    return array.slice(start, upTo);
  }

  /**
   * Splices an array: Iterates through the array from a start index by
   * a given length.  Let upTo = start + length
   *
   * Iterates from a start index by a length, deleting iterated values
   * and moving them into a new array.  Returns the array of deleted
   * elements, having the original array modified.
   */
  public static splice<T>(
    array: Array<T>,
    start: number,
    length: number
  ): Array<T> {
    return array.splice(start, length);
  }
}
