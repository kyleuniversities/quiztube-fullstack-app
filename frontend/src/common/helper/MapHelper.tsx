import { InvalidInputError } from '../util/error';

/**
 * Helper class for Map related operations
 */
export class MapHelper {
  private constructor() {}

  /**
   * Iterates through all entries in a map
   */
  public static forEach<K, V>(
    map: Map<K, V>,
    action: (key: K, value: V) => void
  ): void {
    map.forEach((value: V, key: K) => action(key, value));
  }

  /**
   * Gets a nonnull value of a map or raises an error
   */
  public static getValue<K, V>(map: Map<K, V>, key: K): V {
    const value = map.get(key);
    if (!value) {
      const keyText = JSON.stringify(key);
      const errorMessage = `Value for key ${keyText} is undefined`;
      throw new InvalidInputError(errorMessage);
    }
    return value as V;
  }

  /**
   * Creates a new Map
   */
  public static newMap<K, V>(
    keyValuePairList?: Iterable<readonly [K, V]>
  ): Map<K, V> {
    return new Map<K, V>(keyValuePairList);
  }
}
