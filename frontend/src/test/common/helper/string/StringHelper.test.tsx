import { StringHelper } from '../../../../common/helper/string/StringHelper';
import { StringList } from '../../../../common/util/string';

describe('Testing StringList methods', () => {
  test('newEmptyStringList() makes an empty StringList', () => {
    const list: StringList = StringHelper.newEmptyStringList();
    expect(list.length).toBe(0);
  });

  test('substringEquals() identifies substring', () => {
    const text: string = 'The fox walked across the street.';
    const target: string = 'walked';
    expect(StringHelper.substringEquals(text, target, 8)).toBe(true);
  });
});
