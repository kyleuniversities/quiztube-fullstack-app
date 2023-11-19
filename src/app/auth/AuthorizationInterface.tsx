import { request } from '../../common/util/request';

export type UserCredentials = {
  username: string;
  password: string;
};

export const loginRequest = async (
  credentials: UserCredentials
): Promise<any> => {
  try {
    return await request('/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      data: JSON.stringify(credentials),
    });
  } catch (e: any) {
    throw e;
  }
};
