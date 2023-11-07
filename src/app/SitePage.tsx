import { ReactNode } from 'react';
import { SiteHeader } from './SiteHeader';
import { Container } from 'semantic-ui-react';

/**
 * A UI Wrapper component for all site pages
 */
export const SitePage = (props: { children: ReactNode }): JSX.Element => {
  const sitePageStyle = {
    backgroundColor: 'rgb(60,255,60)',
    minHeight: '100vh',
  };
  return (
    <Container fluid style={sitePageStyle}>
      <SiteHeader />
      {props.children}
    </Container>
  );
};
