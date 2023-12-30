import { useParams } from 'react-router';
import { ReactNode, useCallback, useEffect, useState } from 'react';
import {
  Button,
  Container,
  Header,
  Image,
  List,
  Search,
} from 'semantic-ui-react';
import { useNavigate } from 'react-router-dom';
import { request } from '../../../common/util/request';
import { SitePage } from '../../SitePage';
import { Link } from 'react-router-dom';
import '../index.css';
import { ConditionalContent } from '../../ConditionalContent';
import { useColorize, useUsername } from '../../context/AppContextManager';
import { NULL_ID } from '../../context/AppContextManager';

export type ViewUrlFunction = (parentId: string, id: string) => string;

const DEFAULT_QUIZ: any = {
  title: 'Loading...',
  description: 'Loading...',
  userId: '#null',
  picture: '#null',
  subject: '#null',
};

export const ViewQuizQuestionsPage = (): JSX.Element => {
  const { id } = useParams();
  const [quiz, setQuiz] = useState(DEFAULT_QUIZ);
  const quizId: string = id ? id : NULL_ID;
  const username = useUsername();
  const props: any = {
    headerTitle: 'Questions',
    parentDataToken: 'quizzes',
    dataToken: 'questions',
    parentId: quizId,
    itemsAreLinked: false,
    getTitle: (item: any) => item.question,
    getDescription: (item: any) => item.answer,
    getViewUrl: (parentId: string, id: string) =>
      `/quizzes/${parentId}/questions`,
    takingIsEnabled: true,
  };

  const [dataItems, setDataItems] = useState([]);
  const [query, setQuery] = useState('');
  const onSearchChange = useCallback(
    (e: any, data: any) => setQuery(data.value),
    []
  );
  const navigate = useNavigate();

  // Set up link to data item
  const onResultSelect = (e: any, res: { result: any }) => {
    navigate(
      `/${props.parentDataToken}/${props.parentId}/${props.dataToken}/${res.result.id}`
    );
  };

  // Load data items on render
  useEffect(() => {
    request(
      `/${props.parentDataToken}/${props.parentId}/${props.dataToken}`
    ).then((data) => {
      setDataItems(data);
    });
    request(`/quizzes/${id}`).then((res: any) => {
      setQuiz(res);
    });
  }, [props.dataToken, props.parentId]);

  // Filter data items by the search query
  const searchFilteredDataItems = dataItems.filter((item) => {
    return true; // getTitle(item).match(new RegExp(`${query}`));
  });

  // Set up View Url
  const viewUrl = props.getViewUrl(props.parentId, '');

  // Returns the page
  return (
    <SitePage>
      <DataItemsSearchSegment
        quiz={quiz}
        headerTitle={props.headerTitle}
        onSearchChange={onSearchChange}
        onResultSelect={onResultSelect}
        dataItems={searchFilteredDataItems}
        query={query}
      />
      <DataItemsMenuListColumnContainer>
        <ConditionalContent
          condition={
            props.takingIsEnabled && searchFilteredDataItems.length > 0
          }
        >
          <Link to={`${viewUrl}/take`}>
            <Button icon="bolt" color="brown" content="Take Quiz" />
          </Link>
        </ConditionalContent>
        <ConditionalContent condition={quiz.authorUsername === username}>
          <Link to={`${viewUrl}/add`}>
            <Button icon="plus" color="blue" content="New Question" />
          </Link>
        </ConditionalContent>

        <DataItemsListColumn
          quiz={quiz}
          parentDataToken={props.parentDataToken}
          dataToken={props.dataToken}
          dataItems={searchFilteredDataItems}
          parentId={props.parentId}
          itemsAreLinked={props.itemsAreLinked}
          getTitle={props.getTitle}
          getDescription={props.getDescription}
          getViewUrl={props.getViewUrl}
        />
      </DataItemsMenuListColumnContainer>
    </SitePage>
  );
};

/**
 * Segment for displaying search container
 *
 * @param string headerTitle - The title for the container
 * @param Consumer<event> onSearchChange - Action that occurs when the search bar text changes
 * @param Consumer<event> onResultSelect - Action that occurs when a data item is clicked
 * @param string query - Search bar text
 */
const DataItemsSearchSegment = (props: {
  quiz: any;
  headerTitle: string;
  onSearchChange: (e: any, data: any) => void;
  onResultSelect: (e: any, res: { result: any }) => void;
  dataItems: any[];
  query: string;
}) => {
  return (
    <Container fluid className="dataItemsSearchSegment">
      <Header as="h1">
        {props.quiz.title} - {props.headerTitle}
      </Header>
      <Search
        size="big"
        fluid
        selectFirstResult
        onSearchChange={props.onSearchChange}
        onResultSelect={props.onResultSelect}
        results={props.dataItems}
        value={props.query}
      />
    </Container>
  );
};

/**
 * Grid for displaying data items along with filter menu
 */
const DataItemsMenuListColumnContainer = (props: { children: ReactNode }) => {
  return (
    <Container className="dataItemsMenuListColumnContainer" fluid>
      {props.children}
    </Container>
  );
};

/**
 * Container for viewing data items
 *
 * @param string dataToken - The token associated with the data item for urls
 * @param Array<item> dataItems - The retrieved data items to display
 * @param Function<item,string> getTitle - Function that collects the displayed title of the item
 * @param Function<item,string> getDescription - Function that collects the displayed description of the item
 */
const DataItemsListColumn = (props: {
  quiz: any;
  parentDataToken: string;
  dataToken: string;
  dataItems: any[];
  parentId: string;
  itemsAreLinked: boolean;
  getTitle: (e: any) => string;
  getDescription: (e: any) => string;
  getViewUrl: ViewUrlFunction;
}) => {
  const colorize = useColorize();
  const questionImage = require('../../resources/question-image.png');
  const questionImageLight = require('../../resources/question-image-light.png');
  const selectedQuestionImage =
    colorize.colorMode().length === 0 ? questionImage : questionImageLight;
  return (
    <List size="huge" relaxed className="dataItemsListColumn">
      {props.dataItems.length == 0 ? (
        <p>This quiz has no questions.</p>
      ) : (
        props.dataItems.map((item) => {
          return (
            <DataListItem
              quiz={props.quiz}
              parentDataToken={props.parentDataToken}
              dataToken={props.dataToken}
              parentId={props.parentId}
              itemIsLinked={props.itemsAreLinked}
              itemId={item.id}
              itemImage={selectedQuestionImage}
              itemTitle={props.getTitle(item)}
              itemDescription={props.getDescription(item)}
              getViewUrl={props.getViewUrl}
            />
          );
        })
      )}
    </List>
  );
};

/**
 * Card for displaying a single data item
 *
 * @param string dataToken - The token associated with the data item for urls
 * @param string itemId - The id of the data item
 * @param string itemId - The url of the image of the data item
 * @param string itemTitle - The title of the data item
 * @param string itemDescription - The description of the data item
 */
const DataListItem = (props: {
  quiz: any;
  parentDataToken: string;
  dataToken: string;
  parentId: string;
  itemIsLinked: boolean;
  itemId: string;
  itemImage: string;
  itemTitle: string;
  itemDescription: string;
  getViewUrl: ViewUrlFunction;
}) => {
  const [isShowingDescription, setIsShowingDescription] = useState(false);
  const username = useUsername();
  const viewUrl: string = props.getViewUrl(props.parentId, props.itemId);
  return (
    <List.Item className="dataItem">
      <div className="dataItemSegment">
        <List.Content>
          <Container fluid>
            <Image className="dataItemImage" src={`${props.itemImage}`} />
            <div>
              <div className="dataItemText">
                <ConditionalContent condition={props.itemIsLinked}>
                  <Link to={`${viewUrl}/${props.itemId}`}>
                    <List.Header>{props.itemTitle}</List.Header>
                  </Link>
                </ConditionalContent>
                <ConditionalContent condition={!props.itemIsLinked}>
                  <List.Header>{props.itemTitle}</List.Header>
                </ConditionalContent>
                <List.Description>
                  {isShowingDescription
                    ? props.itemDescription
                    : '(Answer is Hidden)'}
                </List.Description>
              </div>

              <Container fluid className="dataItemButtonContainer">
                <Button
                  inline
                  icon="eye"
                  color="orange"
                  content={(isShowingDescription ? 'Hide' : 'Show') + ' Answer'}
                  onClick={() => setIsShowingDescription(!isShowingDescription)}
                />
                <ConditionalContent
                  condition={props.quiz.authorUsername === username}
                >
                  <Link to={`${viewUrl}/edit/${props.itemId}`}>
                    <Button
                      inline
                      icon="pencil alternate"
                      color="green"
                      content="Edit"
                    />
                  </Link>
                  <Button
                    inline
                    icon="trash"
                    color="red"
                    content="Delete"
                    onClick={() =>
                      deleteDataItem(
                        props.parentDataToken,
                        props.parentId,
                        props.dataToken,
                        props.itemId
                      )
                    }
                  />
                </ConditionalContent>
              </Container>
            </div>
          </Container>
        </List.Content>
      </div>
    </List.Item>
  );
};

const deleteDataItem = (
  parentDataToken: string,
  parentId: string,
  dataToken: string,
  id: string
): void => {
  request(`/${parentDataToken}/${parentId}/${dataToken}/${id}`, {
    method: 'DELETE',
  }).then((data) => {
    alert(data);
    window.location.reload();
  });
};
