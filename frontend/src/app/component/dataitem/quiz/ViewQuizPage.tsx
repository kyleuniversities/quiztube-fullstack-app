import { useEffect, useState } from 'react';
import { SitePage } from '../../SitePage';
import { useNavigate, useParams } from 'react-router';
import { Link } from 'react-router-dom';
import { Button, Image } from 'semantic-ui-react';
import {
  useAppContext,
  useColorize,
  useUserId,
  useUsername,
} from '../../context/AppContextManager';
import {
  collectDefaultThumbnailPathFromUsername,
  collectPicturePath,
} from '../../../service/file';
import { ConditionalContent } from '../../ConditionalContent';
import {
  NULL_LIKE,
  likeQuizRequest,
  loadLikeRequest,
  unlikeQuizRequest,
} from '../../../service/entity/like';
import {
  NULL_QUIZ,
  deleteQuizRequest,
  loadQuizAsWholeRequest,
} from '../../../service/entity/quiz';
import { MultilineBreak } from '../../MultilineBreak';
import { removeUserSessionDataIfTokenIsExpired } from '../../../service/auth';
import './index.css';

export const ViewQuizPage = (): JSX.Element => {
  // Set up parameter data
  const { id } = useParams();

  // Set up quiz data
  const [quiz, setQuiz] = useState(NULL_QUIZ);
  const [like, setLike] = useState(NULL_LIKE);

  // Set up user data
  const userContext = useAppContext();
  const userId: any = useUserId();
  const username: any = useUsername();

  // Set up color data
  const colorize = useColorize();

  // Set up navigation
  const navigate = useNavigate();

  // Load quiz and likes
  useEffect(() => {
    removeUserSessionDataIfTokenIsExpired(userContext);
    loadQuizAsWholeRequest(id, setQuiz);
    loadLikeRequest(id, userId, setLike);
  }, [userContext, id, userId]);

  // Return component
  return (
    <SitePage>
      <h1>{quiz.title}</h1>
      <Image src={collectPicturePath(quiz)} style={{ maxWidth: '300px' }} />
      <MultilineBreak lines={1} />
      <p>
        <Link to={`/users/${quiz.userId}`}>
          <Image
            inline
            src={collectDefaultThumbnailPathFromUsername(quiz.authorUsername)}
            style={{ maxWidth: '20px' }}
          />
          <span className={colorize('authorText')}>
            {' '}
            <b>{quiz.authorUsername}</b>
          </span>
        </Link>
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
      <ConditionalContent
        condition={like.userId !== undefined && like.userId.length > 10}
      >
        <Button
          icon="heart"
          color="blue"
          content="Liked"
          onClick={() => unlikeQuizRequest(id, like)}
        />
      </ConditionalContent>
      <ConditionalContent
        condition={like.userId !== undefined && like.userId.length < 10}
      >
        <Button
          icon="heart"
          color="grey"
          content="Like"
          onClick={() => likeQuizRequest(id, userId)}
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
            onClick={() => deleteQuizRequest(navigate, userId, id)}
          />
        </>
      )}
    </SitePage>
  );
};
