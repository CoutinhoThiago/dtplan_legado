import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarFichaExercicioModalComponent } from './criar-ficha-exercicio-modal.component';

describe('CriarFichaExercicioModalComponent', () => {
  let component: CriarFichaExercicioModalComponent;
  let fixture: ComponentFixture<CriarFichaExercicioModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CriarFichaExercicioModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarFichaExercicioModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
