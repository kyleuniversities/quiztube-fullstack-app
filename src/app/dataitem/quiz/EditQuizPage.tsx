import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate, useParams } from 'react-router';
import { AddModifyQuizPage } from './AddModifyQuizPage';

export const EditQuizPage = (): JSX.Element => {
  const { id } = useParams();
  return <AddModifyQuizPage id={id} />;
};
