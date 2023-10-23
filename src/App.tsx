import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Header, Icon, Segment } from 'semantic-ui-react';
import { SiteHeader } from './app/SiteHeader';
import { HomePage } from './app/home/HomePage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<HomePage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
