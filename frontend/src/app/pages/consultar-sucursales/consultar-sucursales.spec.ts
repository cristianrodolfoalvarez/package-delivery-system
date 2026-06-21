import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultarSucursales } from './consultar-sucursales';

describe('ConsultarSucursales', () => {
  let component: ConsultarSucursales;
  let fixture: ComponentFixture<ConsultarSucursales>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsultarSucursales],
    }).compileComponents();

    fixture = TestBed.createComponent(ConsultarSucursales);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
