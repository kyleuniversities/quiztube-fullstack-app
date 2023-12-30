import { useEffect, useState } from 'react';
import { SitePage } from '../../SitePage';
import { request } from '../../../common/util/request';
import { useNavigate, useParams } from 'react-router';
import { Link } from 'react-router-dom';
import { Button, Image } from 'semantic-ui-react';
import {
  useAppContext,
  useUserId,
  useUsername,
} from '../../context/AppContextManager';
import { collectPicturePath } from '../../../common/util/picture';

const DEFAULT_QUIZ: any = {
  title: 'Loading...',
  description: 'Loading...',
  userId: '#null',
  picture: '#null',
  subject: '#null',
};

export const ViewQuizPage = (): JSX.Element => {
  const { id } = useParams();
  const [quiz, setQuiz] = useState(DEFAULT_QUIZ);
  const userId: any = useUserId();
  const username: any = useUsername();
  const navigate = useNavigate();
  useEffect(() => {
    request(`/quizzes/${id}`).then((res: any) => {
      setQuiz(res);
    });
  }, []);
  return (
    <SitePage>
      <h1>{quiz.title}</h1>
      <Image src={collectPicturePath(quiz)} style={{ maxWidth: '300px' }} />
      <p>
        <b>Author: </b>
        {quiz.authorUsername}
      </p>
      <p>
        <b>Description: </b>
        {quiz.description}
      </p>
      <p>
        <b>Subject: </b>
        {quiz.subject}
      </p>
      <p>
        <b>Number of Likes: </b>
        {quiz.numberOfLikes}
      </p>
      <Link to={`/quizzes/${id}/questions`}>
        <Button icon="eye" color="orange" content="View Questions" />
      </Link>
      <Link to={`/quizzes/${id}/questions/take`}>
        <Button icon="bolt" color="brown" content="Take Quiz" />
      </Link>
      {quiz.authorUsername === username && (
        <>
          <Link to={`/quizzes/edit/${id}`}>
            <Button icon="pencil" color="green" content="Edit Quiz" />
          </Link>
          <Button
            icon="trash"
            color="red"
            content="Delete Quiz"
            onClick={() => deleteQuiz(navigate, userId, id)}
          />
        </>
      )}
    </SitePage>
  );
};

/**
 * Deletes the quiz
 */
const deleteQuiz = (
  navigate: any,
  userId: string,
  id: string | undefined
): void => {
  request(`/quizzes/${id}`, { method: 'DELETE' }).then((res) => {
    navigate(`/users/${userId}/quizzes`);
    window.location.reload();
  });
};
