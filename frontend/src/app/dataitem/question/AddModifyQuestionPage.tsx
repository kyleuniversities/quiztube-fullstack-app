import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate } from 'react-router';
import '../../index.css';

/**
 * Page for adding or modifying a Question
 *
 * @param string id - The id of the question
 */
export const AddModifyQuestionPage = (props: {
  quizId: string | undefined;
  id: string | undefined;
}): JSX.Element => {
  const isEditing = props.id !== undefined;
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');
  const [time, setTime] = useState('10000');
  const title = isEditing ? 'Edit Question' : 'Add Question';

  useEffect(() => {
    if (isEditing) {
      request(`/quizzes/${props.quizId}/questions/${props.id}`).then(
        (questionItem) => {
          setQuestion(questionItem.question);
          setAnswer(questionItem.answer);
          setTime(questionItem.numberOfMilliseconds);
        }
      );
    }
  }, [isEditing, props.id]);

  const navigate = useNavigate();
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
          {/*<Form.Input
            fluid
            label="Time"
            placeholder="Enter a number of milliseconds"
            value={time}
            onChange={(e: any) => setTime(e.target.value)}
  />*/}
        </Form>
        <MultilineBreak lines={1} />
        <Button
          fluid
          color="blue"
          content={isEditing ? 'Save' : 'Submit'}
          onClick={() =>
            addModifyQuestion(
              navigate,
              question,
              answer,
              time,
              props.quizId,
              props.id
            )
          }
        />
      </Container>
    </SitePage>
  );
};

/**
 *
 * @param navigate
 * @param questionText
 * @param answerText
 * @param time
 * @param id
 */
const addModifyQuestion = (
  navigate: any,
  questionText: string,
  answerText: string,
  time: string,
  quizId: string | undefined,
  id: string | undefined
): void => {
  const isEditing = id !== undefined;
  const method = isEditing ? 'PATCH' : 'POST';
  try {
    const numberOfMilliseconds = parseNumberOfMilliseconds(time);
    const question = {
      id,
      question: questionText,
      answer: answerText,
      numberOfMilliseconds: numberOfMilliseconds,
      quizId,
    };
    const options = {
      method: method,
      headers: {
        'Content-Type': 'application/json',
      },
      data: JSON.stringify(question),
    };
    const requestUrl = isEditing
      ? `/quizzes/${quizId}/questions/${id}`
      : `/quizzes/${quizId}/questions`;
    request(requestUrl, options).then((data) => {
      if (!data.question || !data.answer) {
        alert(method + ' Question failed!');
        alert('Error: ' + JSON.stringify(data));
        return;
      }
      alert(method + ' Operation success!');
      alert('Question: ' + JSON.stringify(data));
      navigate(`/quizzes/${quizId}/questions`);
      window.location.reload();
    });
  } catch (error: any) {
    alert('Question could not be ' + method + "'ed");
  }
};

/**
 * Parse a time text and checks if the time text is valid
 *
 * @param string time - The time text
 */
const parseNumberOfMilliseconds = (time: string): number => {
  const numberOfMilliseconds = parseInt(time);
  if (numberOfMilliseconds < 1) {
    ExceptionHelper.throwRangeError('Number of milleseconds must be positive.');
  }
  return numberOfMilliseconds;
};
