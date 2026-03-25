import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarFichaModalComponent } from './editar-ficha-modal.component';

describe('EditarFichaModalComponent', () => {
  let component: EditarFichaModalComponent;
  let fixture: ComponentFixture<EditarFichaModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarFichaModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarFichaModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
