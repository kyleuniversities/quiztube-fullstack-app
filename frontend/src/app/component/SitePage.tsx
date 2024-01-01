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
    <Container fluid className={colorize('sitePage')}>
      <SiteHeader />
      {props.children}
      <MultilineBreak lines={2} />
    </Container>
  );
};
