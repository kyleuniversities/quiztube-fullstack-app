import { Dropdown, Header, Image, Menu } from 'semantic-ui-react';
import { ConditionalContent } from './ConditionalContent';
import { LinkButton } from './Component';
import './index.css';
import {
  NULL_USERNAME,
  useAppContext,
  useColorize,
  useUsername,
} from './context/AppContextManager';
import { Link, useNavigate } from 'react-router-dom';
import { ReactNode, useEffect, useState } from 'react';
import { deriveApiHost, request } from '../common/util/request';
import { collectThumbnailPath } from '../common/util/picture';

/**
 * A header component for all site pages
 */
export const SiteHeader = (): JSX.Element => {
  const colorize = useColorize();
  return (
    <div className={colorize('siteHeaderSegment')}>
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
  const colorize = useColorize();
  const logo = require('./resources/logo.png');
  const logoLight = require('./resources/logo-light.png');
  const selectedLogo = colorize.colorMode().length === 0 ? logo : logoLight;
  return (
    <Menu.Item>
      <Link to="/">
        <Image src={selectedLogo} />
      </Link>
    </Menu.Item>
  );
};

// The title for the website
const SiteHeaderTitleItem = (): JSX.Element => {
  const colorize = useColorize();
  return (
    <Menu.Item>
      <Link to="/">
        <span className={colorize('siteHeaderTitleItem')}>Quizzical</span>
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

const DEFAULT_USER: any = {
  thumbnail: 'null',
};

// The content relating to the user whilst signed in
const SiteHeaderUserSignedInContent = (props: {
  username: string;
}): JSX.Element => {
  const navigate = useNavigate();
  const userContext: any = useAppContext();
  const [user, setUser] = useState(DEFAULT_USER);
  useEffect(() => {
    request(`/users/${userContext.user.userId}`).then((res) => {
      setUser(res);
    });
  }, []);
  return (
    <Menu.Item position="right">
      <Image src={collectThumbnailPath(user)} />
      <Dropdown inline item text={props.username}>
        <Dropdown.Menu>
          <Dropdown.Item as={Link} to={`/users/${userContext.user.userId}`}>
            My Account
          </Dropdown.Item>
          <Dropdown.Item
            as={Link}
            to={`/users/${userContext.user.userId}/quizzes`}
          >
            My Quizzes
          </Dropdown.Item>
          <Dropdown.Item as={Link} to={`/quizzes/add`}>
            New Quiz
          </Dropdown.Item>
          <Dropdown.Item onClick={() => logoutAction(navigate, userContext)}>
            Logout
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
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
  const colorize = useColorize();
  return (
    <button
      onClick={props.onClick}
      className={colorize('siteHeaderUserButton')}
    >
      {props.children}
    </button>
  );
};

// Link Button for Site Header Signed Out Content
const SiteHeaderSignedOutButton = (props: {
  to: string;
  children: ReactNode;
}): JSX.Element => {
  const colorize = useColorize();
  return (
    <LinkButton to={props.to} className={colorize('siteHeaderUserButton')}>
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
