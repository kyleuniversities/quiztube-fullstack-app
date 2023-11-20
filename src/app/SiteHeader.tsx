import {
  Container,
  Header,
  Icon,
  Image,
  Menu,
  Segment,
} from 'semantic-ui-react';
import { ConditionalContent } from './ConditionalContent';
import { LinkButton } from './Component';
import './index.css';
import { useAuthorization } from './auth/AuthorizationContextManager';
import { Link } from 'react-router-dom';
import { ReactNode } from 'react';

/**
 * A header component for all site pages
 */
export const SiteHeader = (): JSX.Element => {
  return (
    <div className="siteHeaderSegment">
      <Menu secondary borderless>
        <SiteHeaderHomeIconItem />
        <SiteHeaderTitleItem />
        <SiteHeaderUserContent />
      </Menu>
    </div>
  );
};

// The home icon for the website
const SiteHeaderHomeIconItem = (): JSX.Element => {
  const logo = require('./resources/logo.png');
  return (
    <Menu.Item>
      <Link to="/">
        <Image src={logo} />
      </Link>
    </Menu.Item>
  );
};

// The title for the website
const SiteHeaderTitleItem = (): JSX.Element => {
  return (
    <Menu.Item>
      <Link to="/">
        <span className="siteHeaderTitleItem">Quizzical</span>
      </Link>
    </Menu.Item>
  );
};

// The content relating to the user using the website
const SiteHeaderUserContent = (): JSX.Element => {
  return (
    <ConditionalContent condition={true}>
      <SiteHeaderUserSignedOutContent />
    </ConditionalContent>
  );
};

// The content relating to the user whilst signed out
const SiteHeaderUserSignedOutContent = (): JSX.Element => {
  const userContext: any = useAuthorization();
  const username =
    userContext && userContext.user && userContext.user.username
      ? userContext.user.username
      : '#nullUser';
  return (
    <Menu.Item position="right">
      <Header as="h2">Hello "{username}".</Header>
      <SiteHeaderSignedOutButton to="/login">Login</SiteHeaderSignedOutButton>
      <SiteHeaderSignedOutButton to="/registration">
        Sign Up
      </SiteHeaderSignedOutButton>
    </Menu.Item>
  );
};

// Link Button for Site Header Signed Out Content
const SiteHeaderSignedOutButton = (props: {
  to: string;
  children: ReactNode;
}): JSX.Element => {
  return (
    <LinkButton to={props.to} className="siteHeaderSignedOutButton">
      {props.children}
    </LinkButton>
  );
};
