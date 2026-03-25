import { TestBed } from '@angular/core/testing';

import { DietaTipoMapperService } from './dieta-tipo-mapper.service';

describe('DietaTipoMapperService', () => {
  let service: DietaTipoMapperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DietaTipoMapperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
