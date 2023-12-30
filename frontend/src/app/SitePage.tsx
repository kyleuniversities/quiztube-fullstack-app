import { ReactNode, useState } from 'react';
import { SiteHeader } from './SiteHeader';
import { Button, Container } from 'semantic-ui-react';
import './index.css';
import { useColorize } from './context/AppContextManager';
import { MultilineBreak } from './MultilineBreak';

/**
 * A UI Wrapper component for all site pages
 */
export const SitePage = (props: { children: ReactNode }): JSX.Element => {
  const colorize = useColorize();
  return (
    <Container fluid className={colorize('sitePage')}>
      {/*<Button
        content={<span>Toggle Color Mode</span>}
        onClick={() => colorize.toggle()}
      />/**/}
      <SiteHeader />
      {props.children}
      <MultilineBreak lines={2} />
    </Container>
  );
};
