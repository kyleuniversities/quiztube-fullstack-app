import { Button, Container, Dropdown, Form, Header } from 'semantic-ui-react';
import { SitePage } from '../../SitePage';
import { useEffect, useState } from 'react';
import { MultilineBreak } from '../../MultilineBreak';
import { request } from '../../../common/util/request';
import { useNavigate } from 'react-router';
import '../../index.css';
import { NULL_ID, useUserId } from '../../context/AppContextManager';
import { indexOf } from '../../../common/util/list';

/**
 * Page for adding or modifying a Title
 *
 * @param string id - The id of the title
 */
export const AddModifyQuizPage = (props: {
  id: string | undefined;
}): JSX.Element => {
  const isEditing = props.id !== undefined;
  const [subjectOptions, setSubjectOptions] = useState([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [subject, setSubject] = useState('');
  const userId = useUserId();

  useEffect(() => {
    request('/subjects').then((res) => {
      setSubjectOptions(
        res.map((rawSubjectOption: any) => {
          return {
            ...rawSubjectOption,
            key: rawSubjectOption.text,
            value: rawSubjectOption.id,
          };
        })
      );
    });
    if (isEditing) {
      request(`/quizzes/${props.id}`).then((titleItem: any) => {
        setTitle(titleItem.title);
        setDescription(titleItem.description);
        setSubject(titleItem.subjectId);
      });
    }
  }, [isEditing, props.id]);

  const navigate = useNavigate();
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
            addModifyQuiz(
              navigate,
              title,
              description,
              subject,
              userId,
              props.id,
              subjectOptions
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
 * @param titleText
 * @param descriptionText
 * @param userId
 * @param id
 */
const addModifyQuiz = (
  navigate: any,
  titleText: string,
  descriptionText: string,
  subjectId: string,
  userId: string,
  id: string | undefined,
  subjectOptions: any
): void => {
  if (!userId || userId === NULL_ID) {
    alert('No user present');
    return;
  }
  const isEditing = id !== undefined;
  const method = isEditing ? 'PATCH' : 'POST';
  try {
    const title = {
      id,
      title: titleText,
      description: descriptionText,
      picture: collectPictureText(subjectId, subjectOptions),
      thumbnail: collectThumbnailText(subjectId, subjectOptions),
      subjectId,
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
    request(requestUrl, options).then((data: any) => {
      if (!data.title || !data.description) {
        alert(method + ' Quiz failed!');
        alert('Error: ' + JSON.stringify(data));
        return;
      }
      alert(method + ' Operation success!');
      alert('Quiz: ' + JSON.stringify(data));
      navigate(`/quizzes/${data.id}`);
      window.location.reload();
    });
  } catch (error: any) {
    alert('Quiz could not be ' + method + "'ed");
  }
};

const collectPictureText = (subjectId: string, subjectOptions: any) => {
  return collectImageText(subjectId, subjectOptions, '');
};

const collectThumbnailText = (subjectId: string, subjectOptions: any) => {
  return collectImageText(subjectId, subjectOptions, '_T');
};

const collectImageText = (
  subjectId: string,
  subjectOptions: any,
  extenderText: string
): string => {
  const index = indexOf(
    subjectOptions,
    (subject: any) => subject.id === subjectId
  );
  const subject = subjectOptions[index];
  const subjectSnakeCaseText = subject.text.toLowerCase().replace(/ /g, '-');
  return `static/quiz/quiz-picture-${subjectSnakeCaseText}${extenderText}.png`;
};
