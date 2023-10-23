import { Icon, Image, Menu } from 'semantic-ui-react';

/**
 * A header component for all site pages
 */
export const SiteHeader = (): JSX.Element => {
  return (
    <Menu secondary borderless>
      <SiteHeaderHomeIconItem />
      <SiteHeaderTitleItem />
    </Menu>
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
const SiteHeaderTitleItem = () => {
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
