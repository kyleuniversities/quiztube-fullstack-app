import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { HomePage } from './app/home/HomePage';
import { AddQuestionPage } from './app/dataitem/question/AddQuestionPage';
import { EditQuestionPage } from './app/dataitem/question/EditQuestionPage';
import { RegistrationPage } from './app/entryform/RegistrationPage';
import { AppContextManager } from './app/context/AppContextManager';
import { LoginPage } from './app/entryform/LoginPage';
import { AddQuizPage } from './app/dataitem/quiz/AddQuizPage';
import { EditQuizPage } from './app/dataitem/quiz/EditQuizPage';
import { ViewQuizQuestionsPage } from './app/dataitem/question/ViewQuizQuestionsPage';
import { TakeQuizPage } from './app/dataitem/quiz/TakeQuizPage';
import { RequestPage } from './app/request/RequestPage';
import { ViewQuizPage } from './app/dataitem/quiz/ViewQuizPage';
import { ViewUserQuizzesPage } from './app/dataitem/quiz/ViewUserQuizzesPage';
import { ViewUserAccountPage } from './app/dataitem/user/ViewUserAccountPage';

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
