import { useParams } from 'react-router';
import { AddModifyQuizPage } from './AddModifyQuizPage';

export const EditQuizPage = (): JSX.Element => {
  const { id } = useParams();
  return <AddModifyQuizPage id={id} />;
};
