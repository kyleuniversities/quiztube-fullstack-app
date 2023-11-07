/**
 * Helper class for exception operations
 */
export class ExceptionHelper {
  private constructor() {}

  /**
   * Throws a Range Error
   */
  public static throwRangeError(message: string): void {
    throw RangeError(message);
  }
}
