import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate } from 'react-router';

export const AddQuestionPage = (): JSX.Element => {
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');
  const [time, setTime] = useState('10000');
  const navigate = useNavigate();
  return (
    <SitePage>
      <Container fluid style={{ paddingLeft: '20px', paddingRight: '20px' }}>
        <Header>Add a Question</Header>
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
          <Form.Input
            fluid
            label="Time"
            placeholder="Enter a number of milliseconds"
            value={time}
            onChange={(e: any) => setTime(e.target.value)}
          />
        </Form>
        <MultilineBreak lines={1} />
        <Button
          fluid
          color="blue"
          content="Submit"
          onClick={() => addQuestion(navigate, question, answer, time)}
        />
      </Container>
    </SitePage>
  );
};

const addQuestion = (
  navigate: any,
  questionText: string,
  answerText: string,
  time: string
): void => {
  try {
    const numberOfMilliseconds = parseNumberOfMilliseconds(time);
    const question = {
      question: questionText,
      answer: answerText,
      numberOfMilliseconds: numberOfMilliseconds,
    };
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(question),
    };
    request(`/questions`, options).then((data) => {
      if (!data.question || !data.answer) {
        alert('POST Question failed!');
        alert('Error: ' + JSON.stringify(data));
        return;
      }
      alert('POST Question success!');
      alert('Question: ' + JSON.stringify(data));
      navigate('/');
      window.location.reload();
    });
  } catch (error: any) {
    alert('Question could not be added');
  }
};

const parseNumberOfMilliseconds = (time: string): number => {
  const numberOfMilliseconds = parseInt(time);
  if (numberOfMilliseconds < 1) {
    ExceptionHelper.throwRangeError('Number of milleseconds must be positive.');
  }
  return numberOfMilliseconds;
};
