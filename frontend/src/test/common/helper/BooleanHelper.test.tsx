import { BooleanHelper } from '../../../common/helper/BooleanHelper';

describe('Testing BooleanHelper methods', () => {
  test('parseBoolean() parses booleans', () => {
    expect(BooleanHelper.parseBoolean('true')).toEqual(true);
    expect(BooleanHelper.parseBoolean('false')).toEqual(false);
  });
});
