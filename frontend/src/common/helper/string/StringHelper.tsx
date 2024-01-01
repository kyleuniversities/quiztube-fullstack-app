import { StringList, StringMap } from '../../util/string';
import { ArrayHelper } from './../ArrayHelper';

/**
 * Helper class for String  related operations
 */
export class StringHelper {
  private constructor() {}

  /**
   * Creates a new empty String List
   */
  public static newEmptyStringList(): StringList {
    return StringList.newEmptyInstance();
  }

  /**
   * Creates a new String Map
   */
  public static newStringMap(
    keyValuePairList?: Iterable<readonly [string, string]>
  ): StringMap {
    return StringMap.newInstance(keyValuePairList);
  }

  /**
   * Creates a new String Map
   */
  public static newStringMapFromDoubleArray(array: string[][]): StringMap {
    const map = StringHelper.newStringMap();
    ArrayHelper.forEach(array, (keyValuePair: string[]) => {
      map.set(keyValuePair[0], keyValuePair[1]);
    });
    return map;
  }

  /**
   * Checks if a substring is equal to a target string
   */
  public static substringEquals(
    text: string,
    target: string,
    index: number
  ): boolean {
    if (index + target.length > text.length) {
      return false;
    }
    return text.substring(index, index + target.length) === target;
  }
}
