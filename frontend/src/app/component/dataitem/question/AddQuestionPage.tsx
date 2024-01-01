import { useParams } from 'react-router';
import { AddModifyQuestionPage } from './AddModifyQuestionPage';

/**
 * Page for adding a question
 */
export const AddQuestionPage = (): JSX.Element => {
  const { quizId } = useParams();
  return <AddModifyQuestionPage quizId={quizId} id={undefined} />;
};
