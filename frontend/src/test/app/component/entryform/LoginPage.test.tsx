import { render, screen } from '@testing-library/react';
import { Route, Routes } from 'react-router';
import { AppContextManager } from '../../../../app/component/context/AppContextManager';
import { BrowserRouter } from 'react-router-dom';
import { LoginPage } from '../../../../app/component/entryform/LoginPage';

describe('Testing Login Page', () => {
  // Set up Login Page render function
  const renderLoginPage = () => {
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

  // Test for Login Title
  test('Has title', () => {
    renderLoginPage();
    const loginElements = screen.getAllByText(/Log In/);
    const loginTitle = loginElements[1];
    expect(loginElements.length).toEqual(2);
    expect(loginTitle.localName).toEqual('div');
    expect(loginTitle.className).toEqual('ui header');
    expect(loginTitle.id).toEqual('formHeader');
  });

  // Test for Login Placeholders
  test('Has proper placeholders', () => {
    renderLoginPage();
    expect(screen.getByPlaceholderText(/Enter a Username/)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/Enter a password/)).toBeInTheDocument();
  });

  // Test for Button
  test('Has submit button', () => {
    renderLoginPage();
    const buttonElement = screen.getByText(/Submit/);
    expect(buttonElement).toBeInTheDocument();
    expect(buttonElement.localName).toEqual('button');
    expect(buttonElement.className).toEqual('ui blue fluid button');
  });
});
