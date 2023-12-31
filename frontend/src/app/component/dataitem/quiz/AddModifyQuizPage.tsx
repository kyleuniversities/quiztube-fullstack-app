import { Button, Container, Dropdown, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { useNavigate } from 'react-router';
import {
  addModifyQuizRequest,
  loadQuizAsPartsRequest,
} from '../../../service/entity/quiz';
import {
  useAppContext,
  useColorize,
  useUserId,
} from '../../context/AppContextManager';
import { loadSubjectsAsOptionsRequest } from '../../../service/entity/subject';
import '../../index.css';
import { ArrayHelper } from '../../../../common/helper/ArrayHelper';
import { redirectFromUnauthorizedQuizActionRequest } from '../../../service/auth';
import { LoadedResourceContainer } from '../LoadedResourceContainer';
import { handleAbsentResourceException } from '../../../util/exception';

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
  const [isAbsent, setIsAbsent] = useState(false);

  // Set up user data
  const userContext = useAppContext();
  const userId = useUserId();

  // Set up color data
  const colorize = useColorize();

  // Set up navigation
  const navigate = useNavigate();

  // Redirect if unauthorized
  useEffect(() => {
    redirectFromUnauthorizedQuizActionRequest(userContext, props.id, navigate);
  }, [navigate, userContext, props.id]);

  // Load subject options
  useEffect(() => {
    loadSubjectsAsOptionsRequest(setSubjectOptions);
  }, []);

  // Load quiz for editing
  useEffect(() => {
    if (isEditing) {
      loadQuizAsPartsRequest(
        props.id,
        subjectOptions,
        setTitle,
        setDescription,
        setSubjectText,
        setSubjectId
      ).catch((exception: any) => {
        handleAbsentResourceException(exception, setIsAbsent);
      });
    }
  }, [isEditing, props.id, subjectOptions]);

  // Return component
  return (
    <SitePage>
      <LoadedResourceContainer
        entityName="Quiz"
        id={props.id}
        isAbsent={isAbsent}
      >
        <Container fluid className="formContainer">
          <Header id={colorize('formHeader')}>
            {isEditing ? 'Edit' : 'Add'} Quiz
          </Header>
          <Form id={colorize('formWrapper')}>
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
      </LoadedResourceContainer>
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
  setSubjectId(subjectId);
  setSubjectText(subject.text);
};
