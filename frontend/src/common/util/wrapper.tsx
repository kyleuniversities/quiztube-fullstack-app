/**
 * Utility class for integer wrapping
 */
export class IntegerWrapper {
  // Instance Fields
  private value: number;

  /**
   * New Instance Method
   */
  public static newInstance(value: number): IntegerWrapper {
    return new IntegerWrapper(value);
  }

  /**
   * Constructor Method
   */
  private constructor(value: number) {
    this.value = value;
  }

  /**
   * Getter Method
   */
  public getValue(): number {
    return this.value;
  }

  /**
   * Setter Methods
   */
  public setValue(value: number): void {
    this.value = value;
  }

  /**
   * Operant Methods
   */
  public increment(): void {
    this.setValue(this.getValue() + 1);
  }

  public isEqualTo(value: number): boolean {
    return this.value === value;
  }
}
