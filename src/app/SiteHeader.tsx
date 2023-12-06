import { Header, Image, Menu } from 'semantic-ui-react';
import { ConditionalContent } from './ConditionalContent';
import { LinkButton } from './Component';
import './index.css';
import {
  NULL_USERNAME,
  useAuthorization,
  useUsername,
} from './auth/AuthorizationContextManager';
import { Link, useNavigate } from 'react-router-dom';
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
  const username = useUsername();
  const userIsSignedIn = username !== NULL_USERNAME;
  return (
    <>
      <ConditionalContent condition={userIsSignedIn}>
        <SiteHeaderUserSignedInContent username={username} />
      </ConditionalContent>
      <ConditionalContent condition={!userIsSignedIn}>
        <SiteHeaderUserSignedOutContent />
      </ConditionalContent>
    </>
  );
};

// The content relating to the user whilst signed in
const SiteHeaderUserSignedInContent = (props: {
  username: string;
}): JSX.Element => {
  const navigate = useNavigate();
  const userContext: any = useAuthorization();
  return (
    <Menu.Item position="right">
      <Header as="h2">Hello "{props.username}".</Header>
      <SiteHeaderSignedInButton
        onClick={(e: any) => logoutAction(navigate, userContext)}
      >
        Logout
      </SiteHeaderSignedInButton>
    </Menu.Item>
  );
};

// The content relating to the user whilst signed out
const SiteHeaderUserSignedOutContent = (): JSX.Element => {
  return (
    <Menu.Item position="right">
      <SiteHeaderSignedOutButton to="/login">Login</SiteHeaderSignedOutButton>
      <SiteHeaderSignedOutButton to="/registration">
        Sign Up
      </SiteHeaderSignedOutButton>
    </Menu.Item>
  );
};

// Button for Site Header Signed In Content
const SiteHeaderSignedInButton = (props: {
  onClick: (e: any) => void;
  children: ReactNode;
}): JSX.Element => {
  return (
    <button onClick={props.onClick} className="siteHeaderUserButton">
      {props.children}
    </button>
  );
};

// Link Button for Site Header Signed Out Content
const SiteHeaderSignedOutButton = (props: {
  to: string;
  children: ReactNode;
}): JSX.Element => {
  return (
    <LinkButton to={props.to} className="siteHeaderUserButton">
      {props.children}
    </LinkButton>
  );
};

// Logout Action
const logoutAction = (navigate: any, userContext: any): void => {
  const logOut = userContext.logOut;
  logOut();
  navigate('/');
  window.location.reload();
};
