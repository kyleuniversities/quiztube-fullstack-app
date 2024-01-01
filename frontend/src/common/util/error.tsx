/**
 * Utility error for operations receiving invalid input
 */
export class InvalidInputError extends Error {
  constructor(message: string) {
    super(message);
    Object.setPrototypeOf(this, InvalidInputError.prototype);
  }
}
