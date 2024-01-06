import { StringCodeHelper } from '../../../../common/helper/string/StringCodeHelper';

describe('Testing StringCode methods', () => {
  test('Test to Code', () => {
    const text: string = '\t"The brown fox\nwalked west."';
    const target: string = '"\\t\\"The brown fox\\nwalked west.\\""';
    console.log('_TEXT: ' + text);
    console.log('CODE: ' + StringCodeHelper.toCode(text));
    console.log('TARGET: ' + target);
    expect(StringCodeHelper.toCode(text)).toEqual(target);
  });

  test('Test to Text', () => {
    const code: string = '"\\tThe password is:\\n\\"Fork\\"."';
    const target: string = '\tThe password is:\n"Fork".';
    console.log('__CODE: ' + code);
    console.log('TEXT: ' + StringCodeHelper.toText(code));
    console.log('TARGET: ' + target);
    expect(StringCodeHelper.toText(code)).toEqual(target);
  });
});
