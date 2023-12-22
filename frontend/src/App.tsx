import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { HomePage } from './app/home/HomePage';
import { AddQuestionPage } from './app/dataitem/question/AddQuestionPage';
import { EditQuestionPage } from './app/dataitem/question/EditQuestionPage';
import { RegistrationPage } from './app/entryform/RegistrationPage';
import { AuthorizationContextManager } from './app/auth/AuthorizationContextManager';
import { LoginPage } from './app/entryform/LoginPage';
import { AddQuizPage } from './app/dataitem/quiz/AddQuizPage';
import { EditQuizPage } from './app/dataitem/quiz/EditQuizPage';
import { ViewQuizPage } from './app/dataitem/quiz/ViewQuizPage';
import { TakeQuizPage } from './app/dataitem/quiz/TakeQuizPage';
import { RequestPage } from './app/request/RequestPage';

function App() {
  return (
    <AuthorizationContextManager>
      <BrowserRouter>
        <Routes>
          <Route index element={<HomePage />} />
          <Route
            path="/questions/by/:quizId/add"
            element={<AddQuestionPage />}
          />
          <Route
            path="/questions/by/:quizId/edit/:id"
            element={<EditQuestionPage />}
          />
          <Route path="/questions/by/:quizId/take" element={<TakeQuizPage />} />
          <Route path="/quizzes/:id" element={<ViewQuizPage />} />
          <Route path="/quizzes/add" element={<AddQuizPage />} />
          <Route path="/quizzes/edit/:id" element={<EditQuizPage />} />
          <Route path="/registration" element={<RegistrationPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/request" element={<RequestPage />} />
        </Routes>
      </BrowserRouter>
    </AuthorizationContextManager>
  );
}

export default App;
