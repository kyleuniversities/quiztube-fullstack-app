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
import { collectPicturePath } from '../../../service/file';
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
import { QuizUserThumbnailText } from './QuizUserThumbnailText';
import { handleAbsentResourceException } from '../../../util/exception';
import { LoadedResourceContainer } from '../LoadedResourceContainer';

export const ViewQuizPage = (): JSX.Element => {
  // Set up parameter data
  const { id } = useParams();

  // Set up quiz data
  const [quiz, setQuiz] = useState(NULL_QUIZ);
  const [like, setLike] = useState(NULL_LIKE);
  const [isAbsent, setIsAbsent] = useState(false);

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
    loadLikeRequest(id, userId, setLike);
    loadQuizAsWholeRequest(id, setQuiz).catch((exception: any) => {
      handleAbsentResourceException(exception, setIsAbsent);
    });
  }, [userContext, id, userId]);

  // Return component
  return (
    <SitePage>
      <LoadedResourceContainer entityName="Quiz" id={id} isAbsent={isAbsent}>
        <h1>{quiz.title}</h1>
        <Image src={collectPicturePath(quiz)} style={{ maxWidth: '300px' }} />
        <MultilineBreak lines={1} />
        <p>
          <QuizUserThumbnailText id="quizWholeUserThumbnailImage" quiz={quiz} />
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
        <ConditionalContent condition={quiz.numberOfQuestions > 0}>
          <Link to={`/quizzes/${id}/questions/take`}>
            <Button icon="bolt" color="brown" content="Take Quiz" />
          </Link>
        </ConditionalContent>
        <ConditionalContent
          condition={like.userId !== undefined && like.userId.length > 10}
        >
          <Button
            icon="heart"
            color={colorize.getLikedButtonColor()}
            content="Liked"
            onClick={() => unlikeQuizRequest(id, like)}
          />
        </ConditionalContent>
        <ConditionalContent
          condition={like.userId !== undefined && like.userId.length < 10}
        >
          <Button
            icon="heart"
            color={colorize.getUnlikedButtonColor()}
            content="Like"
            onClick={() => likeQuizRequest(id, userId)}
          />
        </ConditionalContent>
        {quiz.authorUsername === username && (
          <>
            <Link to={`/quizzes/edit/${id}`}>
              <Button
                icon="pencil"
                color={colorize.getEditButtonColor()}
                content="Edit Quiz"
              />
            </Link>
            <Button
              icon="trash"
              color="red"
              content="Delete Quiz"
              onClick={() => deleteQuizRequest(navigate, userId, id)}
            />
          </>
        )}
      </LoadedResourceContainer>
    </SitePage>
  );
};
