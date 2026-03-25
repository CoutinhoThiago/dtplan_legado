import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarFichaModalComponent } from './criar-ficha-modal.component';

describe('CriarFichaModalComponent', () => {
  let component: CriarFichaModalComponent;
  let fixture: ComponentFixture<CriarFichaModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CriarFichaModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarFichaModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
