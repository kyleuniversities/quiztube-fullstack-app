import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';
import { request } from '../../../common/util/request';
import { useNavigate, useParams } from 'react-router';

export const EditQuestionPage = (): JSX.Element => {
  const { id } = useParams();
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');
  const [time, setTime] = useState('10000');

  useEffect(() => {
    request(`/questions/${id}`).then((questionItem) => {
      setQuestion(questionItem.question);
      setAnswer(questionItem.answer);
      setTime(questionItem.numberOfMilliseconds);
    });
  }, []);

  const navigate = useNavigate();
  return (
    <SitePage>
      <Container fluid style={{ paddingLeft: '20px', paddingRight: '20px' }}>
        <Header>Edit Question</Header>
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
          content="Save"
          onClick={() => editQuestion(navigate, question, answer, time, id)}
        />
      </Container>
    </SitePage>
  );
};

const editQuestion = (
  navigate: any,
  questionText: string,
  answerText: string,
  time: string,
  id: string | undefined
): void => {
  try {
    const numberOfMilliseconds = parseNumberOfMilliseconds(time);
    const question = {
      question: questionText,
      answer: answerText,
      numberOfMilliseconds: numberOfMilliseconds,
    };
    const options = {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(question),
    };
    request(`/questions/${id}`, options).then((data) => {
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
