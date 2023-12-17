import { ReactNode } from 'react';
import { SiteHeader } from './SiteHeader';
import { Container } from 'semantic-ui-react';
import './index.css';

/**
 * A UI Wrapper component for all site pages
 */
export const SitePage = (props: { children: ReactNode }): JSX.Element => {
  return (
    <Container fluid className="sitePage">
      <SiteHeader />
      {props.children}
    </Container>
  );
};
