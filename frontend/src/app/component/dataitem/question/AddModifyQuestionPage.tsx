import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { useNavigate } from 'react-router';
import {
  addModifyQuestionRequest,
  loadQuizQuestionRequest,
} from '../../../service/entity/question';
import { useAppContext } from '../../context/AppContextManager';
import { redirectFromUnauthorizedQuizActionRequest } from '../../../service/auth';
import './index.css';

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

  // Set up user data
  const userContext = useAppContext();

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
      loadQuizQuestionRequest(props.quizId, props.id, setQuestion, setAnswer);
    }
  }, [userContext, navigate, isEditing, props.quizId, props.id]);

  // Return component
  return (
    <SitePage>
      <Container fluid className="formContainer">
        <Header>{title}</Header>
        <Form>
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
    </SitePage>
  );
};
