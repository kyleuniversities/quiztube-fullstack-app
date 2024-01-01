import { Dropdown, Image, Menu } from 'semantic-ui-react';
import { ConditionalContent } from './ConditionalContent';
import { LinkButton } from './Component';
import {
  useAppContext,
  useColorize,
  useUserId,
  useUsername,
} from './context/AppContextManager';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { ReactNode, useEffect, useState } from 'react';
import { collectThumbnailPath } from '../service/file';
import './index.css';
import { loadUserRequest } from '../service/entity/user';
import { isPassableId, logoutAction } from '../service/auth';

// A placeholder user to avoid reading errors
const DEFAULT_USER: any = {
  thumbnail: 'null',
};

/**
 * A header component for all site pages
 */
export const SiteHeader = (): JSX.Element => {
  // Set up color data
  const colorize = useColorize();

  // Return component
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
  // Set up color data
  const colorize = useColorize();
  const logo = require('../resource/logo.png');
  const logoLight = require('../resource/logo-light.png');
  const selectedLogo = colorize.colorMode().length === 0 ? logo : logoLight;

  // Return component
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
  // Set up color data
  const colorize = useColorize();

  // Return component
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
  // Set up user data
  const username = useUsername();
  const userId = useUserId();
  const userIsSignedIn = isPassableId(userId);

  // Return component
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
  // Set up user viewing data
  const [user, setUser] = useState(DEFAULT_USER);

  // Set up user session data
  const userContext = useAppContext();
  const userId = useUserId();

  // Set up navigation
  const navigate = useNavigate();

  // Load user
  useEffect(() => {
    loadUserRequest(userId, setUser);
  }, [userId]);

  // Return component
  return (
    <Menu.Item position="right">
      <Image src={collectThumbnailPath(user)} />
      <Dropdown inline item text={props.username}>
        <Dropdown.Menu>
          <Dropdown.Item as={Link} to={`/users/${userId}`}>
            My Account
          </Dropdown.Item>
          <Dropdown.Item as={Link} to={`/users/${userId}/quizzes`}>
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
