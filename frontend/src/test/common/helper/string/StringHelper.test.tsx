import { StringHelper } from '../../../../common/helper/string/StringHelper';
import { StringList, StringMap } from '../../../../common/util/string';

describe('Testing StringList methods', () => {
  test('newEmptyStringList() makes an empty StringList', () => {
    const list: StringList = StringHelper.newEmptyStringList();
    expect(list.length).toBe(0);
  });

  test('stringMap() makes a String Map', () => {
    const map: StringMap = StringHelper.newStringMap();
    expect(map.size).toEqual(0);
  });

  test('stringMap() makes a String Map from double array', () => {
    const map: StringMap = StringHelper.newStringMapFromDoubleArray([
      ['public', 'private'],
      ['int', 'double'],
      ['+', '*'],
      ['-', '/'],
    ]);
    expect(map.size).toEqual(4);
  });

  test('substringEquals() identifies substring', () => {
    const text: string = 'The fox walked across the street.';
    const target: string = 'walked';
    expect(StringHelper.substringEquals(text, target, 8)).toBe(true);
  });
});
