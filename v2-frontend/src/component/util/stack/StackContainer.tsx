// Imports
import { CSSProperties, ReactNode } from "react";
import styles from "./StackContainer.module.css";

// Props Interface
interface StackContainerProps {
  className?: string;
  style?: CSSProperties;
  children: ReactNode;
}

// Export Component
export default function StackContainer({
  className = "",
  style = {},
  children,
}: StackContainerProps) {
  return (
    <>
      <div
        className={`${styles["stack-container"]} ${className}}`}
        style={{ ...style, position: "relative" }}
      >
        {children}
      </div>
    </>
  );
}
