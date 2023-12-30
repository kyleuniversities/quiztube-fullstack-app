import { Header } from 'semantic-ui-react';
import { ConditionalContent } from '../ConditionalContent';
import { SitePage } from '../SitePage';
import { NULL_ID, useUserId } from '../context/AppContextManager';
import { ViewListDataItemsPage } from '../dataitem/ViewListDataItemsPage';
import { ViewAllQuizzesPage } from '../dataitem/quiz/ViewAllQuizzesPage';

/**
 * The Home Page for the app
 */
export const HomePage = () => {
  const userId = useUserId();
  return <ViewAllQuizzesPage />;
};
