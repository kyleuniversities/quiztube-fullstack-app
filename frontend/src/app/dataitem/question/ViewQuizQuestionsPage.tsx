import { useParams } from 'react-router';
import { ViewListDataItemsPage } from '../ViewListDataItemsPage';
import { NULL_ID } from '../../context/AppContextManager';

export const ViewQuizQuestionsPage = (): JSX.Element => {
  const { id } = useParams();
  const quizId: string = id ? id : NULL_ID;
  return (
    <ViewListDataItemsPage
      headerTitle="Questions"
      parentDataToken="quizzes"
      dataToken="questions"
      parentId={quizId}
      itemsAreLinked={false}
      getTitle={(item: any) => item.question}
      getDescription={(item: any) => item.answer}
      getViewUrl={(parentId: string, id: string) =>
        `/quizzes/${parentId}/questions`
      }
      takingIsEnabled={true}
    />
  );
};
