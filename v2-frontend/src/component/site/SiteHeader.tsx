// Imports
import {
  CanvasHTMLAttributes,
  CSSProperties,
  useEffect,
  useState,
} from "react";
import styles from "./SiteHeader.module.css";
import StackContainer from "../util/stack/StackContainer";
import StackComponent from "../util/stack/StackComponent";
import Canvas, { DrawableCanvasOptions } from "../canvas/Canvas";
import DrawHelper from "../../helper/draw/DrawHelper";

// Parameters Interface
interface SiteHeaderParameters {
  className?: string;
  style?: CSSProperties;
}

// Export Component
export default function SiteHeader({
  className = "",
  style = {},
}: SiteHeaderParameters) {
  // Constant
  const innerBallLimit = 100;

  // Use State Constants
  const [innerBallTime, setInnerBallTime] = useState<number>(0);

  // Use Effects
  useEffect(() => {
    if (innerBallTime < innerBallLimit) {
      setTimeout(() => {
        setInnerBallTime(innerBallTime + 1);
      }, 20);
    }
  }, [innerBallTime]);

  // Helper Functions
  const draw = (context: CanvasRenderingContext2D, time: number = 0) => {
    const canvas = context.canvas;
    if (canvas) {
      const width = canvas.width;
      const height = canvas.height;
      const extension = Math.pow(Math.sin(time * 0.01), 2);
      const innerWidth = width * ((50 + 25 * extension) / 100);
      const innerHeight = height * ((50 + 25 * extension) / 100);
      DrawHelper.fillStyle(context, "white");
      DrawHelper.fillRect(context, 0, 0, width, height);
      DrawHelper.fillStyle(context, `hsl(${time % 360} 100% 50%)`);
      DrawHelper.fillRect(
        context,
        (width - innerWidth) / 2,
        (height - innerHeight) / 2,
        innerWidth,
        innerHeight,
      );
    }
  };

  // XML Parameters
  const containerStyle: CSSProperties = {
    ...style,
  };

  const logoStyle: CSSProperties = {
    width: `100vw`,
    height: `100vh`,
  };

  const canvasOptions: DrawableCanvasOptions = {
    width: window.innerWidth,
    height: window.innerHeight,
    draw,
  };

  // Return Component
  return (
    <div
      className={`${styles["container"]} ${{ className }}}`}
      style={containerStyle}
    >
      <StackContainer>
        <StackComponent zIndex={0}>
          <div className={styles["bottom-logo"]}>Hello</div>
        </StackComponent>
        <StackComponent zIndex={10}>
          <div className={styles["top-logo"]}>Hello</div>
        </StackComponent>
        <StackComponent zIndex={20}>
          <div className={styles["canvas-container"]}>
            <Canvas options={canvasOptions} />
          </div>
        </StackComponent>
      </StackContainer>
    </div>
  );
}
