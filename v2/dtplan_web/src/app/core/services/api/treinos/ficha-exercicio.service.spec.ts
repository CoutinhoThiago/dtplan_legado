import { TestBed } from '@angular/core/testing';

import { FichaExercicioService } from './ficha-exercicio.service';

describe('FichaExercicioService', () => {
  let service: FichaExercicioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FichaExercicioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
