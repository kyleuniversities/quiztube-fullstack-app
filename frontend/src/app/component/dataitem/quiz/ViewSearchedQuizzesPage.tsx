import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { loadSubjectsRequest } from '../../../service/entity/subject';
import {
  loadQuizzesFromSubjectRequest,
  loadQuizzesFromTitleQueryRequest,
} from '../../../service/entity/quiz';
import { useColorize } from '../../context/AppContextManager';
import { ViewQuizzesContainer } from './ViewQuizzesContainer';
import { QuizSearchContainer } from './QuizSearchContainer';
import { useParams } from 'react-router';

/**
 * Page to View Search Quizzes
 */
export const ViewSearchQuizzesPage = () => {
  // Set up query data
  const { query } = useParams();

  // Set up quiz data
  const [quizPosts, setQuizPosts] = useState([]);

  // Set up color data
  const colorize = useColorize();

  // Load subjects
  useEffect(() => {
    loadQuizzesFromTitleQueryRequest(query, setQuizPosts);
  }, [query]);

  // Return component
  return (
    <SitePage>
      <div id={colorize('viewQuizzesContainerContainer')}>
        <QuizSearchContainer />
        <MultilineBreak lines={3} />
        <ViewQuizzesContainer
          title={`Quizzes like "${query}"`}
          quizPosts={quizPosts}
        />
      </div>
    </SitePage>
  );
};
