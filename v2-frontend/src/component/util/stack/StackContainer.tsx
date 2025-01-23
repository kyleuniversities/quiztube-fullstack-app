// Imports
import { CSSProperties, ReactNode } from "react";
import styles from "./StackContainer.module.css";

// Parameters Interface
interface StackContainerParameters {
  className?: string;
  style?: CSSProperties;
  children: ReactNode;
}

// Export Component
export default function StackContainer({
  className = "",
  style = {},
  children,
}: StackContainerParameters) {
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
