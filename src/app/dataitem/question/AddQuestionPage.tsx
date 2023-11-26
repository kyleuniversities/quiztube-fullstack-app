import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate, useParams } from 'react-router';
import { AddModifyQuestionPage } from './AddModifyQuestionPage';

export const AddQuestionPage = (): JSX.Element => {
  const { quizId } = useParams();
  return <AddModifyQuestionPage quizId={quizId} id={undefined} />;
};
