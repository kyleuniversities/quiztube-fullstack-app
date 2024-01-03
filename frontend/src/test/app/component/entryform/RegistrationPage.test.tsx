import { render, screen } from '@testing-library/react';
import { Route, Routes } from 'react-router';
import { AppContextManager } from '../../../../app/component/context/AppContextManager';
import { BrowserRouter } from 'react-router-dom';
import { RegistrationPage } from '../../../../app/component/entryform/RegistrationPage';

describe('Testing Registration Page', () => {
  // Set up Registration Page render function
  const renderLoginPage = () => {
    render(
      <AppContextManager>
        <BrowserRouter>
          <Routes>
            <Route index element={<RegistrationPage />} />
          </Routes>
        </BrowserRouter>
      </AppContextManager>
    );
  };

  // Test for Login Title
  test('Has title', () => {
    renderLoginPage();
    const registrationTitle = screen.getByText(/Create an Account/);
    expect(registrationTitle).toBeInTheDocument();
    expect(registrationTitle.localName).toEqual('div');
    expect(registrationTitle.className).toEqual('ui header');
    expect(registrationTitle.id).toEqual('formHeader');
  });

  // Test for Login Placeholders
  test('Has proper placeholders', () => {
    renderLoginPage();
    expect(screen.getByPlaceholderText(/Enter a Username/)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/Enter an Email/)).toBeInTheDocument();
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
