import { ArrayHelper } from '../../../common/helper/ArrayHelper';
import { RandomHelper } from '../../../common/helper/RandomHelper';

describe('Testing ArrayHelper methods', () => {
  test('filter() filters an array', () => {
    const array: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
    const filtered: number[] = ArrayHelper.filter(
      array,
      (value: number) => value % 3 === 1
    );
    expect(array.length).toBe(10);
    expect(filtered.toString()).toBe('1,4,7');
  });

  test('map() maps an array', () => {
    const array: number[] = [1, 2, 3];
    const mapped: number[] = ArrayHelper.map(
      array,
      (value: number) => value * 100
    );
    expect(mapped.toString()).toBe('100,200,300');
  });

  test('reduce() reduces an array', () => {
    const text: string[] = ['f', 'o', 'x'];
    const reduced: number = ArrayHelper.reduce(
      text,
      (sum: number, ch: string) => sum + ch.charCodeAt(0),
      0
    );
    expect(reduced).toBe(
      'f'.charCodeAt(0) + 'o'.charCodeAt(0) + 'x'.charCodeAt(0)
    );
  });

  test('slice() slices an array', () => {
    const array: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
    const start: number = RandomHelper.newRandomInteger(0, 8);
    const upTo: number = RandomHelper.newRandomInteger(start, 9);
    const subArray: number[] = ArrayHelper.slice(array, start, upTo);
    expect(subArray.length).toBe(upTo - start);
    for (let i = start; i < upTo; i++) {
      expect(subArray[i - start]).toBe(array[i]);
    }
  });

  test('splice() splices an array', () => {
    const array: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
    const spliced: number[] = ArrayHelper.splice(array, 6, 4);
    expect(spliced.toString()).toBe('6,7,8,9');
  });
});
