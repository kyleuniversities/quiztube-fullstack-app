import { ifThen } from '../../common/util/conditional';
import { IntegerWrapper } from '../../common/util/wrapper';

/**
 * Utility class for reformatting card markdown links on mobile
 */
export class CardMarkdownReformatter {
  // Instance Fields
  private text: string;
  private textBreakLength: number;
  private lines: string[];
  private reformattedLines: string[];
  private line: string;
  private isLink: boolean;

  /**
   * New Instance Method
   */
  public static newInstance(): CardMarkdownReformatter {
    return new CardMarkdownReformatter();
  }

  /**
   * Constructor Method
   */
  private constructor() {
    this.text = '';
    this.textBreakLength = -1;
    this.lines = [];
    this.reformattedLines = [];
    this.line = '';
    this.isLink = false;
  }

  /**
   * Main Instance Method
   */
  public reformat(text: string, textBreakLength: number): string {
    this.reset(text, textBreakLength);
    this.forEachLine((line: string) => {
      this.resetLine(line);
      this.checkIfIsLink();
      ifThen(this.isLink, () => this.processLinkLine());
      ifThen(!this.isLink, () => this.processNonLinkLine());
    });
    console.log('\n\n\n');
    return this.reformattedLines.join('\n'); //text; // reformattedLines.join('\n');
  }

  /**
   * Major Methods
   */
  private checkIfIsLink(): void {
    let isLink =
      this.line.length > 1 &&
      this.line.charAt(0) === '[' &&
      this.line.charAt(this.line.length - 2) === ')';
    this.isLink = isLink;
    console.log(this.isLink);
    console.log(' ' + this.line.length);
    console.log(' ' + this.line.charAt(0));
    console.log(' ' + this.line.charAt(this.line.length - 1));
  }

  private processLinkLine(): void {
    const restIndex = this.line.indexOf(']');
    const rest = this.line.substring(restIndex);
    const firstText = this.line.substring(0, restIndex);
    const linkLineBuilder: string[] = [];
    const numberOfNonDashCharacters = IntegerWrapper.newInstance(0);
    for (let i = 0; i < firstText.length; i++) {
      const ch = firstText.charAt(i);
      linkLineBuilder.push(ch);
      ifThen(ch === '-', () => numberOfNonDashCharacters.setValue(0));
      ifThen(ch !== '-', () => {
        numberOfNonDashCharacters.increment();
        if (numberOfNonDashCharacters.isEqualTo(this.textBreakLength)) {
          linkLineBuilder.push('-');
          numberOfNonDashCharacters.setValue(0);
        }
      });
    }
    linkLineBuilder.push(rest);
    this.reformattedLines.push(linkLineBuilder.join(''));
  }

  private processNonLinkLine(): void {
    this.reformattedLines.push(this.line);
  }

  /**
   * Iteration Methods
   */
  private forEachLine(action: (text: string) => void): void {
    for (let i = 0; i < this.lines.length; i++) {
      console.log('LINE: ' + i);
      action(this.lines[i]);
    }
  }

  /**
   * Initializations Methods
   */
  private resetLine(line: string): void {
    this.line = line;
  }

  private reset(text: string, textBreakLength: number): void {
    this.text = text;
    this.textBreakLength = textBreakLength;
    this.lines = text.split('\n');
    this.reformattedLines = [];
  }
}

/*
public reformat(text: string): string {
    const reformattedLines: string[] = [];
    const lines = text.split('\n');
    for (let i = 0; i < lines.length; i++) {
      const line = lines[i];
      const isLink =
        line.length > 1 &&
        line.charAt(0) == '[' &&
        line.charAt(line.length - 1) == ')';
      if (isLink) {
      }
      if (!isLink) {
      }
    }
    return text; // reformattedLines.join('\n');
  }
**/
