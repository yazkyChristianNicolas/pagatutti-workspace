import { TestBed } from '@angular/core/testing';

import { ConfigurationProvider } from './configuration-provider';

describe('ConfigurationProviderService', () => {
  let service: ConfigurationProvider;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfigurationProvider);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
