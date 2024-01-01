import { StringMap, TextReplacer } from '../../util/string';
import { StringHelper } from './StringHelper';

/**
 * Helper class for String Replacement related operations
 */
export class StringReplacementHelper {
  private constructor() {}

  /**
   * Replaces all instances of a target string in a text
   */
  public static replaceAll(
    text: string,
    target: string,
    replacement: string
  ): string {
    const replacementMap = StringHelper.newStringMapFromDoubleArray([
      [target, replacement],
    ]);
    return StringReplacementHelper.replaceAllWithStringMap(
      text,
      replacementMap
    );
  }

  /**
   * Replaces all instances of a target string in a text with a String Map
   */
  public static replaceAllWithStringMap(
    text: string,
    replacementMap: StringMap
  ): string {
    return TextReplacer.newInstance().replaceWithReplacementMap(
      text,
      replacementMap
    );
  }
}
