import { Button, Container, Dropdown, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { useNavigate } from 'react-router';
import {
  addModifyQuizRequest,
  loadQuizAsPartsRequest,
} from '../../../service/entity/quiz';
import { useAppContext, useUserId } from '../../context/AppContextManager';
import {
  NULL_SUBJECT,
  loadSubjectsAsOptionsRequest,
} from '../../../service/entity/subject';
import '../../index.css';
import { ArrayHelper } from '../../../../common/helper/ArrayHelper';
import { redirectFromUnauthorizedQuizActionRequest } from '../../../service/auth';

/**
 * Page for adding or modifying a Title
 */
export const AddModifyQuizPage = (props: {
  id: string | undefined;
}): JSX.Element => {
  // Set up editing data
  const isEditing = props.id !== undefined;

  // Set up field data
  const [subjectOptions, setSubjectOptions] = useState([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [subjectText, setSubjectText] = useState('');
  const [subjectId, setSubjectId] = useState('');

  // Set up user data
  const userContext = useAppContext();
  const userId = useUserId();

  // Set up navigation
  const navigate = useNavigate();

  // Load subjects and quiz
  useEffect(() => {
    redirectFromUnauthorizedQuizActionRequest(userContext, props.id, navigate);
    loadSubjectsAsOptionsRequest(setSubjectOptions).then(() => {
      if (isEditing) {
        loadQuizAsPartsRequest(
          props.id,
          subjectOptions,
          setTitle,
          setDescription,
          setSubjectText,
          setSubjectId
        );
      }
    });
  }, [isEditing, props.id]);

  // Return component
  return (
    <SitePage>
      <Container fluid className="formContainer">
        <Header>{isEditing ? 'Edit' : 'Add'} Quiz</Header>
        <Form>
          <Form.Input
            fluid
            label="Title"
            placeholder="Enter a Title"
            value={title}
            onChange={(e: any) => setTitle(e.target.value)}
          />
          <Form.Input
            fluid
            label="Description"
            placeholder="Enter an Description"
            value={description}
            onChange={(e: any) => setDescription(e.target.value)}
          />
        </Form>
        <MultilineBreak lines={2} />
        <span>
          Subject:{' '}
          <Dropdown
            inline
            options={subjectOptions}
            text={subjectText}
            onChange={(e, data: any) =>
              selectSubject(
                subjectOptions,
                data.value,
                setSubjectId,
                setSubjectText
              )
            }
          />
        </span>
        <MultilineBreak lines={2} />
        <Button
          fluid
          color="blue"
          content={isEditing ? 'Save' : 'Submit'}
          onClick={() =>
            addModifyQuizRequest(
              navigate,
              title,
              description,
              subjectId,
              userId,
              props.id,
              subjectOptions,
              isEditing ? 'PATCH' : 'POST',
              isEditing ? `/quizzes/${props.id}` : `/quizzes`
            )
          }
        />
      </Container>
    </SitePage>
  );
};

// Function for selecting subject
const selectSubject = (
  subjectOptions: any[],
  subjectId: string,
  setSubjectId: any,
  setSubjectText: any
): void => {
  const subject = ArrayHelper.query(
    subjectOptions,
    (subjectOption: any) => subjectOption.id === subjectId
  );
  //alert('ARR: ' + JSON.stringify(subjectOptions));
  //alert('TGT: ' + JSON.stringify(setSubjectId));
  //alert('QUE: ' + JSON.stringify(subject));
  setSubjectId(subjectId);
  setSubjectText(subject.text);
};
