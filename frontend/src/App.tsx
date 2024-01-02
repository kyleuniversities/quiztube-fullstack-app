import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { AppContextManager } from './app/component/context/AppContextManager';
import { HomePage } from './app/component/home/HomePage';
import { AddQuestionPage } from './app/component/dataitem/question/AddQuestionPage';
import { EditQuestionPage } from './app/component/dataitem/question/EditQuestionPage';
import { TakeQuizPage } from './app/component/dataitem/quiz/TakeQuizPage';
import { ViewQuizPage } from './app/component/dataitem/quiz/ViewQuizPage';
import { ViewQuizQuestionsPage } from './app/component/dataitem/question/ViewQuizQuestionsPage';
import { AddQuizPage } from './app/component/dataitem/quiz/AddQuizPage';
import { EditQuizPage } from './app/component/dataitem/quiz/EditQuizPage';
import { ViewUserQuizzesPage } from './app/component/dataitem/quiz/ViewUserQuizzesPage';
import { ViewUserAccountPage } from './app/component/dataitem/user/ViewUserAccountPage';
import { RegistrationPage } from './app/component/entryform/RegistrationPage';
import { LoginPage } from './app/component/entryform/LoginPage';
import { RequestPage } from './app/component/request/RequestPage';
import { ViewSearchQuizzesPage } from './app/component/dataitem/quiz/ViewSearchedQuizzesPage';

function App() {
  return (
    <AppContextManager>
      <BrowserRouter>
        <Routes>
          <Route index element={<HomePage />} />
          <Route
            path="/quizzes/:quizId/questions/add"
            element={<AddQuestionPage />}
          />
          <Route
            path="/quizzes/:quizId/questions/edit/:id"
            element={<EditQuestionPage />}
          />
          <Route
            path="/quizzes/:quizId/questions/take"
            element={<TakeQuizPage />}
          />
          <Route path="/quizzes/:id" element={<ViewQuizPage />} />
          <Route
            path="/quizzes/:id/questions"
            element={<ViewQuizQuestionsPage />}
          />
          <Route path="/quizzes/add" element={<AddQuizPage />} />
          <Route path="/quizzes/edit/:id" element={<EditQuizPage />} />
          <Route
            path="/quizzes/search/:query"
            element={<ViewSearchQuizzesPage />}
          />
          <Route path="/users/:id/quizzes" element={<ViewUserQuizzesPage />} />
          <Route path="/users/:id" element={<ViewUserAccountPage />} />
          <Route path="/registration" element={<RegistrationPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/request" element={<RequestPage />} />
        </Routes>
      </BrowserRouter>
    </AppContextManager>
  );
}

export default App;
