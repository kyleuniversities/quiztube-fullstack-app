// Imports
import { CSSProperties, ReactNode } from "react";
import styles from "./StackComponent.module.css";

// Props Interface
interface StackContainerProps {
  zIndex: number;
  className?: string;
  style?: CSSProperties;
  children: ReactNode;
}

// Export Component
export default function StackComponent({
  zIndex,
  className = "",
  style = { zIndex },
  children,
}: StackContainerProps) {
  return (
    <>
      <div
        className={`${styles["stack-component"]} ${className}}`}
        style={{ position: "relative", ...style }}
      >
        {children}
      </div>
    </>
  );
}
