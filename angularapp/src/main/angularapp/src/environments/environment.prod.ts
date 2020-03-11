import { Injector, InjectionToken } from '@angular/core';

export const environment = {
  production: true
};

const BASE_URL = new InjectionToken<string>('BaseUrl');
const injector = Injector.create({providers: [{provide: BASE_URL, useValue: 'http://localhost'}]});