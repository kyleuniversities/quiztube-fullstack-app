import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useColorize, useUsername } from '../../context/AppContextManager';
import { NULL_USER, loadUserRequest } from '../../../service/entity/user';
import { loadQuizzesFromUserRequest } from '../../../service/entity/quiz';
import { ViewQuizzesContainer } from './ViewQuizzesContainer';
import './index.css';

/**
 * Page to View All Quizzes
 */
export const ViewUserQuizzesPage = () => {
  // Set up parameter data
  const { id } = useParams();

  // Set up user and quiz data
  const [user, setUser] = useState(NULL_USER);
  const [quizPosts, setQuizPosts] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const username = useUsername();

  // Use color data
  const colorize = useColorize();

  // Load user and quizzes
  useEffect(() => {
    loadUserRequest(id, setUser);
    loadQuizzesFromUserRequest(id, setQuizPosts, setIsLoaded);
  }, [id]);

  // Set up quizzes page title
  const title =
    user.username === username ? 'My Quizzes' : `Quizzes by ${user.username}`;

  // Return component
  return (
    <SitePage>
      <div id={colorize('viewQuizzesContainerContainer')}>
        <ViewQuizzesContainer
          title={title}
          isLoaded={isLoaded}
          quizPosts={quizPosts}
          absentText="This user has no quizzes yet"
        />
      </div>
    </SitePage>
  );
};
