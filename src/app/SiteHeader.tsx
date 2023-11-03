import { Container, Icon, Image, Menu, Segment } from 'semantic-ui-react';
import { ConditionalContent } from './ConditionalContent';
import { LinkButton } from './Component';

/**
 * A header component for all site pages
 */
export const SiteHeader = (): JSX.Element => {
  return (
    <Segment
      style={{
        backgroundColor: 'rgb(160,255,160)',
        paddingLeft: '5px',
        borderColor: 'black',
        borderBottom: '5px solid black',
        borderRadius: '0px',
      }}
    >
      <Menu secondary borderless>
        <SiteHeaderHomeIconItem />
        <SiteHeaderTitleItem />
        <SiteHeaderUserContent />
      </Menu>
    </Segment>
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
  const headerTitleStyle = {
    color: 'black',
    fontFamily: 'Helvetica Neue',
    fontSize: '50px',
    fontWeight: 'bold',
  };
  return (
    <Menu.Item>
      <span style={headerTitleStyle}>Quizzical</span>
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
  return (
    <Menu.Item position="right">
      <LinkButton to="/login">Login</LinkButton>
      <LinkButton to="/registration">Sign Up</LinkButton>
    </Menu.Item>
  );
};
