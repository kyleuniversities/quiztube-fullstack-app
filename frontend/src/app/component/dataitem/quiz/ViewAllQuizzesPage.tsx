import { SitePage } from "../../SitePage";
import { useEffect, useState } from "react";
import { MultilineBreak } from "../../MultilineBreak";
import { loadSubjectsRequest } from "../../../service/entity/subject";
import {
  DEFAULT_QUIZ_LIMIT_VALUE,
  NO_LIMIT_VALUE,
  loadQuizCatalogRequest,
  loadQuizzesFromSubjectRequest,
  loadQuizzesRequest,
} from "../../../service/entity/quiz";
import { useColorize } from "../../context/AppContextManager";
import { Updater, ViewQuizzesContainer } from "./ViewQuizzesContainer";
import { QuizSearchContainer } from "./QuizSearchContainer";

/**
 * Loading Timing Constant
 */
const LOAD_TIMING_CONSTANT = 500;

/**
 * Page to View All Quizzes
 */
export const ViewAllQuizzesPage = () => {
  // Set up subject section data
  const [quizCatalog, setQuizCatalog] = useState(null);
  const [subjects, setSubjects] = useState([]);

  // Set up color data
  const colorize = useColorize();

  // Load subjects
  useEffect(() => {
    loadSubjectsRequest(setSubjects);
    loadQuizCatalogRequest(5, setQuizCatalog);
  }, []);

  // Return component
  return (
    <SitePage>
      <div id={colorize("viewQuizzesContainerContainer")}>
        <QuizSearchContainer />
        <MultilineBreak lines={3} />
        <ViewAllQuizzesMostPopularContainer quizCatalog={quizCatalog} />
        {subjects.map((subject: any, index: number) => {
          return (
            <ViewAllQuizzesSubContainer
              quizCatalog={quizCatalog}
              subject={subject}
              subjectIndex={index}
            />
          );
        })}
        <MultilineBreak lines={3} />
        <ViewAllQuizzesContainer
          quizCatalog={quizCatalog}
          subjects={subjects}
        />
      </div>
    </SitePage>
  );
};

/**
 * Sub Container to View Quizzes that are the most popular
 */
const ViewAllQuizzesMostPopularContainer = (props: { quizCatalog: any }) => {
  // Set up load quizzes function
  const loadQuizzesFunction = (
    setQuizPosts: Updater,
    setIsLoaded: Updater,
  ): void => {
    if (props.quizCatalog) {
      setQuizPosts(props.quizCatalog.popularQuizzes);
      setIsLoaded(true);
    }
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
const ViewAllQuizzesSubContainer = (props: {
  quizCatalog: any;
  subject: any;
  subjectIndex: number;
}) => {
  // Set up load quizzes function
  const loadQuizzesFunction = (
    setQuizPosts: Updater,
    setIsLoaded: Updater,
  ): void => {
    if (props.quizCatalog) {
      setTimeout(
        () => {
          const subjectQuizzes = props.quizCatalog.subjectQuizzes;
          const keys = Object.keys(subjectQuizzes);
          for (let key of keys) {
            if (key === props.subject.id) {
              setQuizPosts(subjectQuizzes[key]);
              setIsLoaded(true);
              return;
            }
          }
        },
        (props.subjectIndex + 1) * LOAD_TIMING_CONSTANT,
      );
    }
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
const ViewAllQuizzesContainer = (props: {
  quizCatalog: any;
  subjects: any;
}) => {
  // Set up load quizzes function
  const loadQuizzesFunction = (
    setQuizPosts: Updater,
    setIsLoaded: Updater,
  ): void => {
    if (props.quizCatalog && props.subjects) {
      setTimeout(
        () => {
          setQuizPosts(props.quizCatalog.allQuizzes);
          setIsLoaded(true);
        },
        (props.subjects.length + 1) * LOAD_TIMING_CONSTANT,
      );
    }
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
