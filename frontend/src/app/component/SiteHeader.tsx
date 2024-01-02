import { Container, Dropdown, Image, Menu } from 'semantic-ui-react';
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
import { NULL_USER, loadUserRequest } from '../service/entity/user';
import { isPassableId, logoutAction } from '../service/auth';
import './index.css';
import { useMediaQuery } from 'react-responsive';
import {
  BIG_SCREEN_QUERY,
  MEDIUM_SCREEN_QUERY,
} from '../../common/util/mobile';

/**
 * A header component for all site pages
 */
export const SiteHeader = (): JSX.Element => {
  // Set up color data
  const colorize = useColorize();

  // Set up media data
  const isBigScreen = useMediaQuery(BIG_SCREEN_QUERY);
  const isMediumScreen = useMediaQuery(MEDIUM_SCREEN_QUERY);
  const isSmallScreen = !isBigScreen && !isMediumScreen;

  // Return component
  return (
    <div className={colorize('siteHeaderSegment')}>
      <Container fluid>
        <Menu secondary borderless>
          <SiteHeaderHomeIconItem />
          <ConditionalContent condition={!isSmallScreen}>
            <SiteHeaderTitleItem />
          </ConditionalContent>
          <SiteHeaderUserContent />
        </Menu>
      </Container>
    </div>
  );
};

// The home icon for the website
const SiteHeaderHomeIconItem = (): JSX.Element => {
  // Set up color data
  const colorize = useColorize();
  const logo = require('../resource/logo.png');
  const logoLight = require('../resource/logo-light.png');
  const selectedLogo = colorize.isDefaultMode() ? logo : logoLight;

  // Return component
  return (
    <Menu.Item style={{ marginLeft: '0px', paddingLeft: '0px' }}>
      <Link to="/">
        <Image className="siteLogoImage" src={selectedLogo} />
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
        <span className={colorize('siteHeaderTitleItem')}>QuizTube</span>
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
  const [user, setUser] = useState(NULL_USER);

  // Set up user session data
  const userContext = useAppContext();
  const userId = useUserId();

  // Set up color session data
  const colorize = useColorize();

  // Set up navigation
  const navigate = useNavigate();

  // Load user
  useEffect(() => {
    loadUserRequest(userId, setUser);
  }, [userId]);

  // Return component
  return (
    <Menu.Item
      style={{ marginRight: '0px', paddingRight: '0px' }}
      position="right"
    >
      <Image
        className="siteHeaderUserThumbnailImage"
        src={collectThumbnailPath(user)}
      />
      <div className={colorize('siteHeaderUserDropdown')}>
        <Dropdown direction="left" inline item text={props.username}>
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
            <Dropdown.Item onClick={() => colorize.toggle()}>
              {colorize.opposite()} Mode
            </Dropdown.Item>
            <Dropdown.Item onClick={() => logoutAction(navigate, userContext)}>
              Logout
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </div>
    </Menu.Item>
  );
};

// The content relating to the user whilst signed out
const SiteHeaderUserSignedOutContent = (): JSX.Element => {
  // Set up user viewing data
  const user = NULL_USER;

  // Set up color context data
  const colorize: any = useColorize();

  // Return component
  return (
    <Menu.Item
      style={{ marginRight: '0px', paddingRight: '0px' }}
      position="right"
    >
      <Image
        className="siteHeaderUserThumbnailImage"
        src={collectThumbnailPath(user)}
      />
      <Dropdown direction="left" inline item text={'Guest'}>
        <Dropdown.Menu>
          <Dropdown.Item as={Link} to={`/login`}>
            Log In
          </Dropdown.Item>
          <Dropdown.Item as={Link} to={`/registration`}>
            Sign Up
          </Dropdown.Item>
          <Dropdown.Item onClick={() => colorize.toggle()}>
            {colorize.opposite()} Mode
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </Menu.Item>
  );
};
