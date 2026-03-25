import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotaoHeaderComponent } from './botao-header.component';

describe('BotaoHeaderComponent', () => {
  let component: BotaoHeaderComponent;
  let fixture: ComponentFixture<BotaoHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotaoHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotaoHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
