// Imports
import { CanvasHTMLAttributes, CSSProperties, useEffect, useRef } from "react";
import styles from "./Canvas.module.css";

// Parameters Interfaces
export interface DrawableCanvasOptions
  extends CanvasHTMLAttributes<HTMLCanvasElement> {
  draw: (context: CanvasRenderingContext2D, time?: number) => void;
}

interface CanvasParameters {
  className?: string;
  style?: CSSProperties;
  timeLimit?: number;
  options: DrawableCanvasOptions;
}

// Export Component
export default function Canvas({
  className = "",
  style = {},
  timeLimit = 1000000000,
  options,
}: CanvasParameters) {
  // Draw Function Constants
  const { draw } = options;

  // Use Reference Constants
  const canvasReference = useRef<HTMLCanvasElement>(null);

  // Set up to draw continuously
  useEffect(() => {
    // Set up canvas data
    const canvas = canvasReference.current;

    // Set up frame data
    let time = 0;
    let frameIsPresent = false;
    let frameId: number;

    if (canvas) {
      // Set up context
      const context: CanvasRenderingContext2D | null = canvas.getContext("2d");

      if (context) {
        // Set up function to draw the image
        const renderImage = () => {
          time = (time + 1) % timeLimit;
          draw(context, time);
          frameId = window.requestAnimationFrame(renderImage);
          frameIsPresent = true;
        };

        // Draw the image
        renderImage();
      }
    }

    // Returns the cleanup function
    // The cleanup function is to cleanup effects from previous renders
    return () => {
      if (frameIsPresent) {
        window.cancelAnimationFrame(frameId);
        frameIsPresent = false;
      }
    };
  }, [draw]);

  // XML Parameters
  const containerStyle: CSSProperties = {
    ...style,
  };

  // Return Component
  return (
    <canvas
      ref={canvasReference}
      className={`${styles["container"]} ${className}`}
      style={containerStyle}
      {...options}
    />
  );
}
