import { StringCodeHelper } from '../../../../common/helper/string/StringCodeHelper';
import { StringHelper } from '../../../../common/helper/string/StringHelper';
import { StringReplacementHelper } from '../../../../common/helper/string/StringReplacementHelper';
import { StringMap } from '../../../../common/util/string';

describe('Testing String Replacement methods', () => {
  test('Test Replace all with text and target', () => {
    const text: string = 'The water was floating way down South.';
    const target: string = 'The gutter guts floating guty down South.';
    expect(StringReplacementHelper.replaceAll(text, 'wa', 'gut')).toEqual(
      target
    );
  });

  test('Test Replace all with String Map', () => {
    const text: string =
      'public static void add(int a, int b)\n' +
      '\treturn a + b;\n' +
      '}\n' +
      'public static void sub(int a, int b)\n' +
      '\treturn a - b;\n' +
      '}\n';
    const target: string =
      'private static void add(double a, double b)\n' +
      '\treturn a + b;\n' +
      '}\n' +
      'private static void sub(double a, double b)\n' +
      '\treturn a - b;\n' +
      '}\n';
    const map: StringMap = StringHelper.newStringMapFromDoubleArray([
      ['public', 'private'],
      ['int', 'double'],
    ]);
    expect(StringReplacementHelper.replaceAllWithStringMap(text, map)).toEqual(
      target
    );
  });
});
