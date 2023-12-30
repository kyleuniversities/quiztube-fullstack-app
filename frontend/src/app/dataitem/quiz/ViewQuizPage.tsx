import { useEffect, useState } from 'react';
import { SitePage } from '../../SitePage';
import { request } from '../../../common/util/request';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';
import { Button } from 'semantic-ui-react';

const DEFAULT_QUIZ: any = {
  title: 'Loading...',
  description: 'Loading...',
};

export const ViewQuizPage = (): JSX.Element => {
  const { id } = useParams();
  const [quiz, setQuiz] = useState(DEFAULT_QUIZ);
  useEffect(() => {
    request(`/quizzes/${id}`).then((res: any) => {
      setQuiz(res);
    });
  }, []);
  return (
    <SitePage>
      <h1>{quiz.title}</h1>
      <p>{quiz.description}</p>
      <Link to={`/quizzes/${id}/questions`}>
        <Button icon="file" color="orange" content="View Questions" />
      </Link>
      <Link to={`/quizzes/${id}/questions/take`}>
        <Button icon="bolt" color="brown" content="Take Quiz" />
      </Link>
    </SitePage>
  );
};
