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
import { request } from '../../common/util/request';
import { SitePage } from '../SitePage';
import { Link } from 'react-router-dom';
import './index.css';
import { ConditionalContent } from '../ConditionalContent';

export type ViewUrlFunction = (parentId: string, id: string) => string;

export type ViewListDataItemsPageProps = {
  headerTitle: string;
  dataToken: string;
  parentId: string;
  itemsAreLinked: boolean;
  getTitle: (item: any) => string;
  getDescription: (item: any) => string;
  getViewUrl: ViewUrlFunction;
  takingIsEnabled: boolean;
};

/**
 * Page for viewing data items in a List container
 *
 * @param string headerTitle - The title for the container
 * @param string dataToken - The token associated with the data item for urls
 * @param Function<item,string> getTitle - Function that collects the displayed title of the item
 * @param Function<item,string> getDescription - Function that collects the displayed description of the item
 * @param Predicate<item,string> filter - Function for filtering items based on a filter token
 * @param Array<string> filterTokens - The list of tokens for filtering items
 */
export const ViewListDataItemsPage = (
  props: ViewListDataItemsPageProps
): JSX.Element => {
  // Set up display variables
  const [dataItems, setDataItems] = useState([]);
  const [query, setQuery] = useState('');
  const onSearchChange = useCallback(
    (e: any, data: any) => setQuery(data.value),
    []
  );
  const navigate = useNavigate();
  const onResultSelect = (e: any, res: { result: any }) => {
    navigate(`/${props.dataToken}/${res.result.id}`);
  };

  // Load data items on render
  useEffect(() => {
    request(`/${props.dataToken}/by/${props.parentId}`).then((data) => {
      setDataItems(data);
    });
  }, [props.dataToken, props.parentId]);

  // Filter data items by the search query
  const searchFilteredDataItems = dataItems.filter((item) => {
    return true; // getTitle(item).match(new RegExp(`${query}`));
  });

  const viewUrl = props.getViewUrl(props.parentId, '');

  return (
    <SitePage>
      <DataItemsSearchSegment
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
        <Link to={`${viewUrl}/add`}>
          <Button icon="plus" color="blue" content="New Question" />
        </Link>
        <DataItemsListColumn
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
  headerTitle: string;
  onSearchChange: (e: any, data: any) => void;
  onResultSelect: (e: any, res: { result: any }) => void;
  dataItems: any[];
  query: string;
}) => {
  return (
    <Container fluid className="dataItemsSearchSegment">
      <Header as="h1">{props.headerTitle}</Header>
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
  dataToken: string;
  dataItems: any[];
  parentId: string;
  itemsAreLinked: boolean;
  getTitle: (e: any) => string;
  getDescription: (e: any) => string;
  getViewUrl: ViewUrlFunction;
}) => {
  const questionImage = require('../resources/question-image.png');
  return (
    <List size="huge" relaxed className="dataItemsListColumn">
      {props.dataItems.map((item) => {
        return (
          <DataListItem
            dataToken={props.dataToken}
            parentId={props.parentId}
            itemIsLinked={props.itemsAreLinked}
            itemId={item.id}
            itemImage={questionImage}
            itemTitle={props.getTitle(item)}
            itemDescription={props.getDescription(item)}
            getViewUrl={props.getViewUrl}
          />
        );
      })}
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
  dataToken: string;
  parentId: string;
  itemIsLinked: boolean;
  itemId: string;
  itemImage: string;
  itemTitle: string;
  itemDescription: string;
  getViewUrl: ViewUrlFunction;
}) => {
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
                <List.Description>{props.itemDescription}</List.Description>
              </div>
              <Container fluid className="dataItemButtonContainer">
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
                  onClick={() => deleteDataItem(props.dataToken, props.itemId)}
                />
              </Container>
            </div>
          </Container>
        </List.Content>
      </div>
    </List.Item>
  );
};

const deleteDataItem = (dataToken: string, id: string): void => {
  request(`/${dataToken}/${id}`, { method: 'DELETE' }).then((data) => {
    alert(data);
    window.location.reload();
  });
};
