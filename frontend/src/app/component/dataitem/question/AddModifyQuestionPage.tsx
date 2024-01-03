import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { useNavigate } from 'react-router';
import {
  addModifyQuestionRequest,
  loadQuizQuestionRequest,
} from '../../../service/entity/question';
import { useAppContext, useColorize } from '../../context/AppContextManager';
import { redirectFromUnauthorizedQuizActionRequest } from '../../../service/auth';
import './index.css';
import { handleAbsentResourceException } from '../../../util/exception';
import { LoadedResourceContainer } from '../LoadedResourceContainer';

/**
 * Page for adding or modifying a Question
 */
export const AddModifyQuestionPage = (props: {
  quizId: string | undefined;
  id: string | undefined;
}): JSX.Element => {
  // Set up mode data
  const isEditing = props.id !== undefined;
  const title = isEditing ? 'Edit Question' : 'Add Question';

  // Set up form fields
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');
  const [isAbsent, setIsAbsent] = useState(false);

  // Set up user data
  const userContext = useAppContext();

  // Set up color data
  const colorize = useColorize();

  // Set up navigation
  const navigate = useNavigate();

  // Load question
  useEffect(() => {
    redirectFromUnauthorizedQuizActionRequest(
      userContext,
      props.quizId,
      navigate
    );
    if (isEditing) {
      loadQuizQuestionRequest(
        props.quizId,
        props.id,
        setQuestion,
        setAnswer
      ).catch((exception: any) =>
        handleAbsentResourceException(exception, setIsAbsent)
      );
    }
  }, [userContext, navigate, isEditing, props.quizId, props.id]);

  // Set up combined id
  const combinedId = `${props.quizId}/${props.quizId}`;

  // Return component
  return (
    <SitePage>
      <LoadedResourceContainer
        entityName="Quiz Question"
        id={combinedId}
        isAbsent={isAbsent}
      >
        <Container fluid className="formContainer">
          <Header id={colorize('formHeader')}>{title}</Header>
          <Form id={colorize('formWrapper')}>
            <Form.Input
              fluid
              label="Question"
              placeholder="Enter a Question"
              value={question}
              onChange={(e: any) => setQuestion(e.target.value)}
            />
            <Form.Input
              fluid
              label="Answer"
              placeholder="Enter an Answer"
              value={answer}
              onChange={(e: any) => setAnswer(e.target.value)}
            />
          </Form>
          <MultilineBreak lines={1} />
          <Button
            fluid
            color="blue"
            content={isEditing ? 'Save' : 'Submit'}
            onClick={() =>
              addModifyQuestionRequest(
                navigate,
                question,
                answer,
                props.quizId,
                props.id,
                isEditing ? 'PATCH' : 'POST',
                isEditing
                  ? `/quizzes/${props.quizId}/questions/${props.id}`
                  : `/quizzes/${props.quizId}/questions`
              )
            }
          />
        </Container>
      </LoadedResourceContainer>
    </SitePage>
  );
};
