import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarFichaExercicioModalComponent } from './editar-ficha-exercicio-modal.component';

describe('EditarFichaExercicioModalComponent', () => {
  let component: EditarFichaExercicioModalComponent;
  let fixture: ComponentFixture<EditarFichaExercicioModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarFichaExercicioModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarFichaExercicioModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
