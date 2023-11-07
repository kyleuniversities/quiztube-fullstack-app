import { RequestContainer } from '../RequestContainer';
import { SitePage } from '../SitePage';
import { ViewListDataItemsPage } from '../dataitem/ViewListDataItemsPage';

/**
 * The Home Page for the app
 */
export const HomePage = () => {
  return (
    <ViewListDataItemsPage
      headerTitle="Questions"
      dataToken="questions"
      getTitle={(item: any) => item.question}
      getDescription={(item: any) => item.answer}
    />
  );
};
