// Imports
import { CSSProperties } from "react";
import styles from "./HomePage.module.css";
import SitePage from "../component/site/SitePage";

// Parameters Interface
interface HomePageParameters {
  className?: string;
  style?: CSSProperties;
}

// Export Component
export default function HomePage({
  className = "",
  style = {},
}: HomePageParameters) {
  // XML Parameters
  const containerStyle: CSSProperties = {
    ...style,
  };

  // Return Component
  return (
    <SitePage
      className={`${styles["container"]} ${{ className }}}`}
      style={containerStyle}
    >
      HomePage
    </SitePage>
  );
}
