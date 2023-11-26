import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate, useParams } from 'react-router';
import { AddModifyQuestionPage } from './AddModifyQuestionPage';

export const EditQuestionPage = (): JSX.Element => {
  const { quizId, id } = useParams();
  return <AddModifyQuestionPage quizId={quizId} id={id} />;
};
