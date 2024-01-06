import { StringCodeHelper } from '../../../../common/helper/string/StringCodeHelper';

describe('Testing StringCode methods', () => {
  test('toCode() returns the text in code', () => {
    const text: string = '\t"The brown fox\nwalked west."';
    const target: string = '"\\t\\"The brown fox\\nwalked west.\\""';
    expect(StringCodeHelper.toCode(text)).toEqual(target);
  });

  test('toText() returns the code as text', () => {
    const code: string = '"\\tThe password is:\\n\\"Fork\\"."';
    const target: string = '\tThe password is:\n"Fork".';
    expect(StringCodeHelper.toText(code)).toEqual(target);
  });
});
