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
        setInnerBallTime(
          Math.min(
            innerBallTime + Math.max(innerBallTime < 50 ? 3 : 2, 1),
            innerBallLimit,
          ),
        );
      }, 10);
    }
  }, [innerBallTime]);

  // Helper Functions
  const draw = (context: CanvasRenderingContext2D, time: number = 0) => {
    const canvas = context.canvas;
    const logoImage = document.getElementById("logo") as HTMLImageElement;
    const logoImageWhite = document.getElementById(
      "logo-white",
    ) as HTMLImageElement;
    if (canvas && logoImage && logoImageWhite) {
      const width = canvas.width;
      const height = canvas.height;
      const centerX = width / 2;
      const centerY = height / 2;
      const radiusX = width * 0.15;
      const radiusY = width * 0.15;
      const innerBallPos = (innerBallTime - 50) / 50;
      DrawHelper.fillStyle(context, "rgba(0,0,0,0)");
      DrawHelper.clearRect(context, 0, 0, width, height);
      DrawHelper.globalAlpha(context, Math.max((innerBallTime - 50) / 50, 0));
      DrawHelper.drawImage(
        context,
        logoImage,
        centerX - width * 0.2,
        centerY - width * 0.2,
        width * 0.4,
        width * 0.4,
      );
      const pastTime = (innerBallTime - 50) * 2; // [0, 100]
      const exponent = 3;
      DrawHelper.globalAlpha(
        context,
        Math.max(
          innerBallTime < 100
            ? 0
            : (100 -
                Math.pow(pastTime, exponent) / Math.pow(100, exponent - 1)) /
                100,
          0,
        ),
      );
      const theta = ((innerBallTime - 50) / 50) * Math.PI * 2;
      DrawHelper.transformRotate(context, theta);
      DrawHelper.drawImage(
        context,
        logoImageWhite,
        centerX - width * 0.2,
        centerY - width * 0.2,
        width * 0.4,
        width * 0.4,
      );
      DrawHelper.transformRotate(context, -theta);
      DrawHelper.globalAlpha(context, 1.0);
      DrawHelper.fillStyle(context, "rgb(124,255,255)");
      for (let i = 0; i < 8; i++) {
        const angle = (Math.PI / 4) * i;
        DrawHelper.fillCircle(
          context,
          centerX + radiusX * innerBallPos * Math.cos(angle),
          centerY + radiusY * innerBallPos * Math.sin(angle),
          30,
        );
      }
      DrawHelper.fillStyle(context, "rgba(0,0,0,0)");
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
        <StackComponent zIndex={11}>
          <div className={styles["canvas-container"]}>
            <img id="logo" src="/logo800.png" />
          </div>
        </StackComponent>
        <StackComponent zIndex={12}>
          <div className={styles["canvas-container"]}>
            <img id="logo-white" src="/logo800white.png" />
          </div>
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
