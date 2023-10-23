import { RequestContainer } from '../RequestContainer';
import { SitePage } from '../SitePage';

/**
 * The Home Page for the app
 */
export const HomePage = () => {
  return (
    <SitePage>
      <RequestContainer />
    </SitePage>
  );
};
