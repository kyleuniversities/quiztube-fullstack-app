import { useParams } from 'react-router';
import { ViewListDataItemsPage } from '../ViewListDataItemsPage';
import { NULL_ID } from '../../auth/AuthorizationContextManager';

export const ViewQuizPage = (): JSX.Element => {
  const { id } = useParams();
  const quizId: string = id ? id : NULL_ID;
  return (
    <ViewListDataItemsPage
      headerTitle="Questions"
      dataToken="questions"
      parentId={quizId}
      itemsAreLinked={false}
      getTitle={(item: any) => item.question}
      getDescription={(item: any) => item.answer}
      getViewUrl={(parentId: string, id: string) => `/questions/by/${parentId}`}
      takingIsEnabled={true}
    />
  );
};
