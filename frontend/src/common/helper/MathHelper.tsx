/**
 * Helper class for Math related operations
 */
export class MathHelper {
  private constructor() {}

  /**
   * Applies the ceiling function to a value
   */
  public static ceiling(value: number): number {
    return Math.ceil(value);
  }

  /**
   * Applies the floor function to a value
   */
  public static floor(value: number): number {
    return Math.floor(value);
  }

  /**
   * Applies the max function to a value
   */
  public static max(a: number, b: number): number {
    return Math.max(a, b);
  }

  /**
   * Applies the min function to a value
   */
  public static min(a: number, b: number): number {
    return Math.min(a, b);
  }

  /**
   * Applies the random function: Produces a random number between
   * 0 and 1
   */
  public static random(): number {
    return Math.random();
  }
}
