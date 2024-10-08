import { SitePage } from "../../SitePage";
import { useEffect, useState } from "react";
import { MultilineBreak } from "../../MultilineBreak";
import { loadSubjectsRequest } from "../../../service/entity/subject";
import {
  DEFAULT_QUIZ_LIMIT_VALUE,
  NO_LIMIT_VALUE,
  loadQuizzesFromSubjectRequest,
  loadQuizzesRequest,
} from "../../../service/entity/quiz";
import { useColorize } from "../../context/AppContextManager";
import { Updater, ViewQuizzesContainer } from "./ViewQuizzesContainer";
import { QuizSearchContainer } from "./QuizSearchContainer";

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
      <div id={colorize("viewQuizzesContainerContainer")}>
        <QuizSearchContainer />
        <MultilineBreak lines={3} />
        <ViewAllQuizzesMostPopularContainer />
        {subjects.map((subject: any) => {
          return <ViewAllQuizzesSubContainer subject={subject} />;
        })}
        <MultilineBreak lines={3} />
        <ViewAllQuizzesContainer />
      </div>
    </SitePage>
  );
};

/**
 * Sub Container to View Quizzes that are the most popular
 */
const ViewAllQuizzesMostPopularContainer = () => {
  // Set up load quizzes function
  const loadQuizzesFunction = (
    setQuizPosts: Updater,
    setIsLoaded: Updater,
  ): void => {
    loadQuizzesRequest(DEFAULT_QUIZ_LIMIT_VALUE, setQuizPosts, setIsLoaded);
  };

  // Return component
  return (
    <ViewQuizzesContainer
      title="Most Popular Quizzes"
      loadQuizzesFunction={loadQuizzesFunction}
      loadQuizzesFunctionDependencyArray={[]}
      absentText="No quizzes exist yet"
    />
  );
};

/**
 * Sub Container to View Quizzes from a Subject
 */
const ViewAllQuizzesSubContainer = (props: { subject: any }) => {
  // Set up load quizzes function
  const loadQuizzesFunction = (
    setQuizPosts: Updater,
    setIsLoaded: Updater,
  ): void => {
    loadQuizzesFromSubjectRequest(
      props.subject.id,
      DEFAULT_QUIZ_LIMIT_VALUE,
      setQuizPosts,
      setIsLoaded,
    );
  };

  // Return component
  return (
    <ViewQuizzesContainer
      title={props.subject.text}
      loadQuizzesFunction={loadQuizzesFunction}
      loadQuizzesFunctionDependencyArray={[props.subject.id]}
      absentText="This subject has no quizzes yet"
    />
  );
};

/**
 * Sub Container to All Quizzes
 */
const ViewAllQuizzesContainer = () => {
  // Set up load quizzes function
  const loadQuizzesFunction = (
    setQuizPosts: Updater,
    setIsLoaded: Updater,
  ): void => {
    loadQuizzesRequest(NO_LIMIT_VALUE, setQuizPosts, setIsLoaded);
  };

  // Return component
  return (
    <ViewQuizzesContainer
      title="All Quizzes"
      loadQuizzesFunction={loadQuizzesFunction}
      loadQuizzesFunctionDependencyArray={[]}
      absentText="No quizzes exist yet"
    />
  );
};
