import { Card, Icon } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { Link } from 'react-router-dom';
import { loadSubjectsRequest } from '../../../service/entity/subject';
import { loadQuizzesFromSubjectRequest } from '../../../service/entity/quiz';
import { useColorize } from '../../context/AppContextManager';
import { ViewQuizzesContainer } from './ViewQuizzesContainer';

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
      <div id={colorize('viewAllQuizzesContainer')}>
        {subjects.map((subject: any) => {
          return <ViewAllQuizzesSubContainer subject={subject} />;
        })}
      </div>
    </SitePage>
  );
};

/**
 * Sub Container to View All Arts Quizzes
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
    <ViewQuizzesContainer title={props.subject.text} quizPosts={quizPosts} />
  );
};
