// Imports
import { CSSProperties, ReactNode, useEffect, useState } from "react";
import styles from "./SitePage.module.css";
import SiteHeader from "./SiteHeader";

// Parameters Interface
interface SitePageParameters {
  className?: string;
  style?: CSSProperties;
  children: ReactNode;
}

// Export Component
export default function SitePage({
  className = "",
  style = {},
  children,
}: SitePageParameters) {
  // XML Parameters
  const containerStyle: CSSProperties = {
    ...style,
  };

  // Return Component
  return (
    <div
      className={`${styles["container"]} ${{ className }}}`}
      style={containerStyle}
    >
      <SiteHeader />
      {children}
    </div>
  );
}
