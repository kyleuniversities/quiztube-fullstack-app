import { useParams } from 'react-router';
import { AddModifyQuizPage } from './AddModifyQuizPage';

/**
 * Page for editing a quiz
 */
export const EditQuizPage = (): JSX.Element => {
  const { id } = useParams();
  return <AddModifyQuizPage id={id} />;
};
