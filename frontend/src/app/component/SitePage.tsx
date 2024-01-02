import { ReactNode } from 'react';
import { SiteHeader } from './SiteHeader';
import { useColorize } from './context/AppContextManager';
import { MultilineBreak } from './MultilineBreak';
import './index.css';
import { SiteSection } from '../util/structure/SiteSection';
import { SiteFooter } from './SiteFooter';

/**
 * Structure for storing site section data
 */
const SECTION_MAP = {
  ABOUT_ME: SiteSection.newInstance('About Me', '/sections/about-me'),
  ABOUT_THIS_WEBSITE: SiteSection.newInstance(
    'About This Website',
    '/sections/about-this-website'
  ),
  ABOUT_THIS_PROJECT: SiteSection.newInstance(
    'About This Project',
    '/sections/about-this-project'
  ),
  FRONTEND_DEVELOPMENT: SiteSection.newInstance(
    'Frontend Development',
    '/sections/frontend-development'
  ),
  BACKEND_DEVELOPMENT: SiteSection.newInstance(
    'Backend Development',
    '/sections/backend-development'
  ),
  DEPLOYMENT: SiteSection.newInstance('Deployment', '/sections/deployment'),
  MY_PORTFOLIO_WEBSITE: SiteSection.newInstance(
    'My Other Website',
    'https://kyleuniversities.com/'
  ),
  MY_LINKEDIN: SiteSection.newInstance(
    'My LinkedIn',
    'https://www.linkedin.com/in/kyle-humphrey-b1324524a/'
  ),
  MY_GITHUB: SiteSection.newInstance(
    'My GitHub',
    'https://github.com/kyleuniversities'
  ),
};

/**
 * Type for site section group
 */
export type SiteSectionGroupProps = {
  title: string;
  sectionMap: any;
  sectionKeys: string[];
};

/**
 * A UI Wrapper component for all site pages
 */
export const SitePage = (props: { children: ReactNode }): JSX.Element => {
  // Set up color data
  const colorize = useColorize();

  // Return component
  return (
    <div>
      <div className={colorize('sitePage')}>
        <SiteHeader />
        <div className="siteContentContainer">{props.children}</div>
        <MultilineBreak lines={2} />
        <SiteFooter sectionMap={SECTION_MAP} />
      </div>
    </div>
  );
};
