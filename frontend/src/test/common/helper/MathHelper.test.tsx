import { MathHelper } from '../../../common/helper/MathHelper';

describe('Testing MathHelper methods', () => {
  test('interfaced math functions function correctly', () => {
    expect(MathHelper.ceiling(100.5)).toEqual(101);
    expect(MathHelper.floor(100.5)).toEqual(100);
    expect(MathHelper.max(3, 7)).toEqual(7);
    expect(MathHelper.min(3, 7)).toEqual(3);
    for (let i = 0; i < 100; i++) {
      const value = MathHelper.random() * 100;
      expect(value).toBeLessThan(100);
      expect(value).toBeGreaterThanOrEqual(0);
    }
  });
});
