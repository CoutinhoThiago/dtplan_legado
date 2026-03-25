import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarFichaComponent } from './criar-ficha.component';

describe('CriarFichaComponent', () => {
  let component: CriarFichaComponent;
  let fixture: ComponentFixture<CriarFichaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CriarFichaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarFichaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
