import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Header, Icon, Segment } from 'semantic-ui-react';
import { SiteHeader } from './app/SiteHeader';
import { HomePage } from './app/home/HomePage';
import { AddQuestionPage } from './app/dataitem/question/AddQuestionPage';
import { EditQuestionPage } from './app/dataitem/question/EditQuestionPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<HomePage />} />
        <Route path="/questions/add" element={<AddQuestionPage />} />
        <Route path="/questions/edit/:id" element={<EditQuestionPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
