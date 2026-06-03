import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultarPaquetes } from './consultar-paquetes';

describe('ConsultarPaquetes', () => {
  let component: ConsultarPaquetes;
  let fixture: ComponentFixture<ConsultarPaquetes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsultarPaquetes],
    }).compileComponents();

    fixture = TestBed.createComponent(ConsultarPaquetes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
