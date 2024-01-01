import { PromiseHelper } from '../../../common/helper/js/PromiseHelper';
import { handleException } from '../../util/exception';
import { NULL_TEXT } from '../general';
import { request } from '../request';

// Null subject constant
export const NULL_SUBJECT = {
  id: NULL_TEXT,
  text: '',
};

/**
 * READ Method
 * Loads all subjects
 */
export const loadSubjectsRequest = async (setSubjects: any) => {
  return request('/subjects')
    .then((res) => {
      setSubjects(res);
      return PromiseHelper.newConservativeVoidPromise();
    })
    .catch(handleException);
};

/**
 * READ Method
 * Loads all subjects as options
 */
export const loadSubjectsAsOptionsRequest = async (setSubjectOptions: any) => {
  return request('/subjects').then((res) => {
    setSubjectOptions(
      res.map((rawSubjectOption: any) => {
        return {
          ...rawSubjectOption,
          key: rawSubjectOption.text,
          value: rawSubjectOption.id,
        };
      })
    ).catch(handleException);
    return PromiseHelper.newConservativeVoidPromise();
  });
};
