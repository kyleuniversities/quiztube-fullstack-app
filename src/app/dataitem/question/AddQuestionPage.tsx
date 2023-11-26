import { useParams } from 'react-router';
import { AddModifyQuestionPage } from './AddModifyQuestionPage';

export const AddQuestionPage = (): JSX.Element => {
  const { quizId } = useParams();
  return <AddModifyQuestionPage quizId={quizId} id={undefined} />;
};
