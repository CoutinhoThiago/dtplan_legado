import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExecutarTreinoComponent } from './executar-treino.component';

describe('ExecutarTreinoComponent', () => {
  let component: ExecutarTreinoComponent;
  let fixture: ComponentFixture<ExecutarTreinoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExecutarTreinoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExecutarTreinoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
