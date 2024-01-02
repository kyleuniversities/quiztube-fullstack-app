import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { loadSubjectsRequest } from '../../../service/entity/subject';
import {
  loadQuizzesFromSubjectRequest,
  loadQuizzesRequest,
} from '../../../service/entity/quiz';
import { useColorize } from '../../context/AppContextManager';
import { ViewQuizzesContainer } from './ViewQuizzesContainer';
import { QuizSearchContainer } from './QuizSearchContainer';

/**
 * Page to View All Quizzes
 */
export const ViewAllQuizzesPage = () => {
  // Set up subject section data
  const [subjects, setSubjects] = useState([]);

  // Set up color data
  const colorize = useColorize();

  // Load subjects
  useEffect(() => {
    loadSubjectsRequest(setSubjects);
  }, []);

  // Return component
  return (
    <SitePage>
      <div id={colorize('viewQuizzesContainerContainer')}>
        <QuizSearchContainer />
        <MultilineBreak lines={3} />
        <ViewAllQuizzesMostPopularContainer />
        {subjects.map((subject: any) => {
          return <ViewAllQuizzesSubContainer subject={subject} />;
        })}
      </div>
    </SitePage>
  );
};

/**
 * Sub Container to View Quizzes that are the most popular
 */
const ViewAllQuizzesMostPopularContainer = () => {
  // Set up quiz data
  const [quizPosts, setQuizPosts] = useState([]);

  // Load quizzes
  useEffect(() => {
    loadQuizzesRequest(true, setQuizPosts);
  }, []);

  // Return component
  return (
    <ViewQuizzesContainer
      title="Most Popular Quizzes"
      quizPosts={quizPosts}
      absentText="No quizzes exist yet"
    />
  );
};

/**
 * Sub Container to View Quizzes from a Subject
 */
const ViewAllQuizzesSubContainer = (props: { subject: any }) => {
  // Set up quiz data
  const [quizPosts, setQuizPosts] = useState([]);

  // Load quizzes
  useEffect(() => {
    loadQuizzesFromSubjectRequest(props.subject.id, setQuizPosts);
  }, [props.subject.id]);

  // Return component
  return (
    <ViewQuizzesContainer
      title={props.subject.text}
      quizPosts={quizPosts}
      absentText="This subject has no quizzes yet"
    />
  );
};
