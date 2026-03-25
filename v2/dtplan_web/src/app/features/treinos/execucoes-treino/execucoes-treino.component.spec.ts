import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExecucoesTreinoComponent } from './execucoes-treino.component';

describe('ExecucoesTreinoComponent', () => {
  let component: ExecucoesTreinoComponent;
  let fixture: ComponentFixture<ExecucoesTreinoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExecucoesTreinoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExecucoesTreinoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
