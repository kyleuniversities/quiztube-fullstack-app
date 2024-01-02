import { CardMarkdownReformatter } from '../util/markdown';

/**
 * Helper class for Markdown support
 */
export class MarkdownHelper {
  /**
   * Private constructor to prevent instantiation
   */
  private constructor() {}

  /**
   * Reformats Card Markdown Text
   */
  public static reformat(text: string, textBreakLength: number): string {
    return CardMarkdownReformatter.newInstance().reformat(
      text,
      textBreakLength
    );
  }
}
