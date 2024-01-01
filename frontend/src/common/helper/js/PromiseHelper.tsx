/**
 * Helper class for Promise related operations
 */
export class PromiseHelper {
  private constructor() {}

  /**
   * Creates a promise that resolves immediately
   */
  public static async newConservativePromise<T>(returnValue: T): Promise<T> {
    return new Promise((resolve, reject) => resolve(returnValue));
  }

  /**
   * Creates a void promise that resolves immediately
   */
  public static async newConservativeVoidPromise(): Promise<void> {
    return new Promise((resolve, reject) => resolve());
  }
}
