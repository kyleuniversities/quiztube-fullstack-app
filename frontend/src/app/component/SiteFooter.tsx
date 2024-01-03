import { ReactNode } from 'react';
import { Container, Grid, List } from 'semantic-ui-react';
import { SiteSectionGroupProps } from './SitePage';
import { Link } from 'react-router-dom';

/**
 * The footer for the website
 */
export const SiteFooter = (props: { sectionMap: any }): JSX.Element => {
  return (
    <SiteFooterSegment>
      <SiteFooterGridAboutColumn sectionMap={props.sectionMap} />
      <SiteFooterGridDevelopmentColumn sectionMap={props.sectionMap} />
      <SiteFooterGridExternalLinksColumn sectionMap={props.sectionMap} />
    </SiteFooterSegment>
  );
};

/**
 * Site Footer Column for About Content
 */
const SiteFooterGridAboutColumn = (props: { sectionMap: any }): JSX.Element => {
  return (
    <SiteFooterGridColumn
      title="About"
      sectionMap={props.sectionMap}
      sectionKeys={['ABOUT_ME', 'ABOUT_THIS_WEBSITE', 'ABOUT_THIS_PROJECT']}
    />
  );
};

/**
 * Site Footer Column for Development Content
 */
const SiteFooterGridDevelopmentColumn = (props: {
  sectionMap: any;
}): JSX.Element => {
  return (
    <SiteFooterGridColumn
      title="How I Made This Website"
      sectionMap={props.sectionMap}
      sectionKeys={[
        'FRONTEND_DEVELOPMENT',
        'BACKEND_DEVELOPMENT',
        'DEPLOYMENT',
      ]}
    />
  );
};

/**
 * Site Footer Column for Project Content
 */
const SiteFooterGridExternalLinksColumn = (props: {
  sectionMap: any;
}): JSX.Element => {
  return (
    <SiteFooterGridColumn
      title="External Links"
      sectionMap={props.sectionMap}
      sectionKeys={['MY_PORTFOLIO_WEBSITE', 'MY_LINKEDIN', 'MY_GITHUB']}
    />
  );
};

/**
 * The segment for the footer of the website
 */
const SiteFooterSegment = (props: { children: ReactNode }): JSX.Element => {
  return (
    <div className="siteFooterSegment">
      <Container>
        <Grid divided inverted stackable>
          <Grid.Row>{props.children}</Grid.Row>
        </Grid>
      </Container>
    </div>
  );
};

/**
 * Grid Column for items of the site footer
 */
const SiteFooterGridColumn = (props: SiteSectionGroupProps): JSX.Element => {
  return (
    <Grid.Column width={5}>
      <SiteFooterGridColumnHeader title={props.title} />
      <List link inverted>
        {props.sectionKeys.map((key) => (
          <div key={key}>
            <SiteFooterListItem key={key} siteSection={props.sectionMap[key]} />
          </div>
        ))}
      </List>
    </Grid.Column>
  );
};

/**
 * Grid Column Header for items of the site footer
 */
const SiteFooterGridColumnHeader = (props: { title: string }): JSX.Element => {
  return <span className="siteFooterGridColumnHeader">{props.title}</span>;
};

/**
 * List Item for Site Footer
 */
const SiteFooterListItem = (props: { siteSection: any }): JSX.Element => {
  return (
    <div className="siteFooterListItemContainer">
      <List.Item as={Link} to={props.siteSection.url}>
        <span className="siteFooterListItem">{props.siteSection.title}</span>
      </List.Item>
    </div>
  );
};
