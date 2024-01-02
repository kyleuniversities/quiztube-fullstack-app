import { Button, Form } from 'semantic-ui-react';
import { useColorize } from '../../context/AppContextManager';
import { useState } from 'react';
import './index.css';
import { useNavigate } from 'react-router';

export const QuizSearchContainer = () => {
  // Set up search query
  const [query, setQuery] = useState('');

  // Set up color data
  const colorize = useColorize();

  // Set up navigation
  const navigate = useNavigate();

  // Handle search bar change
  const handleQueryChange = (newQuery: string): void => {
    if (isValidQuery(newQuery)) {
      setQuery(newQuery);
    }
  };

  // Return component
  return (
    <Form id={colorize('formWrapper')}>
      <h1>
        <div className={colorize('quizGroupTitle')}>Search</div>
      </h1>
      <div id="quizSearchContainer">
        <p>
          <span id="quizSearchBarWrapper">
            <input
              placeholder="Search for a Quiz"
              type="text"
              value={query}
              id="quizSearchBar"
              onChange={(e: any) => handleQueryChange(e.target.value)}
            />
          </span>
          <Button
            className="quizSearchButton"
            color="blue"
            content="Go"
            onClick={() => searchAction(navigate, query)}
          />
        </p>
      </div>
    </Form>
  );
};

// Check if query contains only valid characters
const isValidQuery = (text: string): boolean => {
  const lower = text.toLowerCase();
  const aCode = 'a'.charCodeAt(0);
  const zCode = 'z'.charCodeAt(0);
  const zeroCode = '0'.charCodeAt(0);
  const nineCode = '9'.charCodeAt(0);
  for (let i = 0; i < text.length; i++) {
    const ch = lower.charAt(i);
    const code = lower.charCodeAt(i);
    if (
      (code < aCode || code > zCode) &&
      (code < zeroCode || code > nineCode) &&
      ch !== ' ' &&
      ch !== '-'
    ) {
      return false;
    }
  }
  return true;
};

// Run search action
const searchAction = (navigate: any, query: string): void => {
  // Determine if accepting blank queries
  const isAcceptingBlankQueries = true;

  // Stop action if query is empty
  if (!isAcceptingBlankQueries && query.length === 0) {
    return;
  }

  // Change the query to (*) meaning all, if blank
  if (isAcceptingBlankQueries && query.length === 0) {
    query = '*';
  }

  // Encode query for url
  const encodedQuery = query.replace(/ /, '%20');

  // Go to search page
  navigate(`/quizzes/search/${encodedQuery}`);
};
