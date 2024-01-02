import { ReactNode } from 'react';
import { SiteHeader } from './SiteHeader';
import { Container } from 'semantic-ui-react';
import { useColorize } from './context/AppContextManager';
import { MultilineBreak } from './MultilineBreak';
import './index.css';

/**
 * A UI Wrapper component for all site pages
 */
export const SitePage = (props: { children: ReactNode }): JSX.Element => {
  // Set up color data
  const colorize = useColorize();

  // Return component
  return (
    <div>
      {
        <div className={colorize('sitePage')}>
          <SiteHeader />
          <div className="siteContentContainer">{props.children}</div>
          <MultilineBreak lines={2} />
        </div>
      }
      {/*<p>Hello</p>*/}
    </div>
  );
};
