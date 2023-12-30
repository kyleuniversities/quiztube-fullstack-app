import { Button, Container, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { request } from '../../../common/util/request';
import { useNavigate } from 'react-router';
import '../../index.css';
import { NULL_ID, useUserId } from '../../context/AppContextManager';

/**
 * Page for adding or modifying a Title
 *
 * @param string id - The id of the title
 */
export const AddModifyQuizPage = (props: {
  id: string | undefined;
}): JSX.Element => {
  const isEditing = props.id !== undefined;
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const userId = useUserId();

  useEffect(() => {
    if (isEditing) {
      request(`/quizzes/${props.id}`).then((titleItem) => {
        setTitle(titleItem.title);
        setDescription(titleItem.description);
      });
    }
  }, [isEditing, props.id]);

  const navigate = useNavigate();
  return (
    <SitePage>
      <Container fluid className="formContainer">
        <Header>Edit Title</Header>
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
        <MultilineBreak lines={1} />
        <Button
          fluid
          color="blue"
          content={isEditing ? 'Save' : 'Submit'}
          onClick={() =>
            addModifyQuiz(navigate, title, description, userId, props.id)
          }
        />
      </Container>
    </SitePage>
  );
};

/**
 *
 * @param navigate
 * @param titleText
 * @param descriptionText
 * @param userId
 * @param id
 */
const addModifyQuiz = (
  navigate: any,
  titleText: string,
  descriptionText: string,
  userId: string,
  id: string | undefined
): void => {
  if (!userId || userId === NULL_ID) {
    alert('No user present');
    return;
  }
  const isEditing = id !== undefined;
  const method = isEditing ? 'PATCH' : 'POST';
  try {
    const title = {
      title: titleText,
      description: descriptionText,
      userId,
    };
    const options = {
      method: method,
      headers: {
        'Content-Type': 'application/json',
      },
      data: JSON.stringify(title),
    };
    const requestUrl = isEditing ? `/quizzes/${id}` : `/quizzes`;
    request(requestUrl, options).then((data) => {
      if (!data.title || !data.description) {
        alert(method + ' Quiz failed!');
        alert('Error: ' + JSON.stringify(data));
        return;
      }
      alert(method + ' Operation success!');
      alert('Quiz: ' + JSON.stringify(data));
      navigate('/');
      window.location.reload();
    });
  } catch (error: any) {
    alert('Quiz could not be ' + method + "'ed");
  }
};
