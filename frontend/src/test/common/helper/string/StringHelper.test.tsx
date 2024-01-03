import { StringHelper } from '../../../../common/helper/string/StringHelper';
import { StringList } from '../../../../common/util/string';

describe('Testing StringList methods', () => {
  test('Makes an Empty String List', () => {
    const list: StringList = StringHelper.newEmptyStringList();
    expect(list.length).toBe(0);
  });
});
