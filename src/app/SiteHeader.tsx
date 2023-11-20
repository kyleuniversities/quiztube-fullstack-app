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
      <Image src={logo} />
    </Menu.Item>
  );
};

// The title for the website
const SiteHeaderTitleItem = (): JSX.Element => {
  return (
    <Menu.Item>
      <span className="siteHeaderTitleItem">Quizzical</span>
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
      <LinkButton to="/login">Login</LinkButton>
      <LinkButton to="/registration">Sign Up</LinkButton>
    </Menu.Item>
  );
};
