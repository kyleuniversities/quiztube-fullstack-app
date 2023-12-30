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
  const usernameIsPresent = userId !== NULL_ID;
  return (
    <>
      <ViewAllQuizzesPage />
      {/*
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
          <h3>Welcome to Quizzical A3! Log In to Make Quizzes! A3</h3>
        </SitePage>
      </ConditionalContent>/**/}
    </>
  );
};
