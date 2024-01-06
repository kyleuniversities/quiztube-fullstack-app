import { render, screen } from '@testing-library/react';
import { Route, Routes } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import { ConditionalContent } from '../../../app/component/ConditionalContent';

describe('Testing Conditional Content', () => {
  // Test for Present Content
  test('Has present content', () => {
    render(
      <div>
        <p>Test Text</p>
        <ConditionalContent condition={true}>Test Text</ConditionalContent>
      </div>
    );
    expect(screen.getAllByText(/Test Text/).length).toEqual(2);
  });

  // Test for Absent Content
  test('Has absent content', () => {
    render(
      <div>
        <p>Test Text</p>
        <ConditionalContent condition={false}>Test Text</ConditionalContent>
      </div>
    );
    expect(screen.getAllByText(/Test Text/).length).toEqual(1);
  });
});
