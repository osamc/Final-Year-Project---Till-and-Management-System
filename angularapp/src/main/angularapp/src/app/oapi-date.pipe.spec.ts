import { OapiDatePipe } from './oapi-date.pipe';

describe('OapiDatePipe', () => {
  it('create an instance', () => {
    const pipe = new OapiDatePipe();
    expect(pipe).toBeTruthy();
  });
});
