import { Header } from 'semantic-ui-react';
import { ConditionalContent } from '../ConditionalContent';
import { SitePage } from '../SitePage';
import { NULL_ID, useUserId } from '../auth/AuthorizationContextManager';
import { ViewListDataItemsPage } from '../dataitem/ViewListDataItemsPage';

/**
 * The Home Page for the app
 */
export const HomePage = () => {
  const userId = useUserId();
  const usernameIsPresent = userId !== NULL_ID;
  return (
    <>
      <ConditionalContent condition={usernameIsPresent}>
        <ViewListDataItemsPage
          headerTitle="Quizzes"
          dataToken="quizzes"
          parentId={userId}
          itemsAreLinked={true}
          getTitle={(item: any) => item.title}
          getDescription={(item: any) => item.description}
          getViewUrl={(parentId: string, id: string) => `/quizzes`}
          takingIsEnabled={false}
        />
      </ConditionalContent>
      <ConditionalContent condition={!usernameIsPresent}>
        <SitePage>
          <Header as="h3">Welcome to Quizzical! Log In to Make Quizzes!</Header>
        </SitePage>
      </ConditionalContent>
    </>
  );
};
