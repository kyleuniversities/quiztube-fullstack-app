import { Card, Icon } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { Link, useParams } from 'react-router-dom';
import { useColorize, useUsername } from '../../context/AppContextManager';
import { NULL_USER, loadUserRequest } from '../../../service/entity/user';
import { loadQuizzesFromUserRequest } from '../../../service/entity/quiz';
import { ConditionalContent } from '../../ConditionalContent';
import './index.css';
import { ViewQuizzesContainer } from './ViewQuizzesContainer';

/**
 * Page to View All Quizzes
 */
export const ViewUserQuizzesPage = () => {
  // Set up parameter data
  const { id } = useParams();

  // Set up user and quiz data
  const [user, setUser] = useState(NULL_USER);
  const [quizPosts, setQuizPosts] = useState([]);
  const username = useUsername();

  // Use color data
  const colorize = useColorize();

  // Load user and quizzes
  useEffect(() => {
    loadUserRequest(id, setUser);
    loadQuizzesFromUserRequest(id, setQuizPosts);
  }, [id]);

  // Set up quizzes page title
  const title =
    user.username === username ? 'My Quizzes' : `Quizzes by ${user.username}`;

  // Return component
  return (
    <SitePage>
      <div id="viewQuizzesContainerContainer">
        <ViewQuizzesContainer title={title} quizPosts={quizPosts} />
      </div>
    </SitePage>
  );
};
