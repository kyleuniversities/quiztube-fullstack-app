import { StringHelper } from './StringHelper';
import { StringReplacementHelper } from './StringReplacementHelper';

// Map for mapping text to code
const TO_CODE_REPLACEMENT_MAP = StringHelper.newStringMapFromDoubleArray([
  ['\\', '\\\\'],
  ['\n', '\\n'],
  ['\t', '\\t'],
  ['"', '\\"'],
]);

// Map for mapping code to text
const TO_TEXT_REPLACEMENT_MAP = TO_CODE_REPLACEMENT_MAP.flip();

/**
 * Helper class for String Code related operations
 */
export class StringCodeHelper {
  private constructor() {}

  /**
   * Converts a text to its representation as a String literal
   */
  public static toCode(text: string): string {
    const code: string[] = [];
    code.push('"');
    code.push(StringCodeHelper.replaceToCode(text));
    code.push('"');
    return code.join('');
  }

  /**
   * Converts a String literal representation to its text
   */
  public static toText(code: string): string {
    const unquotedCode = code.substring(1, code.length - 1);
    return StringCodeHelper.replaceToText(unquotedCode);
  }

  /**
   * Replaces noncode characters with escape code strings
   */
  private static replaceToCode(text: string) {
    return StringReplacementHelper.replaceAllWithStringMap(
      text,
      TO_CODE_REPLACEMENT_MAP
    );
  }

  /**
   * Replaces escape code strings to noncode characters
   */
  private static replaceToText(text: string) {
    return StringReplacementHelper.replaceAllWithStringMap(
      text,
      TO_TEXT_REPLACEMENT_MAP
    );
  }
}
