import { MathHelper } from './MathHelper';

/**
 * Helper class for Random related operations
 */
export class RandomHelper {
  private constructor() {}

  /**
   * Returns a random integer between a start value and an up to value
   */
  public static newRandomInteger(start: number, upTo: number): number {
    const length = upTo - start;
    return MathHelper.floor(start + MathHelper.random() * length);
  }
}
