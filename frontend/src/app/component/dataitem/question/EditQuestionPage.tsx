import { useParams } from 'react-router';
import { AddModifyQuestionPage } from './AddModifyQuestionPage';

/**
 * Page for editing a question
 */
export const EditQuestionPage = (): JSX.Element => {
  const { quizId, id } = useParams();
  return <AddModifyQuestionPage quizId={quizId} id={id} />;
};
