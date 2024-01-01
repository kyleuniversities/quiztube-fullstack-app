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
export const loadSubjectsRequest = (setSubjects: any) => {
  request('/subjects').then((res) => {
    setSubjects(res);
  });
};

/**
 * READ Method
 * Loads all subjects as options
 */
export const loadSubjectsAsOptionsRequest = (setSubjectOptions: any) => {
  request('/subjects').then((res) => {
    setSubjectOptions(
      res.map((rawSubjectOption: any) => {
        return {
          ...rawSubjectOption,
          key: rawSubjectOption.text,
          value: rawSubjectOption.id,
        };
      })
    );
  });
};
