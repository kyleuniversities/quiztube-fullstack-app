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
import { ConditionalContent } from '../../ConditionalContent';

const DEFAULT_QUIZ: any = {
  title: 'Loading...',
  description: 'Loading...',
  userId: '#null',
  picture: '#null',
  subject: '#null',
};

const NULL_LIKE: any = {
  id: '#null',
  userId: '#null',
  quizId: '#null',
};

const ABSENT_LIKE: any = {
  id: '#absent',
  userId: '#absent',
  quizId: '#absent ',
};

export const ViewQuizPage = (): JSX.Element => {
  const { id } = useParams();
  const [quiz, setQuiz] = useState(DEFAULT_QUIZ);
  const [like, setLike] = useState(NULL_LIKE);
  const userId: any = useUserId();
  const username: any = useUsername();
  const navigate = useNavigate();
  useEffect(() => {
    request(`/quizzes/${id}`).then((res: any) => {
      setQuiz(res);
    });
    const userExists: boolean = userId && userId.length > 10;
    if (userExists) {
      request(`/quizzes/${id}/likes/i-liked-this/${userId}`).then(
        (res: any) => {
          if (!res || res === null) {
            setLike(ABSENT_LIKE);
            return;
          }
          setLike(res);
        }
      );
    }
    if (!userExists) {
      setLike(NULL_LIKE);
    }
  }, [userId]);
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
      <ConditionalContent condition={like.userId && like.userId.length > 10}>
        <Button
          icon="heart"
          color="blue"
          content="Liked"
          onClick={() => unlikeQuiz(id, userId, like)}
        />
      </ConditionalContent>
      <ConditionalContent condition={like.userId && like.userId.length < 10}>
        <Button
          icon="heart"
          color="black"
          content="Like"
          onClick={() => likeQuiz(id, userId, like)}
        />
      </ConditionalContent>
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

const unlikeQuiz = (
  quizId: string | undefined,
  userId: string,
  like: any
): void => {
  try {
    const options = {
      method: 'DELETE',
    };
    request(`/quizzes/${quizId}/likes/${like.id}`, options).then(
      (data: any) => {
        alert('Unlike Operation success!');
        window.location.reload();
      }
    );
  } catch (error: any) {
    alert('Quiz could not be liked');
  }
};

const likeQuiz = (
  quizId: string | undefined,
  userId: string,
  like: any
): void => {
  try {
    if (userId.length < 10) {
      alert('Must be logged in to like a quiz.');
      return;
    }
    const like = {
      id: '#null',
      userId,
      quizId,
    };
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      data: JSON.stringify(like),
    };
    request(`/quizzes/${quizId}/likes`, options).then((data: any) => {
      if (!data.userId || !data.quizId) {
        alert('Like Quiz failed!');
        alert('Error: ' + JSON.stringify(data));
        return;
      }
      alert('Like Operation success!');
      alert('Like: ' + JSON.stringify(data));
      window.location.reload();
    });
  } catch (error: any) {
    alert('Quiz could not be liked');
  }
};
