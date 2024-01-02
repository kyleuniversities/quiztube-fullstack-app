import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { loadMarkdownRequest } from '../../service/md';
import ReactMarkdown from 'react-markdown';
import { SitePage } from '../SitePage';
import { Container } from 'semantic-ui-react';
import { MultilineBreak } from '../MultilineBreak';
import { useMediaQuery } from 'react-responsive';
import {
  BIG_SCREEN_QUERY,
  MEDIUM_SCREEN_QUERY,
} from '../../../common/util/mobile';
import './index.css';

/**
 * Page for displaying pure markdown info articles
 */
export const InfoPage = (): JSX.Element => {
  // Set up parameter data
  const { key } = useParams();

  // Set up info page text data
  const [infoPageText, setInfoPageText] = useState('');

  // Set up responsive media queries
  const isBigScreen = useMediaQuery(BIG_SCREEN_QUERY);
  const isMediumScreen = useMediaQuery(MEDIUM_SCREEN_QUERY);
  const isSmallScreen = !isBigScreen && !isMediumScreen;

  // Load the markdown
  useEffect(() => {
    loadMarkdownRequest(key, isSmallScreen, setInfoPageText);
  }, [key, isSmallScreen]);

  // Return component
  return (
    <SitePage>
      <Container fluid className="infoPageContainer">
        <div className="infoPageWrapper">
          <MultilineBreak lines={1} />
          <ReactMarkdown className="reactMarkdown" children={infoPageText} />
          <MultilineBreak lines={3} />
        </div>
      </Container>
    </SitePage>
  );
};
