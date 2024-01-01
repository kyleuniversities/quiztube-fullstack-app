/**
 * Helper class for Boolean related operations
 */
export class BooleanHelper {
  private constructor() {}

  /**
   * Converts text to a boolean
   */
  public static parseBoolean(text: string): boolean {
    if (text !== 'true' && text !== 'false') {
      throw SyntaxError('Text is not a boolean');
    }
    return text === 'true';
  }
}
