import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { HomePage } from './app/home/HomePage';
import { AddQuestionPage } from './app/dataitem/question/AddQuestionPage';
import { EditQuestionPage } from './app/dataitem/question/EditQuestionPage';
import { RegistrationPage } from './app/entryform/RegistrationPage';
import { AuthorizationContextManager } from './app/auth/AuthorizationContextManager';
import { LoginPage } from './app/entryform/LoginPage';

function App() {
  return (
    <AuthorizationContextManager>
      <BrowserRouter>
        <Routes>
          <Route index element={<HomePage />} />
          <Route path="/questions/add" element={<AddQuestionPage />} />
          <Route path="/questions/edit/:id" element={<EditQuestionPage />} />
          <Route path="/registration" element={<RegistrationPage />} />
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      </BrowserRouter>
    </AuthorizationContextManager>
  );
}

export default App;
