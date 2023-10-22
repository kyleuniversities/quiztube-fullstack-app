import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Header, Icon, Segment } from 'semantic-ui-react';

function App() {
  return (
    <Segment>
      <Icon name="home" size="huge" />
      <Header as="h1">Welcome to Quizzical!</Header>
    </Segment>
  );
}

export default App;
