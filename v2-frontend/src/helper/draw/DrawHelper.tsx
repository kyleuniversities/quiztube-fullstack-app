// Import
import Point from "../../type/draw/Point";

// Helper Class for Drawing
export default class DrawHelper {
  /**
   * Clears a rectangle on a context
   */
  static clearRect(
    context: CanvasRenderingContext2D,
    x: number,
    y: number,
    w: number,
    h: number,
  ) {
    context.clearRect(x, y, w, h);
  }

  /**
   * Draws an Image
   */
  static drawImage(
    context: CanvasRenderingContext2D,
    image: HTMLImageElement,
    x: number,
    y: number,
    w: number,
    h: number,
  ) {
    context.drawImage(image, x, y, w, h);
  }

  /**
   * Fills a circle on a context
   */
  static fillCircle(
    context: CanvasRenderingContext2D,
    x: number,
    y: number,
    r: number,
  ) {
    context.beginPath();
    context.arc(x, y, r, 0, 2 * Math.PI);
    context.fill();
  }

  /**
   * Fills a rectangle on a context
   */
  static fillRect(
    context: CanvasRenderingContext2D,
    x: number,
    y: number,
    w: number,
    h: number,
  ) {
    context.fillRect(x, y, w, h);
  }

  /**
   * Sets the fill style of a context
   */
  static fillStyle(context: CanvasRenderingContext2D, style: string) {
    context.fillStyle = style;
  }

  /**
   * Sets the global alpha of a context
   */
  static globalAlpha(context: CanvasRenderingContext2D, alpha: number) {
    context.globalAlpha = alpha;
  }

  /**
   * Sets the line width of a context
   */
  static lineWidth(context: CanvasRenderingContext2D, width: number) {
    context.lineWidth = width;
  }
  /**
   * Strokes a line on a context
   */
  static strokeLine(
    context: CanvasRenderingContext2D,
    x1: number,
    y1: number,
    x2: number,
    y2: number,
  ) {
    context.beginPath();
    context.moveTo(x1, y1);
    context.lineTo(x2, y2);
    context.stroke();
  }

  /**
   * Strokes a line on a context
   */
  static strokeLineWithPoints(
    context: CanvasRenderingContext2D,
    p1: Point,
    p2: Point,
  ) {
    DrawHelper.strokeLine(context, p1.x, p1.y, p2.x, p2.y);
  }

  /**
   * Sets the stroke style of a context
   */
  static strokeStyle(context: CanvasRenderingContext2D, style: string) {
    context.strokeStyle = style;
  }

  /**
   * Transforms an image by rotation
   */
  static transformRotate(context: CanvasRenderingContext2D, theta: number) {
    context.transform(
      Math.cos(theta),
      Math.sin(theta),
      -Math.sin(theta),
      Math.cos(theta),
      0,
      0,
    );
  }

  /**
   * Translayes an image
   */
  static transformRotate(context: CanvasRenderingContext2D, theta: number) {
    context.transform(
      Math.cos(theta),
      Math.sin(theta),
      -Math.sin(theta),
      Math.cos(theta),
      0,
      0,
    );
  }

  // Constructor
  private constructor() {
    // Do Nothing
  }
}
