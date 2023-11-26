import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate } from 'react-router';
import { AddModifyQuizPage } from './AddModifyQuizPage';

export const AddQuizPage = (): JSX.Element => {
  return <AddModifyQuizPage id={undefined} />;
};
