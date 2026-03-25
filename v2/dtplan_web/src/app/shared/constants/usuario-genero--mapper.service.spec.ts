import { TestBed } from '@angular/core/testing';

import { UsuarioGeneroMapperService } from './usuario-genero--mapper.service';

describe('UsuarioGeneroMapperService', () => {
  let service: UsuarioGeneroMapperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsuarioGeneroMapperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
