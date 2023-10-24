import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate } from 'react-router';
import { AddModifyQuestionPage } from './AddModifyQuestionPage';

export const AddQuestionPage = (): JSX.Element => {
  return <AddModifyQuestionPage id={undefined} />;
};
