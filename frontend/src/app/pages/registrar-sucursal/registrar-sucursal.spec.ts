import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarSucursal } from './registrar-sucursal';

describe('RegistrarSucursal', () => {
  let component: RegistrarSucursal;
  let fixture: ComponentFixture<RegistrarSucursal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarSucursal],
    }).compileComponents();

    fixture = TestBed.createComponent(RegistrarSucursal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
