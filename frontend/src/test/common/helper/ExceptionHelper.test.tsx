import { ExceptionHelper } from '../../../common/helper/ExceptionHelper';

describe('Testing ExceptionHelper methods', () => {
  test('throwRangeError() throws a range error', () => {
    const message = 'The number must be between 0 and 100';
    let errorWasThrown = false;
    try {
      ExceptionHelper.throwRangeError(message);
    } catch (error: any) {
      errorWasThrown = true;
    }
    expect(errorWasThrown).toEqual(true);
  });
});
