import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import {
  NO_LIMIT_VALUE,
  loadQuizzesFromTitleQueryRequest,
  loadQuizzesRequest,
} from '../../../service/entity/quiz';
import { useColorize } from '../../context/AppContextManager';
import { Updater, ViewQuizzesContainer } from './ViewQuizzesContainer';
import { QuizSearchContainer } from './QuizSearchContainer';
import { useParams } from 'react-router';

/**
 * Page to View Search Quizzes
 */
export const ViewSearchQuizzesPage = () => {
  // Set up query data
  const { query } = useParams();

  // Set up color data
  const colorize = useColorize();

  // Set up load quizzes function
  const loadAllQuizzesFunction = (
    setQuizPosts: Updater,
    setIsLoaded: Updater
  ): void => {
    loadQuizzesRequest(NO_LIMIT_VALUE, setQuizPosts, setIsLoaded);
  };

  // Set up load query quizzes function
  const loadTitleQueryQuizzesFunction = (
    setQuizPosts: Updater,
    setIsLoaded: Updater
  ): void => {
    loadQuizzesFromTitleQueryRequest(query, setQuizPosts, setIsLoaded);
  };

  // Select load quizzes function
  const selectedLoadQuizzesFunciton =
    query === '*' ? loadAllQuizzesFunction : loadTitleQueryQuizzesFunction;

  // Set up title text
  const title = query === '*' ? 'All Quizzes' : `Quizzes like "${query}"`;

  // Return component
  return (
    <SitePage>
      <div id={colorize('viewQuizzesContainerContainer')}>
        <QuizSearchContainer />
        <MultilineBreak lines={3} />
        <ViewQuizzesContainer
          title={title}
          loadQuizzesFunction={selectedLoadQuizzesFunciton}
          loadQuizzesFunctionDependencyArray={[query]}
          absentText="I'm sorry, no quizzes match your query :("
        />
      </div>
    </SitePage>
  );
};
