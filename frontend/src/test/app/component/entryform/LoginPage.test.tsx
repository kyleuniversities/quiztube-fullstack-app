import { render, screen } from '@testing-library/react';
import { Route, Routes } from 'react-router';
import { AppContextManager } from '../../../../app/component/context/AppContextManager';
import { BrowserRouter } from 'react-router-dom';
import { LoginPage } from '../../../../app/component/entryform/LoginPage';

describe('Testing Login Page', () => {
  // Set up Login Page render function
  const renderLoginPage = () => {
    // Renders the login page
    render(
      <AppContextManager>
        <BrowserRouter>
          <Routes>
            <Route index element={<LoginPage />} />
          </Routes>
        </BrowserRouter>
      </AppContextManager>
    );
  };

  test('Has title', () => {
    renderLoginPage();
    const loginElements = screen.getAllByText(/Log In/);
    const loginTitle = loginElements[1];
    expect(loginElements.length).toEqual(2);
    expect(loginTitle.id).toEqual('formHeader');
  });

  test('Has proper placeholders', () => {
    renderLoginPage();
    expect(screen.getByPlaceholderText(/Enter a Username/)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/Enter a password/)).toBeInTheDocument();
  });
});
