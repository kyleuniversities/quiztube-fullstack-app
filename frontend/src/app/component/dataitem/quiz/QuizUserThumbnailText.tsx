import { Link } from 'react-router-dom';
import { Image } from 'semantic-ui-react';
import { useColorize } from '../../context/AppContextManager';
import { collectDefaultThumbnailPathFromUsername } from '../../../service/file';
import './index.css';

export const QuizUserThumbnailText = (props: { id: string; quiz: any }) => {
  // Set up color data
  const colorize = useColorize();

  // Return component
  return (
    <Link to={`/users/${props.quiz.userId}`}>
      <Image
        inline
        src={collectDefaultThumbnailPathFromUsername(props.quiz.authorUsername)}
        id={props.id}
      />
      <span className={colorize('authorText')}>
        {' '}
        <b>{props.quiz.authorUsername}</b>
      </span>
    </Link>
  );
};
