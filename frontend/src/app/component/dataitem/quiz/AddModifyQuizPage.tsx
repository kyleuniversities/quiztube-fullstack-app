import { Button, Container, Dropdown, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { useNavigate } from 'react-router';
import {
  addModifyQuizRequest,
  loadQuizAsPartsRequest,
} from '../../../service/entity/quiz';
import { useUserId } from '../../context/AppContextManager';
import { loadSubjectsAsOptionsRequest } from '../../../service/entity/subject';
import '../../index.css';

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
  const [subject, setSubject] = useState('');

  // Set up user data
  const userId = useUserId();

  // Load subjects and quiz
  useEffect(() => {
    loadSubjectsAsOptionsRequest(setSubjectOptions);
    if (isEditing) {
      loadQuizAsPartsRequest(props.id, setTitle, setDescription, setSubject);
    }
  }, [isEditing, props.id]);

  // Set up navigation
  const navigate = useNavigate();

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
            defaultValue={subject}
            onChange={(e, data: any) => setSubject(data.value)}
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
              subject,
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
